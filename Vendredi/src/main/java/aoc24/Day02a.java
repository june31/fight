package aoc24;

import tools.collections.int32.L;
import tools.scanner.list.ScanLs;
import tools.strings.S;

public class Day02a {
	public static void main(String[] args) {
		var l = ScanLs.readRaw();
		
		long z = 0;
		W: for (var x: l) {
			L a = new L(x);
			if (a.sortedUp().equals(a) || a.sortedDown().equals(a)) {
				a = a.sortedUp();
				for (int i = 0; i < a.size() - 1; i++) {
					int h = a.g(i+1)-a.g(i);
					if (h < 1 || h > 3) continue W; 
				}
				z++;
			}
		}
		S.o(z);
	}
}
