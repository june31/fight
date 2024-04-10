package currentCG;

import tools.collections.int64.Ll;
import tools.math.Primes;
import tools.scanner.Scan;
import tools.strings.S;
import tools.structures.graph.node.Node;

public class CGS_Clash {
	public static void main(String[] args) {
		Ll l = new Ll(Primes.retrieveAllFactorsLong(Scan.readLong()));
		for (int z = 0; z < Scan.readOnce(); z++) {
			var sCounts = new Ll(Primes.retrieveAllFactorsLong(Scan.readInt())).counts();
			var eCounts = new Ll(Primes.retrieveAllFactorsLong(Scan.readInt())).counts();
			for (var entry: sCounts.entrySet()) {
				int prime = (int) (long) entry.getKey();
				if (Node.exists(prime)) {
					
				}
			}
		}
		S.o(l.mul());
	}
}