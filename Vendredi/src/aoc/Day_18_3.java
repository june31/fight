package aoc;

import java.util.ArrayList;
import java.util.Collections;

import tools.scanner.Scan;

public class Day_18_3 {

	public static void main(String[] args) {
		String[] in = Scan.readRawStrings();
		
		int minL = 0;
		int maxL = 0;

		int l = 0;
		int c = 0;
		var vsList = new ArrayList<VS>();
		long s = 0;

		for (String a: in) {
			String[] t = a.split(" ");
			int x = Integer.parseInt(t[1]);
			int d = t[0].charAt(0);
			if (d == 'R') { s += 0; c += x; System.out.println(l + "R" + (x+1)); }
			if (d == 'D') { vsList.add(new VS(c, l + 1, l + x - 1, false)); l += x; }
			if (d == 'L') { s += x + 1; c -= x; System.out.println(l + "L" + (x+1)); }
			if (d == 'U') { vsList.add(new VS(c, l - x + 1, l + 1, true)); l -= x; }
			if (l < minL) minL = l;
			if (l > maxL) maxL = l;
		}

		Collections.sort(vsList, (s1, s2) -> s1.c - s2.c);

		for (l = minL; l <= maxL; l++) {
			for (VS vs: vsList) {
				if (l >= vs.min && l <= vs.max) {
					if (vs.up) c = vs.c;
					else {
						s += vs.c - c + 1;
						System.out.println(l + " " + (vs.c - c + 1));
					}
				}
			}
		}

		System.out.println(s);
	}

	private static record VS(int c, int min, int max, boolean up) {}
}