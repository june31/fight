package tooltests.enumeration;

import tools.collections.float64.Ld;
import tools.enumeration.combinations.MixCombinations;
import tools.scanner.list.ScanLd;
import tools.strings.S;

// https://www.isograd-testingservices.com/FR/solutions-challenges-de-code?cts_id=109
// Challenge BPCE collectif 2023 - Exercise 2
public class Iso_Judges {
	public static void main(String[] args) {
		Ld notes = new Ld();
		for (var c: new MixCombinations<Double>(ScanLd.read(12), 9)) notes.add(new Ld(c).mean());
		S.o(notes.min());
		S.o(notes.mean());
		S.o(notes.median());
		S.o(notes.max());
	}
}
