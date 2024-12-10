package aoc24.done;

import tools.scanner.Scan;
import tools.strings.S;
import tools.tables.Table;
import tools.tuple.Pos;

public class Day10b {
	private static long z = 0;
	private static int[][] map = Scan.readRawMap();
	public static void main(String[] args) {
		for (Pos p: Table.findAll(map, '0')) recurse(p);
		S.o(z);
	}
	
	private static void recurse(Pos p) {
		int v = Table.get(map,  p);
		if (Table.get(map, p.up()) == v + 1) {
			if (v == '8') z++;
			else recurse(p.up());
		}
		if (Table.get(map, p.down()) == v + 1) {
			if (v == '8') z++;
			else recurse(p.down());
		}
		if (Table.get(map, p.left()) == v + 1) {
			if (v == '8') z++;
			else recurse(p.left());
		}
		if (Table.get(map, p.right()) == v + 1) {
			if (v == '8') z++;
			else recurse(p.right());
		}
	}
}
