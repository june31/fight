package tooltests.intervalles;

import tools.scanner.Scan;
import tools.scanner.list.ScanLRange;
import tools.strings.S;
import tools.structures.interval.IntervalDiscreteFlatSet;
import tools.tuple.Interval;

// Jeux Olympiques - Ascenseurs
// https://www.isograd-testingservices.com//FR/solutions-challenges-de-code?cts_id=100#
public class ISO_JO_Acenceurs {
	public static void main(String[] args) {
		int N = Scan.readInt();
		int M = Scan.readInt();
		int E = Scan.readInt();
		IntervalContinuousFlatSet iv = new IntervalContinuousFlatSet();
		iv.addAll(ScanLRange.read(M));
		S.o(iv.contains(new Interval(E, N)) ? "YES" : "NO");
	}
}
