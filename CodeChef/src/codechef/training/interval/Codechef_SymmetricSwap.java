package codechef.training.interval;

import java.util.ArrayList;
import java.util.List;

import tools.dichotomy.Search;
import tools.math.Interval;
import tools.output.Out;
import tools.scanner.Scan;
import tools.tuple.LL;

class Codechef_SymmetricSwap {

	static int n;
	static long[] a;
	static long[] b;

	public static void main(String[] args) {
		int T = Scan.readInt();
		for (int turn = 0; turn < T; turn++) {
			n = Scan.readInt();
			a = Scan.readLongArray(n);
			b = Scan.readLongArray(n);
			for (int i = 0; i < n; i++) { a[i] *= 2; b[i] *= 2; }
			Out.bufln(Search.maxTrueLong(x -> fail(x)) + 1);
		}
		Out.flush();
	}

	private static boolean fail(long x) {
		if (x > 2_000_000_000) return false;
		if (x < 0) return true;
		List<LL> intervals = new ArrayList<>();
		intervals.add(new LL(0, Long.MAX_VALUE));
		List<LL> next = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			LL p1 = new LL(a[i] - x, a[i] + x);
			LL p2 = new LL(b[i] - x, b[i] + x);
			for (LL p : intervals) {
				LL np = Interval.intersection(p1, p);
				if (np != null) next.add(np);
				np = Interval.intersection(p2, p);
				if (np != null) next.add(np);
			}
			Interval.flattenLong(next);
			if (next.isEmpty()) return true;
			var tmp = next;
			next = intervals;
			next.clear();
			intervals = tmp;
		}
		return false;
	}
}
