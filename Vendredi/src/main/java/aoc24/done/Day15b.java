package aoc24.done;

import tools.collections.pos.Lp;
import tools.collections.pos.Sp;
import tools.scanner.Scan;
import tools.scanner.list.ScanLs;
import tools.strings.S;
import tools.tables.Table;
import tools.tuple.Pos;

public class Day15b {
	private static long z = 0;
	private static int[][] map;
	public static void main(String[] args) {
		var map1 = Scan.readRawMap();
		int nl = map1.length;
		int nc = map1[0].length;
		map = new int[nl][nc * 2];
		for (int l = 0; l < nl; l++) {
			for (int c = 0; c < nc; c++) {
				int i = map1[l][c];
				if (i == '#') {
					map[l][c * 2] = '#';
					map[l][c * 2 + 1] = '#';
				} else if (i == 'O') {
					map[l][c * 2] = '[';
					map[l][c * 2 + 1] = ']';
				} else if (i == '.') {
					map[l][c * 2] = '.';
					map[l][c * 2 + 1] = '.';
				} else {
					map[l][c * 2] = '@';
					map[l][c * 2 + 1] = '.';
				}
			}
		}
		
		var ls = ScanLs.readRaw();
		Table.printMap(map);
		Pos r = Table.find(map, '@');
		var cp = Table.copy(map);
		Table.set(map, r, '.');
		for (var s : ls) {
			for (var c: s.toCharArray()) {
				S.o();
				S.o(c);
				if (c == '^' && Table.get(map, r.up()) == '.') r = r.up();
				else if (c == 'v' && Table.get(map, r.down()) == '.')
					r = r.down();
				else if (c == '<' && Table.get(map, r.left()) == '.')
					r = r.left();
				else if (c == '>' && Table.get(map, r.right()) == '.')
					r = r.right();
				
				
				else if (c == '^' && Table.get(map, r.up()) > 'Z') {
					Sp sp = tryPushUp(r);
					if (sp != null) {
						Lp lp = new Lp(sp);
						lp.sort((p1, p2) -> p1.l - p2.l);
						for (Pos p: lp) {
							int i = Table.get(map, p);
							Table.set(map, p, '.');
							Table.set(map, p.up(), i);
						}
						r = r.up();
					}
				}
				else if (c == 'v' && Table.get(map, r.down()) > 'Z') {
					Sp sp = tryPushDown(r);
					if (sp != null) {
						Lp lp = new Lp(sp);
						lp.sort((p1, p2) -> p2.l - p1.l);
						for (Pos p: lp) {
							int i = Table.get(map, p);
							Table.set(map, p, '.');
							Table.set(map, p.down(), i);
						}
						r = r.down();
					}
                }
                else if (c == '<' && Table.get(map, r.left()) > 'Z') {
                    Pos a = r.left();
                    while (Table.get(map, a) > 'Z') a = a.left();
                    if (Table.get(map, a) == '.') {
                        r = r.left();
                        Table.set(map, r, '.');
                        while (!a.equals(r)) {
                        	Table.set(map, a, '[');
                        	a = a.right();
                        	Table.set(map, a, ']');
                        	a = a.right();
                        }
                    }
                }
                else if (c == '>' && Table.get(map, r.right()) > 'Z') {
                    Pos a = r.right();
                    while (Table.get(map, a) > 'Z') a = a.right();
                    if (Table.get(map, a) == '.') {
                        r = r.right();
                        Table.set(map, r, '.');
                        while (!a.equals(r)) {
                        	Table.set(map, a, ']');
                        	a = a.left();
                        	Table.set(map, a, '[');
                        	a = a.left();
                        }
                    }
                }
				cp = Table.copy(map);
				Table.set(cp, r, '@');
				Table.printMap(cp);
			}
		}
		for (Pos p: Table.findAll(map, '[')) z += p.l * 100 + p.c;
		S.o(z);
	}
	
	private static Sp tryPushUp(Pos p) {
		Pos p2 = p.up();
		int x = Table.get(map, p2);
		if (x == '#') return null;
		Sp sp = new Sp();
		sp.add(p);
		if (x == '[') {
			Sp spl = tryPushUp(p2);
			if (spl == null) return null;
			sp.addAll(spl);
			Sp spr = tryPushUp(p2.right());
			if (spr == null) return null;
			sp.addAll(spr);
			return sp;
		}
		if (x == ']') {
			Sp spl = tryPushUp(p2.left());
			if (spl == null) return null;
			sp.addAll(spl);
			Sp spr = tryPushUp(p2);
			if (spr == null) return null;
			sp.addAll(spr);
			return sp;
		}
		return sp;
	}
	
	private static Sp tryPushDown(Pos p) {
		Pos p2 = p.down();
		int x = Table.get(map, p2);
		if (x == '#') return null;
		Sp sp = new Sp();
		sp.add(p);
		if (x == '[') {
			Sp spl = tryPushDown(p2);
			if (spl == null) return null;
			sp.addAll(spl);
			Sp spr = tryPushDown(p2.right());
			if (spr == null) return null;
			sp.addAll(spr);
			return sp;
		}
		if (x == ']') {
			Sp spl = tryPushDown(p2.left());
			if (spl == null) return null;
			sp.addAll(spl);
			Sp spr = tryPushDown(p2);
			if (spr == null) return null;
			sp.addAll(spr);
			return sp;
		}
		return sp;
	}
}
