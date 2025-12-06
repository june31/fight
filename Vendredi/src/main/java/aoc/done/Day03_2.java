package aoc.done;

import tools.collections.int32.L;
import tools.scanner.Scan;
import tools.strings.S;

public class Day03_2 {
	public static void main(String[] args) {
		long z = 0;
		var map = Scan.readMapIntRaw();
		for (int i = 0; i < map.length; i++) {
			long s = 0;
			L l = new L(map[i]);
			for (int j = 11; j >= 0; j--) {
				var ii = l.subbed(0, l.size()-j).maxII();
				s = s * 10 + ii.v;
				l = l.subbed(ii.i + 1);
			}
			z += s;
		}
		S.o(z);
	}
}
