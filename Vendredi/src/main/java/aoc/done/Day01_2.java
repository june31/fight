package aoc.done;

import tools.scanner.list.ScanLs;
import tools.strings.S;

public class Day01_2 {
	public static void main(String[] args) {
		int d = 50;
		for (String s: ScanLs.readRaw()) {
			int v = S.i(s.substring(1));
			for (int i = 0; i < v; i++) {
				if (s.charAt(0) == 'R') d += 1; else d -= 1;
				if (d % 100 == 0) S.inc();
			}
		}
		S.score();
	}
}
