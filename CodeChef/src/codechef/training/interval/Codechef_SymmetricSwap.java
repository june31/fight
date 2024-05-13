package codechef.training.interval;

import tools.collections.int32.L;
import tools.collections.multi.Lll;
import tools.dichotomy.Search;
import tools.scanner.Scan;
import tools.scanner.list.ScanL;
import tools.strings.S;
import tools.structures.interval.IntervalDiscreteFlatSet;
import tools.tuple.LL;

class Codechef_SymmetricSwap {

	static Lll rs = new Lll();
	static int n;
	public static void main(String[] args) {
		for (int z = 0; z < Scan.readOnce(); z++) {
			n = Scan.readInt();
			L as = ScanL.readLine();
			L bs = ScanL.readLine();
			for (int i = 0; i < n; i++) rs.add(new LL(as.get(i) * 2, bs.get(i) * 2));
			S.o(Search.minTrueLong(l -> covers2(l)));
		}
	}

	static boolean covers(long l) {
		if (l < 0) return false;
		IntervalDiscreteFlatSet inter = IntervalDiscreteFlatSet.full();
		for (LL r: rs) {
			IntervalDiscreteFlatSet mr = new IntervalDiscreteFlatSet();
			mr.add(r.a - l, r.a + l);
			mr.add(r.b - l, r.b + l);
			inter.intersect(mr);
		}
		return !inter.isEmpty();
	}
	
	static boolean covers2(long x) {
		if (x < 0) return false;
		Lll intervals = new Lll();
		intervals.add(0, Long.MAX_VALUE);
		Lll next = new Lll();
		for (int i = 0; i < n; i++) {
			LL p1 = new LL(rs.get(i).a - x, rs.get(i).a + x);
			LL p2 = new LL(rs.get(i).b - x, rs.get(i).b + x);
			for (LL p : intervals) {
				LL np = p1.intersected(p);
				if (np != null) next.add(np);
				np = p2.intersected(p);
				if (np != null) next.add(np);
			}
			if (next.isEmpty()) return false;
			Lll tmp = next.flattened();
			next = intervals;
			next.clear();
			intervals = tmp;
		}
		return true;
	}
}
