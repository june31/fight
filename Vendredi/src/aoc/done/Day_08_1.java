package aoc.done;

import java.util.LinkedHashMap;

import tools.scanner.Scan;

public class Day_08_1 {
	
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
		int s = 0;
		N n = map.get("AAA");
		do {
			for (char c: cs) {
				if (c == 'L') n = n.l; else n = n.r;
				s++;
			}
		} while (!n.name.equals("ZZZ"));
		
		System.out.println(s);
	}
	
	private static class N { String name; N l; N r; String[] c; }
}
