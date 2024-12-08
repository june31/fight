package aoc24;

import tools.collections.pos.Lp;
import tools.enumeration.combinations.MixArrangements;
import tools.scanner.Scan;
import tools.strings.S;
import tools.tables.Table;
import tools.tuple.Pos;

public class Day08a {
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
		for (var t: new MixArrangements<Pos>(ps, 2)) {
			Pos p1 = t.get(0);
			Pos p2 = t.get(1);
			int l = 2 * p2.l - p1.l;
			int c = 2 * p2.c - p1.c;
			if (l < 0 || l >= map.length || c < 0 || c >= map[0].length) continue;
			if (map2[l][c] != '#') {
				map2[l][c] = '#';
				z++;
			}
		}
	}
}
