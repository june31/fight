package aoc24.done;

import tools.collections.map.Mii;
import tools.scanner.list.ScanLii;
import tools.strings.S;

public class Day01b {
	public static void main(String[] args) {
		var l = ScanLii.readRaw();
		var m = new Mii(l.getValues().counts());
		long z = 0;
		for (var a: l.getIndexes()) z += a * m.getOrZero(a);
		S.o(z);
	}
}
