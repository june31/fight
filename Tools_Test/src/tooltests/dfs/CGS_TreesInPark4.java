package tooltests.dfs;

import java.util.ArrayList;
import java.util.List;

import tools.board.DepthBoard;
import tools.chrono.Chrono;
import tools.dfs.DFSInts;
import tools.enumeration.combinations.IntCombinations0;
import tools.scanner.Scan;
import tools.tables.Table;

public class CGS_TreesInPark4 {

	static final int t = Scan.readInt();
	static final int n = Scan.readInt();
	static final int[][] parkMap = Table.map(Scan.readMap(n), x -> x - 'A');
	static final List<List<Integer>> lineCells = new ArrayList<>(); 
	static final List<List<Integer>> colCells = new ArrayList<>(); 
	static final List<List<Integer>> parkCells = new ArrayList<>(); 
	static int line, col;

	public static void main(String[] args) {
		Chrono.start();
		for (int i = 0; i < n; i++) lineCells.add(new ArrayList<>());
		for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) lineCells.get(i).add((i<<8) | j);
		for (int i = 0; i < n; i++) colCells.add(new ArrayList<>());
		for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) colCells.get(j).add((i<<8) | j);
		for (int i = 0; i < 26; i++) parkCells.add(new ArrayList<>());
		for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) parkCells.get(parkMap[i][j]).add((i<<8) | j);

		// Main DFS
		DFSInts<B> dfs = new DFSInts<>(B::new);
		int[][] res = dfs.process().map;

		Table.printMap(Table.map(res, x -> x >= 2 ? 'T' : '_'));
		Chrono.stop();
	}

	static class B extends DepthBoard<B> {

		int[] lines = new int[n];
		int[] cols = new int[n];
		int[] parks = new int[26];
		int[][] map = new int[n][n];
		int sum;
		int[] currentCells = new int[t];
		
		public void copyTo(B b) {
			System.arraycopy(lines, 0, b.lines, 0, n);
			System.arraycopy(cols, 0, b.cols, 0, n);
			System.arraycopy(parks, 0, b.parks, 0, 26);
			b.map = Table.copy(map);
			b.sum = sum;
		}

		public int process(int order) {
			int command = order & 0xFFFF;
			int mode = order >> 24;
			int index = (order >> 16) & 0xFF;
			int treeNb = 0;
			while (command != 0) {
				currentCells[treeNb++] = switch (mode) {
				case 0 -> selectCell(lineCells.get(index), command);
				case 1 -> selectCell(colCells.get(index), command);
				default -> selectCell(parkCells.get(index), command);
				};
				command ^= Integer.lowestOneBit(command);
			}
			for (int i = 0; i < treeNb; i++) {
				//if (cell == -1) return FAIL;
				line = currentCells[i] >> 8;
				col = currentCells[i] & 0xFF;
				if (map[line][col] != 0 || cols[col] == t || lines[line] == t || parks[parkMap[line][col]] == t) return FAIL;
				set(line, col, 2);
				lines[line]++;
				cols[col]++;
				parks[parkMap[line][col]]++;
				for (int l = line - 1; l <= line + 1; l++) for (int c = col - 1; c <= col + 1; c++) set(l, c, 1);
			}
			sum += treeNb;
			return CONTINUE;
		}

		private int selectCell(List<Integer> cells, int command) {
			int pos = col = Integer.numberOfTrailingZeros(command);
			for (int cell : cells) if (map[cell >> 8][cell & 0xFF] == 0 && pos-- == 0) return cell;
			return -1;
		}

		private void set(int l, int c, int v) {
			if (l < 0 || l == n || c < 0 || c == n || map[l][c] != 0) return;
			map[l][c] = v;
		}

		public int[] getCommands() {
			int min = Integer.MAX_VALUE;
			int order = 0;
			int cOrder = 0;
			int tOrder = 0;
			for (int i = 0; i < n; i++) {
				if (lines[i] == t) continue;
				int v = magic(lineCells.get(i), lines[i]);
				if (min > v) {
					min = v;
					order = i << 16;
					tOrder = t - lines[i];
					cOrder = v & 0xFF;
				}
			}
			for (int i = 0; i < n; i++) {
				if (cols[i] == t) continue;
				int v = magic(colCells.get(i), cols[i]);
				if (min > v) {
					min = v;
					order = 1 << 24 | i << 16;
					tOrder = t - cols[i];
					cOrder = v & 0xFF;
				}
			}
			for (int i = 0; i < 26; i++) {
				if (parks[i] == t || parkCells.get(i).isEmpty()) continue;
				int v = magic(parkCells.get(i), parks[i]);
				if (v != 0 && min > v) {
					min = v;
					order = 2 << 24 | i << 16;
					tOrder = t - parks[i];
					cOrder = v & 0xFF;
				}
			}

			if (tOrder > cOrder) return null;
			var cmb = new IntCombinations0(cOrder, tOrder);
			var t = new int[(int) cmb.max];
			int i = 0;
			for (int[] cs: cmb) {
				int x = 0;
				for (int c: cs) x |= 1 << c;
				t[i++] = order | x;
			}
			return t;
		}

		private int magic(List<Integer> list, int trees) {
			int avail = 0;
			for (int i : list) if (map[i >> 8][i & 0xFF] == 0) avail++;
			//return (avail + trees * 2) << 8 | avail;
			return (avail * 50 + trees*trees*trees) << 8 | avail;
		}

		public boolean endCondition() { return sum == t * n; }
	}
}


