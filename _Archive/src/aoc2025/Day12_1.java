package aoc2025;

import tools.collections.int32.L;
import tools.collections.string.Ls;
import tools.scanner.Scan;
import tools.scanner.list.ScanLs;
import tools.strings.S;

public class Day12_1 {
	public static void main(String[] args) {
		int[] ws = { 6, 5, 7, 7, 7, 7 };
		Scan.skipLines(30);
		L l = new L();
		Ls in = ScanLs.readRaw();
		for (String s: in) {
			String[] tks = s.split(": ");
			String[] sa = tks[0].split("x");
			int area = Integer.parseInt(sa[0]) * Integer.parseInt(sa[1]);
			String[] sb = tks[1].split(" ");
			int w = 0;
			for (int i = 0; i < 6; i++) w += Integer.parseInt(sb[i]) * ws[i];
			l.add(w * 10_000 / area);
		}
		l = l.sortedUp();
		for (int i = 0; i < l.size(); i++) S.o(i + 1, l.get(i));
	}
}
