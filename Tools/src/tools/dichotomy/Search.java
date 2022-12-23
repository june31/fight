package tools.dichotomy;

import java.util.function.DoublePredicate;
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
	
	public static long maxTrue(LongPredicate f) {
		long l = 0;
		long r = 0;
		if (f.test(0)) {
			for (int i = 7; i <= 63; i += 7) {
				r = (1l << i) - 1;
				if (!f.test(r)) break;
				l = r;
			}
			if (l == r) return Long.MAX_VALUE;
		} else {
			for (int i = 7; i <= 63; i += 7) {
				l = -((1l << i) - 1);
				if (f.test(l)) break;
				r = l;
			}
			if (l == r) return Long.MIN_VALUE;
		}
		
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
	
	public static double maxTrueDouble(DoublePredicate f) {
		double l = 0;
		double r = 0;
		if (f.test(0)) {
			r = 0.1;
			for (int i = 0; i <= 50; i++) {
				r *= 10;
				if (!f.test(r)) break;
				l = r;
			}
			if (l == r) return Double.POSITIVE_INFINITY;
		} else {
			l = -0.1;
			for (int i = 0; i <= 50; i ++) {
				l *= 10;
				if (f.test(l)) break;
				r = l;
			}
			if (l == r) return Double.NEGATIVE_INFINITY;
		}
		
		while (true) {
			double i = (l + r) / 2;
			if (i == l || i == r) return i; 
			if (f.test(i)) l = i;
			else r = i;
		}
	}
	
	// reach:
	// The provided function shall be either an increasing function or a decreasing function. 
	// If target is reachable by several values, reach returns any matching value.
	// In case of overflow, reach returns any value.
	// If target is not reachable (skipped value), reach returns lower result
	// (e.g., f(x)=2*x; target=7 => res=3; target = -7 => res=-4).
	
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
			r = positive * ((1l << i) - 1);
			if (positive * increase * f.applyAsLong(r) >= positive * target) break;
			l = r;
		}
		if (positive * increase * f.applyAsLong(r) < positive * target)
			return positive == 1 ? Long.MAX_VALUE : Long.MIN_VALUE; 
		if (r < l) {
			long tmp = l;
			l = r;
			r = tmp;
		}
		while (l <= r) {
			long i = l + (r - l) / 2;
			if (increase * f.applyAsLong(i) <= target) { // A REPRENDRE
				res = i;
				l = i + 1;
			} else r = i - 1;
		}
		return res;
	}
	
	// Same but returns lowest matching value
	public static long reachLow(long target, LongUnaryOperator f) {
		long l = reach(target, f);
		if (f.applyAsLong(l) != target) return l;
		return l - maxTrue(x -> f.applyAsLong(l - x) == target);
	}
	
	// Same but returns highest matching value
	public static long reachHigh(long target, LongUnaryOperator f) {
		long l = reach(target, f);
		if (f.applyAsLong(l) != target) return l;
		return l + maxTrue(x -> f.applyAsLong(l + x) == target);
	}

	/*public static double reachDouble(double ref, DoubleUnaryOperator f) {
		double l = 0;
		double r = 0;
		if (f.applyAsDouble(0)) {
			r = 0.1;
			for (int i = 0; i <= 50; i++) {
				r *= 10;
				if (!f.test(r)) break;
				l = r;
			}
			if (l == r) return Double.POSITIVE_INFINITY;
		} else {
			l = -0.1;
			for (int i = 0; i <= 50; i ++) {
				l *= 10;
				if (f.test(l)) break;
				r = l;
			}
			if (l == r) return Double.NEGATIVE_INFINITY;
		}
		
		while (true) {
			double i = (l + r) / 2;
			if (i == l || i == r) return i; 
			if (f.applyAsDouble(i)) l = i;
			else r = i;
		}
	}*/
	
	public static void main(String[] args) {
		System.out.println(maxTrueDouble(x -> x < 123456789012l));
		System.out.println(maxTrueDouble(x -> true));
		System.out.println(maxTrueDouble(x -> false));
		System.out.println(maxTrueDouble(x -> x < -123456789012l));
	}
}
