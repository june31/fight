package tools.math;

import java.util.List;
import java.util.function.IntBinaryOperator;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import tools.tuple.II;
import tools.tuple.III;
import tools.tuple.IL;
import tools.tuple.IO;

public class Num {
	public static int gcd(int a, int b) {
		if (b == 0) return a;
		else return gcd(b, a % b);
	}

	public static int gcd(int... xs) {
		if (xs.length == 0) return 1;
		int res = xs[0];
		for (int i = 1; i < xs.length; i++) res = gcd(res, xs[i]);
		return res;
	}

	public static long gcd(long a, long b) {
		if (b == 0) return a;
		else return gcd(b, a % b);
	}

	public static long gcd(long... xs) {
		if (xs.length == 0) return 1;
		long res = xs[0];
		for (int i = 1; i < xs.length; i++) res = gcd(res, xs[i]);
		return res;
	}

	public static long lcm(int a, int b) {
		return a * b / gcd(a, b);
	}

	public static long lcm(int... xs) {
		long res = 1l;
		for (int x: xs) res = lcm(res, x);
		return res;
	}

	public static long lcm(long a, long b) {
		return a * b / gcd(a, b);
	}

	public static long lcm(long... xs) {
		long res = 1l;
		for (long x: xs) res = lcm(res, x);
		return res;
	}

	public static int max(int... xs) {
		int max = Integer.MIN_VALUE;
		for (int x : xs) if (x > max) max = x;
		return max;
	}

	public static III max(int[][] t) {
		int max = Integer.MIN_VALUE;
		int bestL = -1;
		int bestC = -1;
		for (int l = 0; l < t.length; l++) {
			for (int c = 0; c < t[l].length; c++) {
				int v = t[l][c];
				if (max < v) {
					max = v;
					bestL = l;
					bestC = c;
				}
			}
		}
		return new III(bestL, bestC, max);
	}

