package aoc24;

import tools.collections.string.Ls;
import tools.scanner.Scan;
import tools.scanner.list.ScanLs;
import tools.strings.S;

public class Day19b {
	private static Ls des = new Ls(Scan.readLine().split(", "));
	public static void main(String[] args) {
		long z = 0;
		Scan.readLine();
		var ls = ScanLs.readRaw();
		for (var x: ls) z += check(x);
		S.o(z);
	}
	private static long check(String s) {
		if (s.isEmpty()) return 1;
		long r = 0;
		for (var a: des) if (s.startsWith(a)) r += check(s.substring(a.length()));
		return r;
	}
}
