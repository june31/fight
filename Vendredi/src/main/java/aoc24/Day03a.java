package aoc24;

import tools.collections.int32.L;
import tools.scanner.list.ScanLs;
import tools.strings.S;

public class Day03a {
	public static void main(String[] args) {
		long z = 0;
		for (String x : ScanLs.readRaw())
			for (String s: S.getRegexMatches(x, "mul\\(\\d+,\\d+\\)"))
				z += new L(S.substring(s, 4, -1)).mul();
		S.o(z);
	}
}
