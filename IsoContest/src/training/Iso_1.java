package training;

import tools.collections.float64.Ld;
import tools.enumeration.combinations.MixCombinations;
import tools.scanner.Scan;
import tools.strings.S;

public class Iso_1 {

	public static void main(String[] args) {
		Ld l = new Ld(Scan.readDoubles(12));
		Ld notes = new Ld();
		for (var c: new MixCombinations<Double>(l, 9)) notes.add(new Ld(c).mean());
		notes = notes.sortedUp();
		S.o(notes.first());
		S.o(notes.mean());
		S.o(notes.median());
		S.o(notes.last());
	}
}
