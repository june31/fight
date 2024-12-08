package aoc24;

import tools.collections.pos.Lp;
import tools.enumeration.combinations.MixCombinations;
import tools.math.Num;
import tools.scanner.Scan;
import tools.strings.S;
import tools.tables.Table;
import tools.tuple.Pos;

public class Day08b {
	static int[][] map = Scan.readRawMap();
	static int[][] map2 = Table.copy(map);
	static long z = 0;
	public static void main(String[] args) {
		for (int i = '0'; i <= '9'; i++) check(i);
		for (int i = 'A'; i <= 'Z'; i++) check(i);
		for (int i = 'a'; i <= 'z'; i++) check(i);
		S.o(z);
	}
	
	private static void check(int i) {
		Lp ps = new Lp(Table.findAll(map, i));
		for (var t: new MixCombinations<Pos>(ps, 2)) {
			Pos p1 = t.get(0);
			Pos p2 = t.get(1);
			int dl = p2.l - p1.l;
			int dc = p2.c - p1.c;
			if (dc == 0) {
				for (int l = 0; l < map.length; l++) {
					if (map2[l][p1.c] != '#') {
						map2[l][p1.c] = '#';
						z++;
					}
				}
			} else {
				double pente = ((double) dl) / dc;
				double l = p1.l - pente * p1.c;
				for (int c = 0; c < map[0].length; c++, l += pente) {
					if (!Num.isInt(l)) continue;
					int ll = (int) Math.round(l);
					if (ll < 0 || ll >= map.length) continue;
					if (map2[ll][c] != '#') {
						map2[ll][c] = '#';
						z++;
					}
				}
			}
		}
	}
}
