package aoc24;

import tools.scanner.Scan;
import tools.strings.S;
import tools.tables.Table;
import tools.tuple.Pos;

public class Day10b {
	private static long z = 0;
	private static int[][] map = Scan.readMapRaw();
	public static void main(String[] args) {
		for (Pos p: Table.findAll(map, '0')) recurse(p);
		S.o(z);
	}

	private static void recurse(Pos p) {
		int v = Table.get(map, p);
		if (v == '9') z++;
		if (Table.get(map, p.up()) == v + 1) recurse(p.up());
		if (Table.get(map, p.down()) == v + 1) recurse(p.down());
		if (Table.get(map, p.left()) == v + 1) recurse(p.left());
		if (Table.get(map, p.right()) == v + 1) recurse(p.right());
	}
}
