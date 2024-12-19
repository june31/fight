package aoc24.done;

import java.util.HashMap;
import java.util.Map;

import tools.collections.string.Ls;
import tools.scanner.Scan;
import tools.scanner.list.ScanLs;
import tools.strings.S;

public class Day19a {
	private static long z = 0;
	private static Ls des = new Ls(Scan.readLine().split(", "));
	private static Map<String, Boolean> memo = new HashMap<>();
	public static void main(String[] args) {
		Scan.readLine();
		var ls = ScanLs.readRaw();
		for (var x: ls) {
			memo.clear();
			if (check(x)) z++;
		}
		S.o(z);
	}
	private static boolean check(String s) {
		if (s.isEmpty()) return true;
		Boolean h = memo.get(s);
		if (h != null) return h;
		boolean r = false;
		for (var a: des) {
			if (s.startsWith(a)) r |= check(s.substring(a.length()));
		}
		memo.put(s, r);
		return r;
	}
}
