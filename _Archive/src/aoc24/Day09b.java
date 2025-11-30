package aoc24;

import tools.collections.int32.L;
import tools.scanner.Scan;
import tools.strings.S;

public class Day09b {
	public static void main(String[] args) {
		var s = Scan.readLine() + "0";
		var l = new L();
		int id = 0;
		for (int i = 0; i < s.length(); i+=2) {
			int a = s.charAt(i) - '0';
			int b = s.charAt(i+1) - '0';
			for (int j = 0; j < a; j++) l.add(id);
			for (int j = 0; j < b; j++) l.add(-1);
			if (a != 0) id++;
		}

		int e = l.size() - 1;
		while (e >= 0) {
			int d = -1;
			int j = 0;
			boolean found = false;
			for (; e >= 0; e--) {
				int g = l.get(e);
				if (g != d)
					if (g != d && d > 0) {
						found = true;
						break;
					} else {
						j = 1;
						d = g;
					}
				else j++;
			}
			if (!found) break;

			Loop: for (int i = 0; i < e; i++) {
				int a = l.get(i);
				if (a == -1) {
					int c = 0;
					while (l.get(i + c) == -1) c++;
					if (c >= j) {
						for (int k = 0; k < j; k++) {
							l.set(k + i, d);
							l.set(k + e + 1, -1);
						}
						break Loop;
					}
				}
			}
		}
		long z = 0;
		for (int i = 0; i < l.size(); i++) {
			long a = l.get(i);
			if (a == -1) continue;
			z += i*a;
		}
		S.o(z);
	}
}