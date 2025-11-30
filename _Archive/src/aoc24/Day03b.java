package aoc24;

import tools.collections.int32.L;
import tools.scanner.list.ScanLs;
import tools.strings.S;

public class Day03b {
	public static void main(String[] args) {
		long z = 0;
		boolean active = true;
		for (String x : ScanLs.readRaw()) {
			for (String op: S.getRegexMatches(x, "(mul\\(\\d+,\\d+\\))|(do\\(\\))|(don't\\(\\))")) {
				switch (op) {
				case "do()": active = true; break;
				case "don't()": active = false; break;
				default: if (active) z += new L(op).mul();
				}
			}
		}
		S.o(z);
	}
}
