package aoc24;

import tools.scanner.Scan;
import tools.scanner.list.ScanLs;
import tools.strings.S;
import tools.tables.Table;
import tools.tuple.Pos;

public class Day15a {
	private static long z = 0;
	public static void main(String[] args) {
		var map = Scan.readMapRaw();
		var ls = ScanLs.readRaw();
		Table.printMap(map);
		Pos r = Table.find(map, '@');
		var cp = Table.copy(map);
		Table.set(map, r, '.');
		for (var s : ls) {
			for (var c: s.toCharArray()) {
				if (c == '^' && Table.get(map, r.up()) == '.') r = r.up();
				else if (c == 'v' && Table.get(map, r.down()) == '.')
					r = r.down();
				else if (c == '<' && Table.get(map, r.left()) == '.')
					r = r.left();
				else if (c == '>' && Table.get(map, r.right()) == '.')
					r = r.right();
				else if (c == '^' && Table.get(map, r.up()) == 'O') {
					Pos a = r.up();
					while (Table.get(map, a) == 'O') a = a.up();
					if (Table.get(map, a) == '.') {
						Table.set(map, a, 'O');
						r = r.up();
						Table.set(map, r, '.');
					}
				}
				else if (c == 'v' && Table.get(map, r.down()) == 'O') {
                    Pos a = r.down();
                    while (Table.get(map, a) == 'O') a = a.down();
                    if (Table.get(map, a) == '.') {
                        Table.set(map, a, 'O');
                        r = r.down();
                        Table.set(map, r, '.');
                    }
                }
                else if (c == '<' && Table.get(map, r.left()) == 'O') {
                    Pos a = r.left();
                    while (Table.get(map, a) == 'O') a = a.left();
                    if (Table.get(map, a) == '.') {
                        Table.set(map, a, 'O');
                        r = r.left();
                        Table.set(map, r, '.');
                    }
                }
                else if (c == '>' && Table.get(map, r.right()) == 'O') {
                    Pos a = r.right();
                    while (Table.get(map, a) == 'O') a = a.right();
                    if (Table.get(map, a) == '.') {
                        Table.set(map, a, 'O');
                        r = r.right();
                        Table.set(map, r, '.');
                    }
                }
				cp = Table.copy(map);
				Table.set(cp, r, '@');
				S.o();
				S.o(c);
				Table.printMap(cp);
			}
		}
		for (Pos p: Table.findAll(map, 'O')) z += p.l * 100 + p.c;
		S.o(z);
	}
}
