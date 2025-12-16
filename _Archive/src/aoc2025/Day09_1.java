package aoc2025;

import tools.collections.pos.Lp;
import tools.scanner.list.ScanLp;
import tools.strings.S;

public class Day09_1 {
	public static void main(String[] args) {
		long z = 0;
		Lp in = ScanLp.readRawCL();
		for (int i = 0; i < in.size() - 1; i++) {
			for (int j = i+1; j < in.size(); j++) {
				long d = ((long) Math.abs(in.get(j).l-in.get(i).l)+1) * ((long) Math.abs(in.get(j).c-in.get(i).c)+1);
				if (d > z) {
					z = d;
				}
			}
		}
		S.o(z);
	}
}
