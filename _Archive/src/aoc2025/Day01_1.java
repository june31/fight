package aoc2025;

import tools.scanner.list.ScanLs;
import tools.strings.S;

public class Day01_1 {
	public static void main(String[] args) {
		int d = 50;
		for (String s: ScanLs.readRaw()) {
			int v = S.i(s.substring(1));
			if (s.charAt(0) == 'R') d += v; else d -= v;
			if (d % 100 == 0) S.inc();
		}
		S.score();
	}
}
