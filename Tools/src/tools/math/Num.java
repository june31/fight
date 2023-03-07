package tools.math;

import java.util.function.IntToLongFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import tools.tuple.IL;

public class Num {
	public static int gcd(int a, int b) {
		if (b == 0) return a;
		else return gcd(b, a % b);
	}
	
	public static long gcd(long a, long b) {
		if (b == 0) return a;
		else return gcd(b, a % b);
	}
	
	public static int lcm(int a, int b) {
		return a * b / gcd(a, b);
	}

	public static long lcm(long a, long b) {
		return a * b / gcd(a, b);
	}
	
	public static int max(int... xs) {
		int max = Integer.MIN_VALUE;
		for (int x : xs) if (x > max) max = x;
		return max;
	}

	public static IL max(int n, IntToLongFunction f) {
		long max = Long.MIN_VALUE;
		int best = -1;
		for (int i = 0; i < n; i++) {
			long v = f.applyAsLong(i);
			if (max < v) {
				max = v;
				best = i;
			}
		}
		return new IL(best, max);
	}

	public static IL min(int n, IntToLongFunction f) {
		long min = Long.MAX_VALUE;
		int best = -1;
		for (int i = 0; i < n; i++) {
			long v = f.applyAsLong(i);
			if (min > v) {
				min = v;
				best = i;
			}
		}
		return new IL(best, min);
	}

	public static <A> A max(Iterable<A> t, ToIntFunction<A> f) {
		A best = null;
		int max = Integer.MIN_VALUE;
		for (A a : t) {
			int v = f.applyAsInt(a);
			if (max < v) {
				max = v;
				best = a;
			}
		}
		return best;
	}

	public static <A> A maxLong(Iterable<A> t, ToLongFunction<A> f) {
		A best = null;
		long max = Long.MIN_VALUE;
		for (A a : t) {
			long v = f.applyAsLong(a);
			if (max < v) {
				max = v;
				best = a;
			}
		}
		return best;
	}

	public static <A> int max(A[] t, ToIntFunction<A> f) {
		int best = -1;
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < t.length; i++) {
			int v = f.applyAsInt(t[i]);
			if (max < v) {
				max = v;
				best = i;
			}
		}
		return best;
	}

	public static <A> A min(Iterable<A> t, ToIntFunction<A> f) {
		A best = null;
		int min = Integer.MAX_VALUE;
		for (A a : t) {
			int v = f.applyAsInt(a);
			if (min > v) {
				min = v;
				best = a;
			}
		}
		return best;
	}

	public static <A> A minLong(Iterable<A> t, ToLongFunction<A> f) {
		A best = null;
		long min = Long.MAX_VALUE;
		for (A a : t) {
			long v = f.applyAsLong(a);
			if (min > v) {
				min = v;
				best = a;
			}
		}
		return best;
	}

	public static <A> int min(A[] t, ToIntFunction<A> f) {
		int best = -1;
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < t.length; i++) {
			int v = f.applyAsInt(t[i]);
			if (min > v) {
				min = v;
				best = i;
			}
		}
		return best;
	}

	public static long max(long... xs) {
		long max = Long.MIN_VALUE;
		for (long x : xs) if (x > max) max = x;
		return max;
	}
	
	public static double max(double... xs) {
		double max = Double.NEGATIVE_INFINITY;
		for (double x : xs) if (x > max) max = x;
		return max;
	}
	
	public static int min(int... xs) {
		int min = Integer.MAX_VALUE;
		for (int x : xs) if (x < min) min = x;
		return min;
	}
	
	public static long min(long... xs) {
		long min = Long.MAX_VALUE;
		for (long x : xs) if (x < min) min = x;
		return min;
	}
	
	public static double min(double... xs) {
		double min = Double.POSITIVE_INFINITY;
		for (double x : xs) if (x < min) min = x;
		return min;
	}
}
