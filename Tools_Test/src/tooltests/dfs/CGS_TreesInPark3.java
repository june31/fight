package tooltests.dfs;

import java.util.ArrayList;
import java.util.List;

import tools.board.DepthBoard;
import tools.chrono.Chrono;
import tools.dfs.DFSInts;
import tools.enumeration.combinations.IntCombinations0;
import tools.scanner.Scan;
import tools.tables.Table;

public class CGS_TreesInPark3 {

	static final int t = Scan.readInt();
	static final int n = Scan.readInt();
	static final int[][] parkMap = Table.map(Scan.readMap(n), x -> x - 'A');
	static final List<List<Integer>> parkCells = new ArrayList<>(); 
	static int line, col;

	public static void main(String[] args) {
		Chrono.start();
		// Get all parks that are smaller than N in size order. In the DFS, they will be processed first.
		for (int i = 0; i < 26; i++) parkCells.add(new ArrayList<>());
		for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) parkCells.get(parkMap[i][j]).add((i<<8) | j);

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
		int sum;

		public void copyTo(B b) {
			System.arraycopy(lines, 0, b.lines, 0, n);
			System.arraycopy(cols, 0, b.cols, 0, n);
			System.arraycopy(parks, 0, b.parks, 0, 26);
			for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) b.map[i][j] = map[i][j];
			b.sum = sum;
		}

		public int process(int order) {
			int command = order & 0xFFFF;
			int mode = order >> 24;
			int index = (order >> 16) & 0xFF;
			if (command == 0) {
				System.out.println();
			}
			while (command != 0) {
				if (mode == 0) { // Line
					line = index;
					col = Integer.numberOfTrailingZeros(command);
				} else if (mode == 1) { // Col
					line = Integer.numberOfTrailingZeros(command);
					col = index;
				} else if (mode == 2) { // Park
					int cell = parkCells.get(index).get(Integer.numberOfTrailingZeros(command));
					line = cell >> 8;
					col = cell & 0xFF;
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
				sum++;
				command ^= Integer.lowestOneBit(command);
			}
			return CONTINUE;
		}

		public int[] getCommands() {
			int min = Integer.MAX_VALUE;
			int order = 0;
			int cOrder = 0;
			int tOrder = 0;
			for (int i = 0; i < n; i++) {
				int v = n * (t - lines[i]);
				if (v != 0 && min > v) {
					min = v;
					order = i << 16;
					tOrder = t - lines[i];
					cOrder = n;
				}
				v = n * (t - cols[i]);
				if (v != 0 && min > v) {
					min = v;
					order = 1 << 24 | i << 16;
					tOrder = t - cols[i];
					cOrder = n;
				}
			}
			for (int i = 0; i < 26; i++) {
				int s = parkCells.get(i).size();
				if (s > 15) continue;
				int v = s * (t - parks[i]);
				if (v != 0 && min > v) {
					min = v;
					order = 2 << 24 | i << 16;
					tOrder = t - parks[i];
					cOrder = s;
				}
			}

			if (tOrder < 0) return null;
			var cmb = new IntCombinations0(cOrder, tOrder);
			if (cmb.max == 0) {
				System.out.println();
			}
			var t = new int[(int) cmb.max];
			int i = 0;
			for (int[] cs: cmb) {
				int x = 0;
				for (int c: cs) x |= 1 << c;
				t[i++] = order | x;
			}
			return t;
		}

		public boolean endCondition() { 
			if (depth > 50) {
				System.out.println();
			}
			return sum == t * n; }
	}
}


