package aoc.done;

import tools.collections.int32.L;
import tools.scanner.Scan;

public class Day_12_1bis {
	
	public static void main(String[] args) {
		String[] in = Scan.readRawLines();
		long s = 0;
		for (var line: in) {
			System.out.println(line);
			String[] tk = line.split(" ");
			L vs = new L(tk[1]);
			System.out.println(":" + cmb(tk[0], vs));
			s += cmb(tk[0], vs);
		}
		
		System.out.println(s);
	}

	private static long cmb(String s, L l) {
		long sum = 0;
		
		int n = s.length();
		int mask1 = 0;
		int mask2 = 0;
		for (char c: s.toCharArray()) {
			mask1 *= 2;
			if (c == '#') mask1++;
			mask2 *= 2;
			if (c == '.') mask2++;
		}
		
		int max = 1<<n;
		Loop: for (int i = 0; i < max; i++) {
			if ((mask1 & ~i) != 0) continue;
			if ((mask2 & i) != 0) continue;
			int pos = 1<<n;
			for (int v: l) {
				while ((i & pos) == 0 && pos > 0) pos /= 2;
				if ((i & pos) == 0 && pos == 0) continue Loop;
				int nb = 0;
				while ((i & pos) != 0 && pos > 0) {
					pos /= 2;
					nb++;
				}
				if (nb != v) continue Loop;
			}
			while ((i & pos) == 0 && pos > 0) pos /= 2;
			if (pos == 0) sum++;
		}
		return sum;
	}
}
