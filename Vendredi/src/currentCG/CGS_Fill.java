package currentCG;

import tools.scanner.Scan;
import tools.switches.Switch;
import tools.tables.Table;

public class CGS_Fill {
	public static void main(String[] args) {
		int n = Scan.readInt();
		boolean[] start = Table.toBooleanArray(Table.flatten(Scan.readMap(n)), x -> x == '*');
		int n2 = start.length;
		boolean[][] switches = new boolean[n2][];
		for (int l = 0; l < n; l++) {
			for (int c = 0; c < n; c++) {
				boolean[][] t = new boolean[n][n];
				t[l][c] = true;
				if (l > 0) t[l-1][c] = true;
				if (l < n - 1) t[l+1][c] = true;
				if (c > 0) t[l][c-1] = true;
				if (c < n - 1) t[l][c+1] = true;
				switches[l * n + c] = Table.flatten(t);
			}
		}

		boolean[] result = Switch.lightAll(start, switches);

		int[][] t = new int[n][n];
		for (int l = 0; l < n; l++) {
			for (int c = 0; c < n; c++) {
				t[l][c] = result[l * n + c] ? 'X' : '.';
			}
		}
		Table.printMap(t);
	}
}
