package aoc.done;

import java.util.HashMap;
import java.util.Map;

import tools.collections.int32.L;
import tools.scanner.Scan;

public class Day_12_1 {

	private static Map<String, Long> memo;

	public static void main(String[] args) {
		String[] in = Scan.readRawStrings();
		memo = new HashMap<>();
		long s = 0;
		for (var line: in) {
			System.out.println(line);
			String[] tk = line.split(" ");
			L vs = new L(tk[1]);
			String t = tk[0].replace('.', ' ').trim();
			long ss = val(t, vs);
			System.out.println(ss);
			s += ss;
		}

		System.out.println(s);
	}

	private static long val(String t, L vs) {
		if (t.isEmpty()) return vs.isEmpty() ? 1 : 0;
		Long rem = memo.get(t + vs);
		if (rem != null) return rem;
		long s = 0;
		char c = t.charAt(0);
		if (c == ' ' || c == '?') s += val(t.substring(1), vs);
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
					if (k != ' ' && k != '?') fine = false;
				}
				if (fine) s += val(t.length() > v ? t.substring(v + 1) : "", vs.sub(1));
			}
		}

		memo.put(t + vs, s);
		return s;
	}
}
