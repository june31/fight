package aoc24;

import tools.scanner.Scan;
import tools.strings.S;
import tools.tables.Table;

public class Day04a {
	public static void main(String[] args) {
		var m = Table.wall(Scan.readMapRaw(), 5, '#');
		long z = 0;
		for (int l = 5; l < m.length - 5; l++) {
			for (int c = 5; c < m[0].length - 5 ; c++) {
				if (m[l][c] == 'X' && m[l + 1][c] == 'M' && m[l + 2][c] == 'A' && m[l + 3][c] == 'S') z++;
				if (m[l][c] == 'X' && m[l][c + 1] == 'M' && m[l][c + 2] == 'A' && m[l][c + 3] == 'S') z++;
				if (m[l][c] == 'X' && m[l + 1][c + 1] == 'M' && m[l + 2][c + 2] == 'A' && m[l + 3][c + 3] == 'S') z++;
				if (m[l][c] == 'X' && m[l - 1][c + 1] == 'M' && m[l - 2][c + 2] == 'A' && m[l - 3][c + 3] == 'S') z++;
				if (m[l][c] == 'X' && m[l - 1][c] == 'M' && m[l - 2][c] == 'A' && m[l - 3][c] == 'S') z++;
				if (m[l][c] == 'X' && m[l][c - 1] == 'M' && m[l][c - 2] == 'A' && m[l][c - 3] == 'S') z++;
				if (m[l][c] == 'X' && m[l + 1][c - 1] == 'M' && m[l + 2][c - 2] == 'A' && m[l + 3][c - 3] == 'S') z++;
				if (m[l][c] == 'X' && m[l - 1][c - 1] == 'M' && m[l - 2][c - 2] == 'A' && m[l - 3][c - 3] == 'S') z++;
			}
		}
		S.o(z);
	}
}
