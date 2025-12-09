package aoc.done;

import tools.collections.pos.Lp;
import tools.scanner.list.ScanLp;
import tools.strings.S;
import tools.tuple.Pos;

public class Day09_2 {
	public static void main(String[] args) {
		long z = 0;
		Lp in = ScanLp.readRawCL();
		for (int i = 0; i < in.size() - 1; i++) {
			L: for (int j = i+1; j < in.size(); j++) {
				long d = ((long) Math.abs(in.get(j).l-in.get(i).l)+1) * ((long) Math.abs(in.get(j).c-in.get(i).c)+1);
				if (d > z) {
					int cmin = Math.min(in.get(i).c, in.get(j).c);
					int cmax = Math.max(in.get(i).c, in.get(j).c);
					int lmin = Math.min(in.get(i).l, in.get(j).l);
					int lmax = Math.max(in.get(i).l, in.get(j).l);
					for (int k = 0; k < in.size() - 1; k++) {
						Pos p = in.get(k);
						Pos q = in.get(k+1);
						if (k != i && k != j && k+1 != i && k+1 != j) {
							if (p.l <= lmin && q.l <= lmin) continue;
							if (p.l >= lmax && q.l >= lmax) continue;
							if (p.c <= cmin && q.c <= cmin) continue;
							if (p.c >= cmax && q.c >= cmax) continue;
							continue L;
						}
					}
					z = d;
				}
			}
		}
		S.o(z);
	}
}
