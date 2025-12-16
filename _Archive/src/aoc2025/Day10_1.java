package aoc2025;

import tools.collections.bool.Lb;
import tools.collections.int32.L;
import tools.collections.string.Ls;
import tools.scanner.list.ScanLs;
import tools.strings.S;

public class Day10_1 {
	public static void main(String[] args) {
		long z = 0;
		Ls in = ScanLs.readRaw();
		for (String s: in) {
			var tks = s.split(" ");
			int x = new Lb(S.substring(tks[0], 1, -1)).asInt();
			L sw = new L();
			for (int i = 1; i < tks.length - 1; i++) {
				L pos = new L(tks[i]);
				sw.add(new Lb(tks[0].length() - 2, n -> pos.contains(n)).asInt());
			}
			int y = Integer.MAX_VALUE;
			for	(int comb = 0; comb < (1 << sw.size()); comb++) {
				int bulbState = 0;
				for (int i = 0; i < sw.size(); i++) if (((comb >> i) & 1) == 1) bulbState ^= sw.get(i);
				if (bulbState == x && Integer.bitCount(comb) < y) y = Integer.bitCount(comb);
			}
			z += y;
		}
		S.o(z);
	}
}
