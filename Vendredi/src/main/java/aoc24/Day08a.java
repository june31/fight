package aoc24;

import tools.enumeration.combinations.Arrangements;
import tools.scanner.Scan;
import tools.strings.S;
import tools.tables.Table;
import tools.tuple.Pos;

public class Day08a {
	public static void main(String[] args) {
		var map = Scan.readRawMap();
		var hashes = Table.copy(map);
		for (int i = '0'; i <= 'z'; i++)
			for (var t: new Arrangements<Pos>(Table.findAll(map, i), 2))
				Table.set(hashes, 2 * t.get(1).l - t.get(0).l, 2 * t.get(1).c - t.get(0).c, '#');
		S.o(Table.findAll(hashes, '#').length);
	}
}
