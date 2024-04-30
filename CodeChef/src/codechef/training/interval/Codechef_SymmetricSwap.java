package codechef.training.interval;

import tools.collections.int32.L;
import tools.collections.segment.LInterval;
import tools.dichotomy.Search;
import tools.scanner.Scan;
import tools.scanner.list.ScanL;
import tools.strings.S;
import tools.structures.interval.IntervalLongFlatSet;
import tools.tuple.Interval;

class Codechef_SymmetricSwap {

	static LInterval rs = new LInterval();
	static int n;
	public static void main(String[] args) {
		for (int z = 0; z < Scan.readOnce(); z++) {
			n = Scan.readInt();
			L as = ScanL.readLine();
			L bs = ScanL.readLine();
			for (int i = 0; i < n; i++) rs.add(new Interval(as.get(i) * 2, bs.get(i) * 2));
			S.o(Search.minTrueLong(l -> covers2(l)));
		}
	}

	static boolean covers(long l) {
		if (l < 0) return false;
		IntervalLongFlatSet inter = IntervalLongFlatSet.full();
		for (Interval r: rs) {
			IntervalLongFlatSet mr = new IntervalLongFlatSet();
			mr.add(r.a - l, r.a + l);
			mr.add(r.b - l, r.b + l);
			inter.intersect(mr);
		}
		return !inter.isEmpty();
	}
	
	static boolean covers2(long x) {
		if (x < 0) return false;
		LInterval intervals = new LInterval();
		intervals.add(0, Long.MAX_VALUE);
		LInterval next = new LInterval();
		for (int i = 0; i < n; i++) {
			Interval p1 = new Interval(rs.get(i).a - x, rs.get(i).a + x);
			Interval p2 = new Interval(rs.get(i).b - x, rs.get(i).b + x);
			for (Interval p : intervals) {
				Interval np = p1.intersected(p);
				if (np != null) next.add(np);
				np = p2.intersected(p);
				if (np != null) next.add(np);
			}
			if (next.isEmpty()) return false;
			LInterval tmp = next.flattened();
			next = intervals;
			next.clear();
			intervals = tmp;
		}
		return true;
	}
}
