package aoc24;

import tools.enumeration.combinations.Combinations;
import tools.math.Num;
import tools.scanner.Scan;
import tools.strings.S;
import tools.tables.Table;
import tools.tuple.Pos;

public class Day08b {
	public static void main(String[] args) {
		var map = Scan.readMapRaw();
		var map2 = Table.copy(map);
		for (int i = '0'; i <= 'z'; i++) {
			for (var t: new Combinations<Pos>(Table.findAll(map, i), 2)) {
				Pos p1 = t.get(0);
				Pos p2 = t.get(1);
				int dl = p2.l - p1.l;
				int dc = p2.c - p1.c;
				if (dc == 0) for (int l = 0; l < map.length; l++) map2[l][p1.c] = '#';
				else {
					double pente = ((double) dl) / dc;
					double l = p1.l - pente * p1.c;
					for (int c = 0; c < map[0].length; c++, l += pente) {
						if (!Num.isInt(l)) continue;
						int ll = (int) Math.round(l);
						Table.set(map2, ll, c, '#');
					}
				}
			}
		}
		S.o(Table.findAll(map2, '#').length);
	}
}
