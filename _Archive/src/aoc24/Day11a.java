package aoc24;

import tools.collections.string.Ls;
import tools.collections.string.Ss;
import tools.scanner.list.ScanLs;
import tools.strings.S;

public class Day11a {
	private static Ls ls = ScanLs.readLine();
	public static void main(String[] args) {
		for (int i = 0; i < 25; i++) {
			blink();
		}
		S.o(ls.size());
		S.o(new Ss(ls).size());
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
