package aoc24.done;

import tools.collections.int32.L;
import tools.scanner.list.ScanLs;
import tools.strings.S;

public class Day03a {
	public static void main(String[] args) {
		var l = ScanLs.readRaw();
		long z = 0;
		for (String x : l) {
			while (S.indexOfRegex(x, "mul\\(\\d+,\\d+\\)") >= 0) {
				int p = S.indexOfRegex(x, "mul\\(\\d+,\\d+\\)");
				x = x.substring(p);
				x = x.substring(x.indexOf('(') + 1);
				String y = x.substring(0,  x.indexOf(')'));
				z += new L(y).mul();
			}
		}
		S.o(z);
	}
}
