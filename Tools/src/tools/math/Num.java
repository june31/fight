package tools.math;

import java.util.List;
import java.util.function.IntToLongFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import tools.function.BiIntToIntFunction;
import tools.function.IntToIntFunction;
import tools.tuple.II;
import tools.tuple.III;
import tools.tuple.IL;

public class Num {
	public static int gcd(int a, int b) {
		if (b == 0) return a;
		else return gcd(b, a % b);
	}

	public static int gcd(int... x) {
		if (x.length == 0) return 1;
		if (x.length == 1) return x[0];
		int[] y = new int[x.length - 1];
		System.arraycopy(x, 1, y, 0, x.length - 1);
		return gcd(x[0], gcd(y));
	}

	public static long gcd(long a, long b) {
		if (b == 0) return a;
		else return gcd(b, a % b);
	}

	public static long gcd(long... x) {
		if (x.length == 0) return 1;
		if (x.length == 1) return x[0];
		long[] y = new long[x.length - 1];
		System.arraycopy(x, 1, y, 0, x.length - 1);
		return gcd(x[0], gcd(y));
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

	public static II max(int n, IntToIntFunction f) {
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

	public static II max(int[] t, IntToIntFunction f) {
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

	public static II max(List<Integer> l, IntToIntFunction f) {
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

	public static III max(int[][] t, BiIntToIntFunction f) {
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

	public static II min(int n, IntToIntFunction f) {
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

	public static II min(int[] t, IntToIntFunction f) {
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

	public static II min(List<Integer> l, IntToIntFunction f) {
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

	public static III min(int[][] t, BiIntToIntFunction f) {
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
	
	
    public static long lcmModN(long a, long b, long N) {
        long lcm = (a * b) % N;
        return (lcm / gcd(a, b)) % N;
    }
    
	public static void main(String[] args) {
		System.out.println(lcmModN(40, 60, 7));
	}
}

