package aoc24;

import tools.collections.int32.L;
import tools.scanner.list.ScanLs;
import tools.strings.S;

public class Day14a {
	public static void main(String[] args) {
		long ul = 0, ur = 0, dl = 0, dr = 0;
		for (var line: ScanLs.readRaw()) {
			L l = new L(line);
			long x = Math.floorMod(l.get(0) + l.get(2) * 100l, 101);
			long y = Math.floorMod(l.get(1) + l.get(3) * 100l, 103);
			if (x < 50 && y < 51) ul++;
			if (x < 50 && y > 51) dl++;
			if (x > 50 && y < 51) ur++;
			if (x > 50 && y > 51) dr++;
		}
		S.o(ul * ur * dl * dr);
	}
}
