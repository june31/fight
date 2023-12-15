package aoc.done;

import java.util.ArrayList;
import java.util.List;

import tools.scanner.Scan;

public class Day_15_2 {

	public static void main(String[] args) {
		String[] in = Scan.readString().split(",");
		List<List<SI>> t = new ArrayList<>();
		for (int i = 0; i < 256; i++) t.add(new ArrayList<>());
		loop: for (var ss: in) {
			if (ss.endsWith("-")) {
				ss = ss.substring(0, ss.length() - 1);
				int s = 0;
				for (char c: ss.toCharArray()) {
					s += ((int) c);
					s *= 17;
					s %= 256;
				}
				var l = t.get(s);
				for (SI x: l) {
					if (x.s.equals(ss)) {
						l.remove(x);
						break;
					}
				}
			} else {
				String[] tk = ss.split("=");
				int z = Integer.parseInt(tk[1]);
				int s = 0;
				for (char c: tk[0].toCharArray()) {
					s += ((int) c);
					s *= 17;
					s %= 256;
				}
				var l = t.get(s);
				for (SI x: l) {
					if (x.s.equals(tk[0])) {
						x.v = z;
						continue loop;
					}
				}
				l.add(new SI(tk[0], z));
			}
		}
		
		long h = 0;
		for (int i = 0; i < 256; i++) {
			var l = t.get(i);
			for (int j = 0; j < l.size(); j++) {
				int x = (i+1) * (j+1) * l.get(j).v;
				h += x;
			}
		}

		System.out.println(h);
	}
	
	private static class SI {
		String s;
		int v;
		SI(String ss, int vv) { s = ss; v = vv; }
		public String toString() { return "[" + s + " " + v + "]"; }
	}
}
