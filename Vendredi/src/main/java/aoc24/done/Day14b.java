package aoc24.done;

import tools.collections.int32.L;
import tools.collections.multi.LLi;
import tools.scanner.list.ScanLs;
import tools.strings.S;
import tools.tables.Table;

public class Day14b {
	public static void main(String[] args) {
		LLi data = new LLi();
		for (var line: ScanLs.readRaw()) data.add(new L(line));
		for (long t = 0; t < 10403; t++) {
			int z = 0;
			for (var l: data) {
				long x = Math.floorMod(l.get(0) + l.get(2) * t, 101);
				long y = Math.floorMod(l.get(1) + l.get(3) * t, 103);
				if (x > 40 && x < 60 && y > 40 && y < 60) z++;
			}
			if (z > 100) {
				var map = new int[103][101];
				for (var l: data)
					map[Math.floorMod(l.get(1) + l.get(3) * t, 103)]
							[Math.floorMod(l.get(0) + l.get(2) * t, 101)] = '#';
				Table.printMap(map);
				S.o(t);
				return;
			}
		}
	}
}
