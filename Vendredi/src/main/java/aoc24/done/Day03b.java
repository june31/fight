package aoc24.done;

import tools.collections.int32.L;
import tools.scanner.list.ScanLs;
import tools.strings.S;

public class Day03b {
	public static void main(String[] args) {
		var l = ScanLs.readRaw();
		long z = 0;
		boolean d = true;
		for (String x : l) {
			while (S.indexOfRegex(x, "mul\\(\\d+,\\d+\\)") >= 0 || S.indexOfRegex(x, "do\\(\\)") >= 0 || S.indexOfRegex(x, "don't\\(\\)") >= 0) {
				int p = S.indexOfRegex(x, "mul\\(\\d+,\\d+\\)");
				int q = S.indexOfRegex(x, "do\\(\\)");
				int r = S.indexOfRegex(x, "don't\\(\\)");
				if (p == -1) p = Integer.MAX_VALUE;
				if (q == -1) q = Integer.MAX_VALUE;
				if (r == -1) r = Integer.MAX_VALUE;
				if (p < q && p < r) {
					x = x.substring(p);
					x = x.substring(x.indexOf('(') + 1);
					String y = x.substring(0,  x.indexOf(')'));
					if (d) z += new L(y).mul();
				} else {
					x = x.substring(1);
					d = q < r;
				}
			}
		}
		S.o(z);
	}
}
