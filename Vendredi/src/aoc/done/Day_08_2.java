package aoc.done;

import java.util.LinkedHashMap;

import tools.collections.int64.Ll;
import tools.scanner.Scan;

public class Day_08_2 {

	public static void main(String[] args) {
		char[] cs = Scan.readLine().toCharArray();
		Scan.readLine();
		String[] in = Scan.readRawStrings();
		var map = new LinkedHashMap<String, N>();
		for (String s: in) {
			String[] t = s.split(" = \\(");
			N n = new N();
			n.name = t[0];
			n.c = t[1].substring(0, t[1].length() - 1).split(", ");
			map.put(t[0], n);
		}
		for (var n: map.values()) {
			n.l = map.get(n.c[0]);
			n.r = map.get(n.c[1]);
		}

		Ll list = new Ll(); 
		for (N n: map.values()) {
			if (!n.name.endsWith("A")) continue;
			long s = 0;
			do {
				for (char c: cs) {
					if (c == 'L') n = n.l; else n = n.r;
					s++;
				}
			} while (!n.name.endsWith("Z"));
			list.add(s);
		}

		System.out.println(list.lcm());
	}

	private static class N { String name; N l; N r; String[] c; }
}
