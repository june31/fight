package tooltests.dfs;

import java.util.ArrayList;
import java.util.List;

import tools.board.DepthBoard;
import tools.chrono.Chrono;
import tools.dfs.DFSInts;
import tools.enumeration.combinations.IntCombinations0;
import tools.scanner.Scan;
import tools.tables.Table;

public class CGS_TreesInPark {

	static final int t = Scan.readInt();
	static final int n = Scan.readInt();
	static final int[][] parkMap = Table.map(Scan.readMap(n), x -> x - 'A');
	static final List<List<Integer>> lastParkRows = new ArrayList<>();
	static final int[] comb;
	static {
		var cmb = new IntCombinations0(n, t);
		comb = new int[(int) cmb.max];
		int id = 0;
		for (int[] cs: cmb) {
			for (int c: cs) comb[id] |= 1<<c;
			id++;
		}
	}

	public static void main(String[] args) {
		Chrono.start();
		for (int i = 0; i < n; i++) lastParkRows.add(new ArrayList<Integer>());
		var all = new ArrayList<Integer>();
		for (int i = n-1; i >= 0; i--) {
			for (int j = 0; j < n; j++) {
				int p = parkMap[i][j];
				if (!all.contains(p)) {
					lastParkRows.get(i).add(p);
					all.add(p);
				}
			}
		}

		DFSInts<B> dfs = new DFSInts<>(B::new);
		int[][] res = dfs.process().map;
		Table.printMap(Table.map(res, x -> x == 0 ? '_' : 'T'));
		Chrono.stop();
	}
	
	static class B extends DepthBoard<B> {
		
		int[] cols = new int[n];
		int[] parks = new int[26];
		int[][] map = new int[n][n];
		
		public void copyTo(B b) {
			System.arraycopy(cols, 0, b.cols, 0, n);
			System.arraycopy(parks, 0, b.parks, 0, 26);
			for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) b.map[i][j] = map[i][j];
		}

		public int process(int command) {
			while (command != 0) {
				int pos = Integer.numberOfTrailingZeros(command);
				int park = parkMap[depth][pos];
				if (cols[pos] == t || parks[park] == t) return FAIL;
				if (depth != 0) {
					if (pos > 0 && map[depth - 1][pos - 1] == 1) return FAIL;
					if (map[depth - 1][pos] == 1) return FAIL;
					if (pos < n - 1 && map[depth - 1][pos + 1] == 1) return FAIL;
				}
				if (pos > 0 && map[depth][pos - 1] == 1) return FAIL;
				if (pos < n - 1 && map[depth][pos + 1] == 1) return FAIL;
				map[depth][pos] = 1;
				cols[pos]++;
				parks[park]++;
				command ^= Integer.lowestOneBit(command);
			}
			for (int p: lastParkRows.get(depth)) if (parks[p] != t) return FAIL;
			if (depth == n - 1) { 
				Table.printMap(Table.map(map, x -> x == 0 ? '_' : 'T'));
				System.out.println();
				return FAIL;
			}
			return CONTINUE;
		}
		
		public int[] getCommands() { return comb; }
	}
}


