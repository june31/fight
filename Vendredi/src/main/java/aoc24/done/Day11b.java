package aoc24.done;

import tools.chrono.Chrono;
import tools.collections.string.Ls;
import tools.collections.string.Ss;
import tools.scanner.list.ScanLs;
import tools.strings.S;

public class Day11b {
	private static Ls ls = ScanLs.readLine();
	public static void main(String[] args) {
		Chrono.start();
		long z = 0;
		for (int i = 0; i < 37; i++) blink();
		S.o(ls.size());
		S.o(new Ss(ls).size());
		var p = ls.countAll();
		int id = 0;
		for (var e:p.entrySet()) {
			ls.clear();
			ls.add(e.getKey());
			for (int i = 0; i < 38; i++) blink();
			z += ls.size() * (long) e.getValue();
			S.e(++id);
		}
		S.o(z);
		Chrono.stop();
	}
	private static void blink() {
		Ls l = new Ls();
		for (String s: ls) {
			while (s.startsWith("0")) s = s.substring(1);
			if (s.equals("")) l.add("1");
			else if (s.length() % 2 == 0) {
				l.add(s.substring(0, s.length()/2));
				l.add(s.substring(s.length()/2));
			}
			else {
				l.add("" + (S.l(s) * 2024l));
			}
		}
		ls = l;
	}
}
