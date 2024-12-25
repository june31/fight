package aoc24;

import tools.scanner.Scan;
import tools.strings.S;
import tools.tables.Table;

public class Day04b {
	public static void main(String[] args) {
		var m = Table.wall(Scan.readRawMap(), 5, '#');
		long z = 0;
		for (int l = 5; l < m.length - 5; l++) {
			for (int c = 5; c < m[0].length - 5 ; c++) {
				if (m[l][c] == 'A' && m[l - 1][c - 1] == 'M' && m[l + 1][c - 1] == 'M' && m[l - 1][c + 1] == 'S' && m[l + 1][c + 1] == 'S') z++;
				if (m[l][c] == 'A' && m[l - 1][c - 1] == 'M' && m[l + 1][c - 1] == 'S' && m[l - 1][c + 1] == 'M' && m[l + 1][c + 1] == 'S') z++;
				if (m[l][c] == 'A' && m[l - 1][c - 1] == 'S' && m[l + 1][c - 1] == 'M' && m[l - 1][c + 1] == 'S' && m[l + 1][c + 1] == 'M') z++;
				if (m[l][c] == 'A' && m[l - 1][c - 1] == 'S' && m[l + 1][c - 1] == 'S' && m[l - 1][c + 1] == 'M' && m[l + 1][c + 1] == 'M') z++;
			}
		}
		S.o(z);
	}
}