package aoc2025;

import tools.collections.int32.Si;
import tools.scanner.Scan;
import tools.strings.S;
import tools.tables.Table;
import tools.tuple.Pos;

public class Day07_1 {
	public static void main(String[] args) {
		var in = Scan.readMapRaw();
		Pos p = Table.find(in, 'S');
		Si s = new Si();
		s.add(p.c);
		int z = 0;
		for (int i = 2; i < in.length; i += 2) {
			Si sn = new Si();
			for (int j=0; j<in[0].length; j++) {
				if (s.contains(j)) {
					if (in[i][j] == '^') {
						sn.add(j-1);
						sn.add(j+1);
						z++;
					} else sn.add(j);
				}
			}
			s = sn;
		}
		S.o(z);
	}
}
