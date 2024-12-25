package aoc24;

import java.util.HashMap;
import java.util.Map;

import tools.collections.string.Ls;
import tools.scanner.Scan;
import tools.scanner.list.ScanLs;
import tools.strings.S;

public class Day19b {
	private static long z = 0;
	private static Ls des = new Ls(Scan.readLine().split(", "));
	private static Map<String, Long> memo = new HashMap<>();
	public static void main(String[] args) {
		Scan.readLine();
		var ls = ScanLs.readRaw();
		for (var x: ls) {
			memo.clear();
			z += check(x);
		}
		S.o(z);
	}
	private static long check(String s) {
		if (s.isEmpty()) return 1;
		Long h = memo.get(s);
		if (h != null) return h;
		long r = 0;
		for (var a: des) {
			if (s.startsWith(a)) r += check(s.substring(a.length()));
		}
		memo.put(s, r);
		return r;
	}
}
