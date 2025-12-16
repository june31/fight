package aoc2025;

import tools.collections.int32.L;
import tools.scanner.Scan;
import tools.strings.S;

public class Day03_1 {
	public static void main(String[] args) {
		long z = 0;
		var map = Scan.readMapIntRaw();
		for (int i = 0; i < map.length; i++) {
			L l = new L(map[i]);
			var ii = l.subbed(0, -1).maxII();
			z += ii.v * 10 + l.subbed(ii.i + 1).max();
		}
		S.o(z);
	}
}
