package aoc.done;

import tools.collections.multi.Lll;
import tools.scanner.Scan;
import tools.strings.S;
import tools.structures.interval.IntervalDiscreteFlatSet;
import tools.tuple.LL;

public class Day05_2 {
	public static void main(String[] args) {
		Lll lll	= new Lll();
		for (String s : Scan.readRaw()) lll.add(new LL(s.replace('-', ' ')));
		S.o(new IntervalDiscreteFlatSet(lll).flatSize());
	}
}
