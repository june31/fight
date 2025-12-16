package aoc2025;

import tools.collections.string.Ls;
import tools.scanner.list.ScanLs;
import tools.strings.S;

public class Day12_2 {
	public static void main(String[] args) {
		long z = 0;
		Ls in = ScanLs.readRaw();
		for (String s: in) {
			S.o(s);
		}
		S.o(z);
	}
}
