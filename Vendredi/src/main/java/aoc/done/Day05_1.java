package aoc.done;

import tools.collections.multi.Lll;
import tools.scanner.Scan;
import tools.strings.S;
import tools.structures.interval.IntervalDiscreteFlatSet;
import tools.tuple.LL;

public class Day05_1 {
	public static void main(String[] args) {
		Lll lll	= new Lll();
		for (String s : Scan.readRaw()) lll.add(new LL(s.replace('-', ' ')));
		var inter = new IntervalDiscreteFlatSet(lll);
		for (long l : Scan.readRawLongs()) if (inter.contains(l)) S.inc();;
		S.score();
	}
}
