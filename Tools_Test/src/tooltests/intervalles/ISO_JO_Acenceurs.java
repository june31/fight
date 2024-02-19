package tooltests.intervalles;

import tools.scanner.Scan;
import tools.scanner.list.ScanLSegment;
import tools.strings.S;
import tools.structures.interval.Interval;
import tools.tuple.Segment;

// Jeux Olympiques - Ascenseurs
// https://www.isograd-testingservices.com//FR/solutions-challenges-de-code?cts_id=100#
public class ISO_JO_Acenceurs {
	public static void main(String[] args) {
		int N = Scan.readInt();
		int M = Scan.readInt();
		int E = Scan.readInt();
		Interval iv = new Interval(true);
		iv.mergeAll(ScanLSegment.read(M));
		S.o(iv.contains(new Segment(E, N)) ? "YES" : "NO");
	}
}