	public static IL maxLong(int n, IntToLongFunction f) {
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

	public static II max(int n, IntUnaryOperator f) {
		int max = Integer.MIN_VALUE;
		int best = -1;
		for (int i = 0; i < n; i++) {
			int v = f.applyAsInt(i);
			if (max < v) {
				max = v;
				best = i;
			}
		}
		return new II(best, max);
	}

	public static II max(int[] t, IntUnaryOperator f) {
		int max = Integer.MIN_VALUE;
		int best = -1;
		for (int i = 0; i < t.length; i++) {
			int v = f.applyAsInt(i);
			if (max < v) {
				max = v;
				best = i;
			}
		}
		return new II(best, max);
	}

	public static II max(List<Integer> l, IntUnaryOperator f) {
		int max = Integer.MIN_VALUE;
		int best = -1;
		for (int i = 0; i < l.size(); i++) {
			int v = f.applyAsInt(i);
			if (max < v) {
				max = v;
				best = i;
			}
		}
		return new II(best, max);
	}

	public static III max(int[][] t, IntBinaryOperator f) {
		int max = Integer.MIN_VALUE;
		int bestI = -1;
		int bestJ = -1;
		for (int i = 0; i < t.length; i++) {
			for (int j = 0; j < t[i].length; j++) {
				int v = f.applyAsInt(i, j);
				if (max < v) {
					max = v;
					bestI = i;
					bestJ = j;
				}
			}
		}
		return new III(bestI, bestJ, max);
	}

	public static IL minLong(int n, IntToLongFunction f) {
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

	public static II min(int n, IntUnaryOperator f) {
		int min = Integer.MAX_VALUE;
		int best = -1;
		for (int i = 0; i < n; i++) {
			int v = f.applyAsInt(i);
			if (min > v) {
				min = v;
				best = i;
			}
		}
		return new II(best, min);
	}

	public static II min(int[] t, IntUnaryOperator f) {
		int min = Integer.MAX_VALUE;
		int best = -1;
		for (int i = 0; i < t.length; i++) {
			int v = f.applyAsInt(i);
			if (min > v) {
				min = v;
				best = i;
			}
		}
		return new II(best, min);
	}

	public static II min(List<Integer> l, IntUnaryOperator f) {
		int min = Integer.MAX_VALUE;
		int best = -1;
		for (int i = 0; i < l.size(); i++) {
			int v = f.applyAsInt(i);
			if (min > v) {
				min = v;
				best = i;
			}
		}
		return new II(best, min);
	}

	public static III min(int[][] t, IntBinaryOperator f) {
		int min = Integer.MAX_VALUE;
		int bestI = -1;
		int bestJ = -1;
		for (int i = 0; i < t.length; i++) {
			for (int j = 0; j < t[i].length; j++) {
				int v = f.applyAsInt(i, j);
				if (min > v) {
					min = v;
					bestI = i;
					bestJ = j;
				}
			}
		}
		return new III(bestI, bestJ, min);
	}

	public static <A> IO<A> max(Iterable<A> t, ToIntFunction<A> f) {
		A best = null;
		int bestId = -1;
		int max = Integer.MIN_VALUE;
		int index = 0;
		for (A a : t) {
			int v = f.applyAsInt(a);
			if (max < v) {
				max = v;
				best = a;
				bestId = index;
			}
			index++;
		}
		return new IO<A>(bestId, best);
	}

	public static <A> IO<A> maxLong(Iterable<A> t, ToLongFunction<A> f) {
		A best = null;
		int bestId = -1;
		long max = Long.MIN_VALUE;
		int index = 0;
		for (A a : t) {
			long v = f.applyAsLong(a);
			if (max < v) {
				max = v;
				best = a;
				bestId = index;
			}
			index++;
		}
		return new IO<A>(bestId, best);
	}

	public static <A> IO<A> max(A[] t, ToIntFunction<A> f) {
		A best = null;
		int bestId = -1;
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < t.length; i++) {
			int v = f.applyAsInt(t[i]);
			if (max < v) {
				max = v;
				best = t[i];
				bestId = i;
			}
		}
		return new IO<A>(bestId, best);
	}

	public static <A> IO<A> min(Iterable<A> t, ToIntFunction<A> f) {
		A best = null;
		int bestId = -1;
		int min = Integer.MAX_VALUE;
		int index = 0;
		for (A a : t) {
			int v = f.applyAsInt(a);
			if (min > v) {
				min = v;
				best = a;
				bestId = index;
			}
			index++;
		}
		return new IO<A>(bestId, best);
	}

	public static <A> IO<A> minLong(Iterable<A> t, ToLongFunction<A> f) {
		A best = null;
		int bestId = -1;
		long min = Long.MAX_VALUE;
		int index = 0;
		for (A a : t) {
			long v = f.applyAsLong(a);
			if (min > v) {
				min = v;
				best = a;
				bestId = index;
			}
			index++;
		}
		return new IO<A>(bestId, best);
	}

	public static <A> IO<A> min(A[] t, ToIntFunction<A> f) {
		A best = null;
		int bestId = -1;
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < t.length; i++) {
			int v = f.applyAsInt(t[i]);
			if (min > v) {
				min = v;
				best = t[i];
				bestId = i;
			}
		}
		return new IO<A>(bestId, best);
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

	public static III min(int[][] t) {
		int min = Integer.MAX_VALUE;
		int bestL = -1;
		int bestC = -1;
		for (int l = 0; l < t.length; l++) {
			for (int c = 0; c < t[l].length; c++) {
				int v = t[l][c];
				if (min > v) {
					min = v;
					bestL = l;
					bestC = c;
				}
			}
		}
		return new III(bestL, bestC, min);
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

	public static String binary(int size, long number) {
		StringBuilder sb = new StringBuilder();
		String s = Long.toBinaryString(number);
		while (sb.length() + s.length() < size) sb.append('0');
		return sb + s;
	}
	
	public static boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        for (int i = 5; i <= Math.sqrt(n);) {
            if (n % i == 0) return false;
            i += 2;
            if (n % i == 0) return false;
            i += 4;
        }
        return true;
	}

	public static int log2(int i) { return 31 - Integer.numberOfLeadingZeros(i); }
	public static int log2(long l) { return 63 - Long.numberOfLeadingZeros(l); }
	public static int log2Plus(int i) { int r = 31 - Integer.numberOfLeadingZeros(i); if (i == 1 << r) return r; else return r + 1; }
}

