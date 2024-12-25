package aoc24;

import tools.math.Num;
import tools.scanner.list.ScanLs;
import tools.strings.S;

public class Day13b {
	public static void main(String[] args) {
		long z = 0;
		double h = 10_000_000_000_000l;
		while (true) {
			var ls = ScanLs.readRaw();
			if (ls.isEmpty()) break;
			var t = ls.get(0).split(". ..");
			double ax = S.d(t[1].substring(2));
			double ay = S.d(t[2]);
			t = ls.get(1).split(". ..");
			double bx = S.d(t[1].substring(2));
			double by = S.d(t[2]);
			t = ls.get(2).split(". ..");
			double x = S.d(t[1]) + h;
			double y = S.d(t[2]) + h;
			double det = ax*by - ay*bx;
			if (det == 0) S.o("---");
			else {
				double i = (x*by - y*bx) / det;
				double j = (y*ax - x*ay) / det;
				if (Num.isInt(i) && Num.isInt(j)) {
					long ii = (long) Math.round(i);
					long jj = (long) Math.round(j);
					z += 3 * ii + jj; 
				}
			}
		}
		S.o(z);
	}
}
