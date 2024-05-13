package tooltests.intervalles;

import tools.scanner.Scan;
import tools.scanner.list.ScanLdd;
import tools.strings.S;
import tools.structures.interval.IntervalContinuousFlatSet;
import tools.tuple.DD;

// Jeux Olympiques - Ascenseurs
// MDF 2023 Round 2 - 11h30 - Exercice 3
// https://www.isograd-testingservices.com//FR/solutions-challenges-de-code?cts_id=100
public class Iso_JO_Acenceurs {
	public static void main(String[] args) {
		int N = Scan.readInt();
		int M = Scan.readInt();
		int E = Scan.readInt();
		IntervalContinuousFlatSet ic = new IntervalContinuousFlatSet();
		ic.addAll(ScanLdd.read(M));
		S.o(ic.contains(new DD(E, N)) ? "YES" : "NO");
	}
}
