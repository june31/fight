package tooltests.intervalles;

import tools.scanner.Scan;
import tools.scanner.list.ScanLRange;
import tools.strings.S;
import tools.structures.interval.MultiRange;
import tools.tuple.Range;

// Jeux Olympiques - Ascenseurs
// https://www.isograd-testingservices.com//FR/solutions-challenges-de-code?cts_id=100#
public class ISO_JO_Acenceurs {
	public static void main(String[] args) {
		int N = Scan.readInt();
		int M = Scan.readInt();
		int E = Scan.readInt();
		MultiRange iv = new MultiRange(true);
		iv.addAll(ScanLRange.read(M));
		S.o(iv.contains(new Range(E, N)) ? "YES" : "NO");
	}
}
