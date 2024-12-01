package aoc24.done;

import tools.scanner.list.ScanLii;
import tools.strings.S;

public class Day01b {
	public static void main(String[] args) {
		var l = ScanLii.readRaw();
		var m = l.getValues().counts();
		long z = 0;
		for (var a: l) {
			var x = m.get(a.index);
			if (x != null) z += a.index * x;
		}
		S.o(z);
	}
}
