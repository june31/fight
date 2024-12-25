package aoc24;

import tools.scanner.list.ScanLs;
import tools.strings.S;

public class Day13a {
	public static void main(String[] args) {
		long z = 0;
		while (true) {
			var ls = ScanLs.readRaw();
			if (ls.isEmpty()) break;
			var t = ls.get(0).split(". ..");
			int ax = S.i(t[1].substring(2));
			int ay = S.i(t[2]);
			t = ls.get(1).split(". ..");
			int bx = S.i(t[1].substring(2));
			int by = S.i(t[2]);
			t = ls.get(2).split(". ..");
			int x = S.i(t[1]);
			int y = S.i(t[2]);
			long min = Long.MAX_VALUE;
			for (int i = 0; i <= x/ax; i++) {
				int j = (x - i*ax) % bx;
				int k = (x - i*ax) / bx;
				if (j == 0 && i*ay + k*by == y) {
					int v = 3*i+k;
					if (min > v) min = v;
				}
			}
			if (min != Long.MAX_VALUE) z += min;
		}
		S.o(z);
	}
}
