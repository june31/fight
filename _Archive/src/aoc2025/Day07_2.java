package aoc2025;

import tools.collections.int64.Ll;
import tools.scanner.Scan;
import tools.strings.S;
import tools.tables.Table;
import tools.tuple.Pos;

public class Day07_2 {
	public static void main(String[] args) {
		var in = Scan.readMapRaw();
		Pos p = Table.find(in, 'S');
		long[] s = new long[in[0].length];
		s[p.c] = 1;
		for (int i = 2; i < in.length; i += 2) {
			long[] sn = new long[in[0].length];
			for (int j=0; j<in[0].length; j++) {
				if (s[j] > 0) {
					if (in[i][j] == '^') {
						sn[j-1] = sn[j-1] + s[j];
						sn[j+1] = sn[j+1] + s[j];
					} else sn[j] = sn[j] + s[j];
				}
			}
			s = sn;
		}
		S.o(new Ll(s).sum());
	}
}
