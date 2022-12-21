package tools.dichotomy;

import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.LongUnaryOperator;

public class Search {

	// maxTrue:
	// The provided function shall return true, true... true, false... false.
	// If all false, maxTrue returns -1. If all true, it returns max.
	
	public static int maxTrue(int max, IntPredicate f) {
		int l = 0;
		int r = max;
		int res = -1;
		while (l <= r) {
			int i = l + (r - l) / 2;
			if (f.test(i)) {
				res = i;
				l = i + 1;
			} else r = i - 1;
		}
		return res;
	}
	
	public static long maxTrue(long max, LongPredicate f) { // A REVOIR SANS PARAMETRE max
		long l = 0;
		long r = max;
		long res = -1;
		while (l <= r) {
			long i = l + (r - l) / 2;
			if (f.test(i)) {
				res = i;
				l = i + 1;
			} else r = i - 1;
		}
		return res;
	}
	
	// reach:
	// The provided function shall be either an increasing function or a decreasing function. 
	// In case target is reachable by several values, reach returns the value that is nearest to 0.
	// In case target is not reachable, reach returns minValue or maxValue in case of overflow,
	// result nearest to 0 in case of "value skipped" (ex: target = 7, f = x -> 2 * x, result = 3).
	// or 0 if function seems to be constant.
	
	// f(x) shall be defined at least for x >= 0 (provided f is increasing). 
	public static long reach(long target, LongUnaryOperator f) {
		long diff = f.applyAsLong(263) - f.applyAsLong(0);
		if (diff == 0) return 0; // looks like constant
		long increase = diff > 0 ? 1 : -1;
		target *= increase;
		long res = increase * f.applyAsLong(0);
		if (res == target) return 0;
		long positive = res < target ? 1 : -1;

		long l = 0;
		long r = 0;
		for (int i = 7; i <= 63; i += 7) {
			r = positive * ((1 << i) - 1);
			if (increase * f.applyAsLong(r) >= target) break;
			l = r;
		}
		if (increase * f.applyAsLong(r) < target) return positive == 1 ? Long.MAX_VALUE : Long.MIN_VALUE; 
		if (r < l) {
			long tmp = l;
			l = r * positive;
			r = tmp * positive;
		} else {
			l *= positive;
			r *= positive;
		}
		while (l <= r) {
			long i = l + (r - l) / 2;
			if (increase * f.applyAsLong(i) == target) { // A REPRENDRE
				res = i;
				l = i + 1;
			} else r = i - 1;
		}
		return res * positive;
	}
}
