package aoc.done;

import tools.collections.int32.L;
import tools.collections.pos.Lp;
import tools.scanner.Scan;
import tools.tables.Table;
import tools.tuple.Pos;

public class Day_03_2 {
	
	static int[][] map = Scan.readMap0();
	
	public static void main(String[] args) {
		Pos[] ps = Table.findAll(map, '*');
		int s = 0;
		for (Pos p: ps) {
			Lp already = new Lp();
			L ns = new L();
			addNb(ns, already, new Pos(p.l, p.c + 1));
			addNb(ns, already, new Pos(p.l + 1, p.c + 1));
			addNb(ns, already, new Pos(p.l + 1, p.c));
			addNb(ns, already, new Pos(p.l + 1, p.c - 1));
			addNb(ns, already, new Pos(p.l, p.c - 1));
			addNb(ns, already, new Pos(p.l - 1, p.c - 1));
			addNb(ns, already, new Pos(p.l - 1, p.c));
			addNb(ns, already, new Pos(p.l - 1, p.c + 1));
			if (ns.size() == 2) s += ns.get(0) * ns.get(1);
		}
		
		System.out.println(s);
	}

	private static void addNb(L ns, Lp already, Pos p) {
		if (already.contains(p)) return;
		if (!digit(p)) return;
		int c1 = p.c;
		while (digit(p.l, c1)) {
			already.add(new Pos(p.l, c1));
			c1--;
		}
		int c2 = p.c;
		while (digit(p.l, c2)) {
			already.add(new Pos(p.l, c2));
			c2++;
		}
		int x = 0;
		for (int i = c1 + 1; i < c2; i++) {
			x *= 10;
			x += map[p.l][i] - '0';
		}
		ns.add(x);
	}

	private static boolean digit(Pos p) { return digit(Table.get(map, p)); }
	private static boolean digit(int l, int c) { return digit(Table.get(map, l, c)); }
	private static boolean digit(int i) { return i >= '0' && i <= '9'; }
}
