package aoc2025;

import tools.collections.int64.Sl;
import tools.scanner.list.ScanLs;
import tools.strings.S;

public class Day02_2 {
	public static void main(String[] args) {
		long z = 0;
		String in = ScanLs.readRaw().join("");
		for (String s: in.split(",")) {
			long a = S.l(s.substring(0, s.indexOf("-")));
			long b = S.l(s.substring(s.indexOf("-") + 1));
			z -= f(a-1);
			z += f(b);
		}
		S.o(z);
	}

	private static long f(long x) {
		Sl sl = new Sl();
		long z = 0;
		for (int i = 1;; i++) {
			boolean bk = true;
			for (int j = 2; j < 99; j++) {
				long y = S.l(S.mul("" + i, j));
				if (y > x) break;
				bk = false;
				if (sl.contains(y)) continue;
				sl.add(y);
				z += y;
			}
			if (bk) break;
		}
		return z;
	}
}
