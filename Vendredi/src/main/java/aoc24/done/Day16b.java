package aoc24.done;

import java.util.ArrayList;
import java.util.List;

import tools.collections.pos.Lp3;
import tools.math.Num;
import tools.scanner.Scan;
import tools.strings.S;
import tools.tables.Table;
import tools.tuple.Pos;
import tools.tuple.Pos3;

public class Day16b {
	public static void main(String[] args) {
		var map = Scan.readRawMap();
		int nl = map.length;
		int nc = map[0].length;
		var mins = new int[4][nl][nc];
		for (int i = 0; i < 4; i++) {
			for (int l = 0; l < nl; l++) {
				for (int c = 0; c < nc; c++) {
					mins[i][l][c] = Integer.MAX_VALUE;
				}
			}
		}
		int[] dirX = { 1, 0, -1, 0 };
		int[] dirY = { 0, 1, 0, -1 };
		Pos start = Table.find(map, 'S');
		mins[0][start.l][start.c] = 0; 
		List<P3H> lp = List.of(new P3H(start.c, start.l, 0, 0, null));
		List<P3H> target = new ArrayList<P3H>();
		while (!lp.isEmpty()) {
			List<P3H> next = new ArrayList<P3H>();
			for (P3H p: lp) {
				int v = mins[p.z][p.y][p.x];
				int vr = mins[(p.z + 1) % 4][p.y][p.x];
				if (v + 1000 <= vr) {
					mins[(p.z + 1) % 4][p.y][p.x] = v + 1000;
					next.add(new P3H(p.x, p.y, (p.z + 1) % 4, v + 1000, p));
				}
				int vl = mins[(p.z + 3) % 4][p.y][p.x];
				if (v + 1000 <= vl) {
					mins[(p.z + 3) % 4][p.y][p.x] = v + 1000;
					next.add(new P3H(p.x, p.y, (p.z + 3) % 4, v + 1000, p));
				}
				int dx = dirX[p.z];
				int dy = dirY[p.z];
				if (map[p.y + dy][p.x + dx] != '#') {
					int vx = mins[p.z][p.y + 2 * dy][p.x + 2 * dx];
					if (v + 2 <= vx) {
						mins[p.z][p.y + 2 * dy][p.x + 2 * dx] = v + 2;
						next.add(new P3H(p.x + 2 * dx, p.y + 2 * dy, p.z, v + 2, p));
					}
				}
			}
			for (P3H p: next) {
				if (p.x == nc - 2 && p.y == 1) target.add(p);
			}
			lp = next;
		}
		
		int high = Num.min(mins[0][1][nc - 2], mins[3][1][nc - 2]);
		S.o(high);
		
		for (var p: target) {
			if (p.score == high) {
				P3H old = p; 
				while (p != null) {
					map[(old.y + p.y) / 2][(old.x + p.x) / 2] = 'O';
					map[p.y][p.x] = 'O';
					old = p;
					p = p.histo;
				}
			}
		}
		
		Table.printMap(map);
		S.o(Table.findAll(map, 'O').length);
	}
	
	record P3H(int x, int y, int z, int score, P3H histo) {}
}
