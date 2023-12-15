package aoc.done;

import tools.scanner.Scan;

public class Day_15_1 {
	
	public static void main(String[] args) {
		String[] in = Scan.readString().split(",");
		int h = 0;
		for (var s: in) {
			int x = 0;
			for (char c: s.toCharArray()) {
				x += ((int) c);
				x *= 17;
				x %= 256;
			}
			h += x;
		}
		System.out.println(h);
	}
}
