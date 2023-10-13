package tooltests.dfs;

import java.util.ArrayList;
import java.util.List;

import tools.board.DepthBoard;
import tools.chrono.Chrono;
import tools.dfs.DFSInts;
import tools.enumeration.combinations.IntCombinations0;
import tools.scanner.Scan;
import tools.tables.Table;

public class CGS_TreesInPark2 {

	static final int t = Scan.readInt();
	static final int n = Scan.readInt();
	static final int[][] parkMap = Table.map(Scan.readMap(n), x -> x - 'A');
	static final List<List<Integer>> prioParkCells = new ArrayList<>(); 
	static int limit;

	public static void main(String[] args) {
		Chrono.start();
		// Get all parks that are smaller than N in size order. In the DFS, they will be processed first.
		List<List<Integer>> parkCells = new ArrayList<>();
		for (int i = 0; i < 26; i++) parkCells.add(new ArrayList<>());
		for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) parkCells.get(parkMap[i][j]).add((i<<16) | j);
		for (var l: parkCells) if (l.size() > 0 && l.size() < n) prioParkCells.add(l);
		prioParkCells.sort((l1, l2) -> Integer.compare(l1.size(), l2.size()));
		limit = prioParkCells.size();

		// Main DFS
		DFSInts<B> dfs = new DFSInts<>(B::new);
		int[][] res = dfs.process().map;

		Table.printMap(Table.map(res, x -> x == 0 ? '_' : 'T'));
		Chrono.stop();
	}

	static class B extends DepthBoard<B> {

		int[] lines = new int[n];
		int[] cols = new int[n];
		int[] parks = new int[26];
		int[][] map = new int[n][n];

		public void copyTo(B b) {
			System.arraycopy(lines, 0, b.lines, 0, n);
			System.arraycopy(cols, 0, b.cols, 0, n);
			System.arraycopy(parks, 0, b.parks, 0, 26);
			for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) b.map[i][j] = map[i][j];
		}

		public int process(int command) {
			while (command != 0) {
				int line, col;
				if (depth < limit) {
					int cell = prioParkCells.get(depth).get(Integer.numberOfTrailingZeros(command));
					line = cell >> 16;
					col = cell & 0xFFFF;
				} else {
					line = depth - limit;
					col = Integer.numberOfTrailingZeros(command);
				}
				if (cols[col] == t || lines[line] == t || parks[parkMap[line][col]] == t) return FAIL;
				if (line != 0) {
					if (col > 0 && map[line - 1][col - 1] == 1) return FAIL;
					if (map[line - 1][col] == 1) return FAIL;
					if (col < n - 1 && map[line - 1][col + 1] == 1) return FAIL;
				}
				if (col > 0 && map[line][col - 1] == 1) return FAIL;
				if (col < n - 1 && map[line][col + 1] == 1) return FAIL;
				if (line != n - 1) {
					if (col > 0 && map[line + 1][col - 1] == 1) return FAIL;
					if (map[line + 1][col] == 1) return FAIL;
					if (col < n - 1 && map[line + 1][col + 1] == 1) return FAIL;
				}
				map[line][col] = 1;
				lines[line]++;
				cols[col]++;
				parks[parkMap[line][col]]++;
				command ^= Integer.lowestOneBit(command);
			}
			return CONTINUE;
		}

		public int[] getCommands() {
			// Depth 0 -> prioParkCells.size() : process small parks
			// Depth prioParkCells.size() -> prioParkCells.size() + n - 1 : process lines 
			int cellNb = depth < limit ? prioParkCells.get(depth).size() : n;
			int treeNb = depth < limit ? t : t - lines[depth - limit];
			if (treeNb < 0) return null;
			var cmb = new IntCombinations0(cellNb, treeNb);
			var list = new ArrayList<Integer>();
			for (int[] cs: cmb) { // If treeNb == 0, 0 will be added (that's what we want).
				int x = 0;
				for (int c: cs) x |= 1<<c;
				list.add(x);
			}
			return Table.toIntArray(list);
		}

		public boolean endCondition() { return depth == limit + n - 1; }
	}
}


