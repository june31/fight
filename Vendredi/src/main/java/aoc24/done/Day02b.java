package aoc24.done;

import tools.collections.int32.L;
import tools.scanner.list.ScanLs;
import tools.strings.S;

public class Day02b {
	public static void main(String[] args) {
		var l = ScanLs.readRaw();

		long z = 0;
		for (var x: l) {
			boolean o = true;
			L r = new L(x);
			L a = new L(r);
			if (a.sortedUp().equals(a) || a.sortedDown().equals(a)) {
				a = a.sortedUp();
				for (int i = 0; i < a.size() - 1; i++) {
					int h = a.g(i+1)-a.g(i);
					if (h < 1 || h > 3) o = false; 
				}
			}
			else o = false;
			if (o) z++;
			else {
				for (int j = 0; j < r.size(); j++) {
					boolean p = true;
					L b = new L(r);
					b.remove(j);
					if (b.sortedUp().equals(b) || b.sortedDown().equals(b)) {
						b = b.sortedUp();
						for (int i = 0; i < b.size() - 1; i++) {
							int h = b.g(i+1)-b.g(i);
							if (h < 1 || h > 3) p = false; 
						}
					}
					else p = false;
					if (p) {
						z++;
						break;
					}
				}
			}
		}
		S.o(z);
	}
}
