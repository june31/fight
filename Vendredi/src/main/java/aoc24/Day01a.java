package aoc24;

import tools.scanner.list.ScanLii;
import tools.strings.S;

public class Day01a {
	public static void main(String[] args) {
		var l = ScanLii.readRaw();
		var a = l.getIs().sortedUp();
		var b = l.getVs().sortedUp();
		int z = 0;
		for (int i = 0; i < l.size(); i++) z += Math.abs(a.get(i) - b.get(i));
		S.o(z);
	}
}
