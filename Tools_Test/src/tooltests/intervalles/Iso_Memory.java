package tooltests.intervalles;

import tools.math.Num;
import tools.scanner.Scan;
import tools.strings.S;
import tools.structures.interval.IntervalContinuousFlatSet;

// Système de stockage mémoire
// Challenge Euro-Information 2023 - Exercice 5
// https://www.isograd-testingservices.com/FR/solutions-challenges-de-code?cts_id=120
public class Iso_Memory {
	public static void main(String[] args) {
		Scan.readInt();
		int s = Scan.readInt();
		var all = new IntervalContinuousFlatSet(); 
		var servers = new IntervalContinuousFlatSet[s];
		for (int i = 0; i < s; i++) servers[i] = new IntervalContinuousFlatSet();
		for (int z = 0; z < Scan.readOnce(); z++) {
			int n = Scan.readInt();
			double a = Scan.readDouble();
			double b = Scan.readDouble();
			if (Scan.readInt() == 1) servers[n].addAll(all.addAndExtract(a, b));
			else all.removeAll(servers[n].removeAndExtract(a, b));
		}
		S.o(Num.maxDouble(servers, IntervalContinuousFlatSet::flatSize).i);
	}
}
