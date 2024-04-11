package currentCG;

import tools.collections.int32.L;
import tools.collections.int64.Ll;
import tools.collections.map.Misi;
import tools.math.Primes;
import tools.scanner.Scan;
import tools.strings.S;

public class CGS_Clash {
	public static void main(String[] args) {
		Ll l = new Ll(Primes.retrieveAllFactorsLong(Scan.readLong()));
		Misi m = new Misi();
		for (int z = 0; z < Scan.readOnce(); z++) {
			var counts1 = new Ll(Primes.retrieveAllFactorsLong(Scan.readLong())).counts();
			var counts2 = new Ll(Primes.retrieveAllFactorsLong(Scan.readLong())).counts();
			for (var e1: counts1.entrySet()) {
				int prime = (int) (long) e1.getKey();
				int c = e1.getValue();
				if (m.containsKey(prime)) {
					L compatibleFactors = new L();
					for (var e2: counts2.entrySet()) if (e2.getValue() == c) compatibleFactors.add((int) (long) e2.getKey());
					m.get(prime).retainAll(compatibleFactors);
				} else for (var e2: counts2.entrySet()) if (e2.getValue() == c) m.put(prime, (int) (long) e2.getKey());
			}
		}
		boolean work;
		do {
			work = false;
			for (var e: m.entrySet())
				if (e.getValue().size() == 1)
					for (var f: m.entrySet())
						work |= !e.getKey().equals(f.getKey()) && f.getValue().remove(e.getValue().first());
		} while (work);
		for (var e: m.entrySet()) l.replace(e.getKey(), e.getValue().first());
		S.o(l.mul());
	}
}