package tools.enumeration.combinations;

import java.util.Iterator;

public class IntCombinations1 implements Iterable<int[]> {

	public final int n;
	public final int c;
	public final long max;

	public IntCombinations1(int n, int c) {
		this.n = n;
		this.c = c;
		max = f(n)/(f(c)*f(n-c));
	}

	@Override
	public Iterator<int[]> iterator() {
		return new Iterator<int[]>() {
			private long id = 0;
			private long provided = 0;
			public boolean hasNext() { return provided < max; }
			public int[] next() {
				while (Long.bitCount(id) != c) id++;
				int[] t = new int[c];
				int p = 0;
				for (int i = 0; i < n; i++) if ((id & 1<<i) != 0) t[p++] = i + 1;
				id++;
				provided++;
				return t;
			}
		};
	}

	private static long f(int x) {
		long m = 1;
		for (int i = 2; i <= x; i++) m *= i;
		return m;
	}
}
