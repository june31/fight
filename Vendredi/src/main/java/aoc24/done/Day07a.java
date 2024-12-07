package aoc24.done;

import tools.collections.int32.L;
import tools.scanner.list.ScanLs;
import tools.strings.S;

public class Day07a {
	public static void main(String[] args) {
		var ls = ScanLs.readRaw();
		long z = 0;
		for (var x: ls) {
			String[] t = x.split(": ");
			long i = S.l(t[0]);
			L l = new L(t[1]);
			int n = l.size() - 1;
			for (int a = 0; a < (1<<n); a++) {
				long g = l.get(0);
				for (int b = 0; b < n; b++) {
					if ((a>>b & 1) == 0) g += l.get(b + 1); 
					else g *= l.get(b + 1);
				}
				if (g == i) {
					z += i;
					break;
				}
			}
		}
		S.o(z);
	}
}
