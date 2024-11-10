package tools;

import java.util.Collection;
import java.util.List;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import tools.function.BiIntConsumer;
import tools.function.TriIntConsumer;
import tools.tuple.ID;
import tools.tuple.II;
import tools.tuple.III;
import tools.tuple.IL;
import tools.tuple.IO;

public class F {

	public static void for0(int n, IntConsumer c) {
        for (int i = 0; i < n; i++)
            c.accept(i);
    }

	public static void for0(int n, int m, BiIntConsumer c) {
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				c.accept(i, j);
	}
	
	public static void for0(int n, int m, int o, TriIntConsumer c) {
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				for (int k = 0; k < o; k++)
					c.accept(i, j, k);
	}

	public static void for1(int n, IntConsumer c) {
		for (int i = 1; i <= n; i++)
			c.accept(i);
	}
	
	public static void for1(int n, int m, BiIntConsumer c) {
		for (int i = 1; i <= n; i++)
			for (int j = 1; j <= m; j++)
				c.accept(i, j);
	}

	public static void for1(int n, int m, int o, TriIntConsumer c) {
		for (int i = 1; i <= n; i++)
			for (int j = 1; j <= m; j++)
				for (int k = 1; k <= o; k++)
					c.accept(i, j, k);
	}
	
	public static IL maxLong0(int n, IntToLongFunction f) {
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

	public static ID maxDouble0(int n, IntToDoubleFunction f) {
		double max = Double.NEGATIVE_INFINITY;
		int best = -1;
		for (int i = 0; i < n; i++) {
			double v = f.applyAsDouble(i);
			if (max < v) {
				max = v;
				best = i;
			}
		}
		return new ID(best, max);
	}

	public static II max0(int n, IntUnaryOperator f) {
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

	public static IL minLong0(int n, IntToLongFunction f) {
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

	public static ID minDouble0(int n, IntToDoubleFunction f) {
		double min = Double.POSITIVE_INFINITY;
		int best = -1;
		for (int i = 0; i < n; i++) {
			double v = f.applyAsDouble(i);
			if (min > v) {
				min = v;
				best = i;
			}
		}
		return new ID(best, min);
	}

	public static II min0(int n, IntUnaryOperator f) {
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

	public static <A> IO<A> maxLong(A[] t, ToLongFunction<A> f) {
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

	public static <A> IO<A> maxDouble(Iterable<A> t, ToDoubleFunction<A> f) {
		A best = null;
		int bestId = -1;
		double max = Double.NEGATIVE_INFINITY;
		int index = 0;
		for (A a : t) {
			double v = f.applyAsDouble(a);
			if (max < v) {
				max = v;
				best = a;
				bestId = index;
			}
			index++;
		}
		return new IO<A>(bestId, best);
	}
	
	public static <A> IO<A> maxDouble(A[] t, ToDoubleFunction<A> f) {
		A best = null;
		int bestId = -1;
		double max = Double.NEGATIVE_INFINITY;
		int index = 0;
		for (A a : t) {
			double v = f.applyAsDouble(a);
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
	
	public static <A> IO<A> minDouble(Iterable<A> t, ToDoubleFunction<A> f) {
		A best = null;
		int bestId = -1;
		double min = Double.POSITIVE_INFINITY;
		int index = 0;
		for (A a : t) {
			double v = f.applyAsDouble(a);
			if (min > v) {
				min = v;
				best = a;
				bestId = index;
			}
			index++;
		}
		return new IO<A>(bestId, best);
	}

	public static <A> IO<A> minLong(A[] t, ToLongFunction<A> f) {
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
	
	public static <A> IO<A> minDouble(A[] t, ToDoubleFunction<A> f) {
		A best = null;
		int bestId = -1;
		double min = Double.POSITIVE_INFINITY;
		int index = 0;
		for (A a : t) {
			double v = f.applyAsDouble(a);
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

	public static IL maxLong1(int n, IntToLongFunction f) {
		long max = Long.MIN_VALUE;
		int best = -1;
		for (int i = 1; i <= n; i++) {
			long v = f.applyAsLong(i);
			if (max < v) {
				max = v;
				best = i;
			}
		}
		return new IL(best, max);
	}

	public static ID maxDouble1(int n, IntToDoubleFunction f) {
		double max = Double.NEGATIVE_INFINITY;
		int best = -1;
		for (int i = 1; i <= n; i++) {
			double v = f.applyAsDouble(i);
			if (max < v) {
				max = v;
				best = i;
			}
		}
		return new ID(best, max);
	}

	public static II max1(int n, IntUnaryOperator f) {
		int max = Integer.MIN_VALUE;
		int best = -1;
		for (int i = 1; i <= n; i++) {
			int v = f.applyAsInt(i);
			if (max < v) {
				max = v;
				best = i;
			}
		}
		return new II(best, max);
	}

	public static IL minLong1(int n, IntToLongFunction f) {
		long min = Long.MAX_VALUE;
		int best = -1;
		for (int i = 1; i <= n; i++) {
			long v = f.applyAsLong(i);
			if (min > v) {
				min = v;
				best = i;
			}
		}
		return new IL(best, min);
	}

	public static ID minDouble1(int n, IntToDoubleFunction f) {
		double min = Double.POSITIVE_INFINITY;
		int best = -1;
		for (int i = 1; i <= n; i++) {
			double v = f.applyAsDouble(i);
			if (min > v) {
				min = v;
				best = i;
			}
		}
		return new ID(best, min);
	}

	public static II min1(int n, IntUnaryOperator f) {
		int min = Integer.MAX_VALUE;
		int best = -1;
		for (int i = 1; i <= n; i++) {
			int v = f.applyAsInt(i);
			if (min > v) {
				min = v;
				best = i;
			}
		}
		return new II(best, min);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void orderUp(List<Integer> orders, List... lists) {
		int n = orders.size();
		for (int i = 0; i < n-1; i++) {
			int x = orders.get(i);
			for (int j = i + 1; j < n; j++) {
				if (x > orders.get(j)) {
					x = orders.get(j);
					orders.set(j, orders.get(i));
					orders.set(i, x);
					for (List list : lists) {
						Object t = list.get(j);
						list.set(j, list.get(i));
						list.set(i, t);
					}
				}
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void orderDown(List<Integer> orders, List... lists) {
		int n = orders.size();
		for (int i = 0; i < n - 1; i++) {
			int x = orders.get(i);
			for (int j = i + 1; j < n; j++) {
				if (x < orders.get(j)) {
					x = orders.get(j);
					orders.set(j, orders.get(i));
					orders.set(i, x);
					for (List list : lists) {
						Object t = list.get(j);
						list.set(j, list.get(i));
						list.set(i, t);
					}
				}
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void orderOnIndexes(List<Integer> indexes, List... lists) {
		int n = indexes.size();
		for (int i = 0; i < n - 1; i++) {
			int x = indexes.get(i);
			if (x != i) {
				for (List list : lists) {
					Object t = list.get(x);
					list.set(x, list.get(i));
					list.set(i, t);
				}
				indexes.set(i, i);
				indexes.set(x, x);
			}
		}
	}

	public static int[] stats0(int n, int[] tab) {
		int[] t = new int[n];
		for (int x: tab) t[x]++;
		return t;
	}
	
	public static int[] stats0(int n, Collection<Integer> l) {
		int[] t = new int[n];
		for (int x: l) t[x]++;
		return t;
	}
}
