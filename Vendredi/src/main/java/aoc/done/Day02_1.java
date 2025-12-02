package aoc.done;

import tools.scanner.list.ScanLs;
import tools.strings.S;

public class Day02_1 {
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
		long z = 0;
		for (int i = 1;; i++) {
			long y = S.l(i + "" + i);
			if (y <= x) z += y; else break;
		}
		return z;
	}
}
