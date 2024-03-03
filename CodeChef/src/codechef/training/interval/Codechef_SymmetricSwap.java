package codechef.training.interval;

import tools.collections.int32.L;
import tools.collections.segment.LRange;
import tools.dichotomy.Search;
import tools.scanner.Scan;
import tools.scanner.list.ScanL;
import tools.strings.S;
import tools.structures.interval.MultiRange;
import tools.tuple.Range;

class Codechef_SymmetricSwap {

	static LRange rs = new LRange();
	static int n;
	public static void main(String[] args) {
		for (int z = 0; z < Scan.readOnce(); z++) {
			n = Scan.readInt();
			L as = ScanL.readLine();
			L bs = ScanL.readLine();
			for (int i = 0; i < n; i++) rs.add(new Range(as.get(i) * 2, bs.get(i) * 2));
			S.o(Search.minTrueLong(l -> covers2(l)));
		}
	}

	static boolean covers(long l) {
		if (l < 0) return false;
		MultiRange inter = MultiRange.full();
		for (Range r: rs) {
			MultiRange mr = new MultiRange();
			mr.add(r.a - l, r.a + l);
			mr.add(r.b - l, r.b + l);
			inter.intersect(mr);
		}
		return !inter.isEmpty();
	}
	
	static boolean covers2(long x) {
		if (x < 0) return false;
		LRange intervals = new LRange();
		intervals.add(0, Long.MAX_VALUE);
		LRange next = new LRange();
		for (int i = 0; i < n; i++) {
			Range p1 = new Range(rs.get(i).a - x, rs.get(i).a + x);
			Range p2 = new Range(rs.get(i).b - x, rs.get(i).b + x);
			for (Range p : intervals) {
				Range np = p1.intersected(p);
				if (np != null) next.add(np);
				np = p2.intersected(p);
				if (np != null) next.add(np);
			}
			if (next.isEmpty()) return false;
			LRange tmp = next.flattened();
			next = intervals;
			next.clear();
			intervals = tmp;
		}
		return true;
	}
}
