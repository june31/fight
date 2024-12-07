package aoc24.done;

import tools.collections.int32.L;
import tools.enumeration.any.AnyInts;
import tools.scanner.list.ScanLs;
import tools.strings.S;

public class Day07b {
	public static void main(String[] args) {
		var ls = ScanLs.readRaw();
		long z = 0;
		for (var line: ls) {
			String[] tokens = line.split(": ");
			long res = S.l(tokens[0]);
			L l = new L(tokens[1]);
			int n = l.size();
			for (var t: new AnyInts(n-1, 3)) {
				long g = l.get(0);
				for (int i = 1; i < n; i++) {
					if (t[i - 1] == 0) g += l.get(i);
					if (t[i - 1] == 1) g *= l.get(i);
					if (t[i - 1] == 2) g = S.l(g + "" + l.get(i));
				}
				if (g == res) {
					z += res;
					break;
				}
			}
		}
		S.o(z);
	}
}
