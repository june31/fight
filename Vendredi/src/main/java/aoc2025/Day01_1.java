package aoc2025;

import tools.collections.int64.Ll;
import tools.scanner.list.ScanLl;
import tools.strings.S;

public class Day01_1 {
	public static void main(String[] args) {
		Ll in = ScanLl.readRaw();
		
		S.o(in.filtered(l -> l > 2025).sum());
	}
}
