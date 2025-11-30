package aoc24;

import tools.scanner.list.ScanLl;
import tools.strings.S;

public class Day22a {
	private static long z = 0;
	public static void main(String[] args) {
		var l = ScanLl.readRaw();
		for (var x: l) {
			for (int i = 0; i < 2000; i++) {
				x ^= x << 6;
				x &= 0xffffff;
				x ^= x >> 5;
				x &= 0xffffff;
				x ^= x << 11;
				x &= 0xffffff;
			}
			z += x;
		}
		S.o(z);
	}
}
