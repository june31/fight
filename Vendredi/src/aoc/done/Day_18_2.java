package aoc.done;

import java.util.ArrayList;
import java.util.Collections;

import tools.scanner.Scan;

public class Day_18_2 {

	private static int STAR = 2;
	public static void main(String[] args) {
		String[] in = Scan.readRawLines();

		int minL = 0;
		int maxL = 0;

		int l = 0;
		int c = 0;
		var vsList = new ArrayList<VS>();
		long s = 0;
		int oldC = 0;

		boolean up = true;
		boolean right = true;
		for (String a: in) {
			String[] t = a.split(" ");
			int x = STAR == 1 ? Integer.parseInt(t[1]) : Integer.parseInt(t[2].substring(2, 7), 16);
			int d = STAR == 1 ? t[0].charAt(0) : t[2].charAt(7);

			if (d == 'R' || d == '0') {
				right = true;
				c += x;
			}
			if (d == 'D' || d == '1') {
				if (up && right) {
					vsList.add(new VS(oldC, l, l, true));
					vsList.add(new VS(c, l, l + x - 1, false));
				}
				if (up && !right) {
					vsList.add(new VS(c, l + 1, l + x - 1, false));
				}
				if (!up && right) {
					vsList.add(new VS(c, l, l + x - 1, false));
				}
				if (!up && !right) {
					vsList.add(new VS(oldC, l, l, false));
					vsList.add(new VS(c, l + 1, l + x - 1, false));
				}
				l += x;
				oldC = c; 
				up = false;
			}
			if (d == 'L' || d == '2') {
				right = false;
				c -= x;
			}
			if (d == 'U' || d == '3') {
				if (up && right) {
					vsList.add(new VS(oldC, l, l, true));
					vsList.add(new VS(c, l - x + 1, l - 1, true));
				}
				if (up && !right) {
					vsList.add(new VS(c, l - x + 1, l, true));
				}
				if (!up && right) {
					vsList.add(new VS(c, l - x + 1, l - 1, true));
				}
				if (!up && !right) {
					vsList.add(new VS(oldC, l, l, false));
					vsList.add(new VS(c, l - x + 1, l, true));
				}
				l -= x;
				oldC = c; 
				up = true;
			}
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
						if (STAR == 1) System.out.println(l + " " + (vs.c - c + 1));
					}
				}
			}
		}

		System.out.println(s);
	}

	private static record VS(int c, int min, int max, boolean up) {}
}