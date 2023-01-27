package codechef.training.interval;

import java.util.ArrayList;
import java.util.List;

import tools.dichotomy.Search;
import tools.math.Interval;
import tools.output.Out;
import tools.scanner.Scan;
import tools.tuple.LPair;

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
			Out.bufln(Search.maxTrue(x -> fail(x)) + 1);
		}
		Out.flush();
	}

	private static boolean fail(long x) {
		if (x > 2_000_000_000) return false;
		if (x < 0) return true;
		List<LPair> intervals = new ArrayList<>();
		intervals.add(new LPair(0, Long.MAX_VALUE));
		List<LPair> next = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			LPair p1 = new LPair(a[i] - x, a[i] + x);
			LPair p2 = new LPair(b[i] - x, b[i] + x);
			for (LPair p : intervals) {
				LPair np = Interval.intersection(p1, p);
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