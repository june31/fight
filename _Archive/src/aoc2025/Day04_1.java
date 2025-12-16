package aoc2025;

import tools.scanner.Scan;
import tools.strings.S;
import tools.tables.Table;

public class Day04_1 {
	public static void main(String[] args) {
		long z = 0;
		var map = Table.wall(Scan.readMapRaw(), '.');
		for (int l = 1; l < map.length - 1; l++) {
			for (int c = 1; c < map[l].length - 1; c++) {
				if (map[l][c] == '@') {
					int n = 0;
					if (map[l - 1][c] == '@') n++;
					if (map[l + 1][c] == '@') n++;
					if (map[l][c - 1] == '@') n++;
					if (map[l][c + 1] == '@') n++;
					if (map[l+1][c + 1] == '@') n++;
					if (map[l-1][c - 1] == '@') n++;
					if (map[l+1][c - 1] == '@') n++;
					if (map[l-1][c + 1] == '@') n++;
					if (n < 4) z++;
				}
			}
		}
		S.o(z);
	}
}
