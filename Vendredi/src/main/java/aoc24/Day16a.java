package aoc24;

import tools.collections.pos.Lp3;
import tools.math.Num;
import tools.scanner.Scan;
import tools.strings.S;
import tools.tables.Table;
import tools.tuple.Pos;
import tools.tuple.Pos3;

public class Day16a {
	public static void main(String[] args) {
		var map = Scan.readRawMap();
		int nl = map.length;
		int nc = map[0].length;
		var mins = new long[4][nl][nc];
		for (int i = 0; i < 4; i++) {
			for (int l = 0; l < nl; l++) {
				for (int c = 0; c < nc; c++) {
					mins[i][l][c] = Long.MAX_VALUE;
				}
			}
		}
		int[] dirX = { 1, 0, -1, 0 };
		int[] dirY = { 0, 1, 0, -1 };
		Pos start = Table.find(map, 'S');
		mins[0][start.l][start.c] = 0; 
		Lp3 lp = Lp3.of(new Pos3(start.c, start.l, 0));
		while (!lp.isEmpty()) {
			Lp3 next = new Lp3();
			for (Pos3 p: lp) {
				long v = mins[p.z][p.y][p.x];
				long vr = mins[(p.z + 1) % 4][p.y][p.x];
				if (v + 1000 < vr) {
					mins[(p.z + 1) % 4][p.y][p.x] = v + 1000;
					next.add(new Pos3(p.x, p.y, (p.z + 1) % 4));
				}
				long vl = mins[(p.z + 3) % 4][p.y][p.x];
				if (v + 1000 < vl) {
					mins[(p.z + 3) % 4][p.y][p.x] = v + 1000;
					next.add(new Pos3(p.x, p.y, (p.z + 3) % 4));
				}
				int dx = dirX[p.z];
				int dy = dirY[p.z];
				if (map[p.y + dy][p.x + dx] != '#') {
					long vx = mins[p.z][p.y + 2 * dy][p.x + 2 * dx];
					if (v + 2 < vx) {
						mins[p.z][p.y + 2 * dy][p.x + 2 * dx] = v + 2;
						next.add(new Pos3(p.x + 2 * dx, p.y + 2 * dy, p.z));
					}
				}
			}
			lp = next;
		}
		
		S.o(Num.min(mins[0][1][nc - 2], mins[3][1][nc - 2]));
	}
}
