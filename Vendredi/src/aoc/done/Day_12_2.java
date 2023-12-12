package aoc.done;

import java.util.HashMap;
import java.util.Map;

import tools.collections.int32.L;
import tools.scanner.Scan;

public class Day_12_2 {

	private static Map<String, Long> memo;

	public static void main(String[] args) {
		String[] in = Scan.readRawStrings();
		memo = new HashMap<>();
		long s = 0;
		for (var line: in) {
			String[] tk = line.split(" ");
			L mvs = new L(tk[1]);
			L vs = new L();
			for (int i = 0; i < 5; i++) vs.addAll(mvs);
			s += val(tk[0] + '?' + tk[0] + '?' + tk[0] + '?' + tk[0] + '?' + tk[0], vs);
		}
		System.out.println(s);
	}

	private static long val(String t, L vs) {
		if (t.isEmpty()) return vs.isEmpty() ? 1 : 0;
		Long rem = memo.get(t + vs);
		if (rem != null) return rem;
		long s = 0;
		char c = t.charAt(0);
		if (c == '.' || c == '?') s += val(t.substring(1), vs);
		if (c == '#' || c == '?') {
			boolean fine = !vs.isEmpty();
			if (fine) {
				int v = vs.get(0);
				fine = t.length() >= v;
				if (fine) {
					for (int i = 0; i < v; i++) {
						char k = t.charAt(i);
						if (k != '#' && k != '?') {
							fine = false;
							break;
						}
					}
				}
				if (fine && t.length() > v) {
					char k = t.charAt(v);
					if (k != '.' && k != '?') fine = false;
				}
				if (fine) s += val(t.length() > v ? t.substring(v + 1) : "", vs.sub(1));
			}
		}

		memo.put(t + vs, s);
		return s;
	}
}
