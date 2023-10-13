package tools.enumeration.combinations;

import java.util.Iterator;

public class IntArrangements1 implements Iterable<int[]> {

	public final int n;
	public final int c;
	public final long max;

	public IntArrangements1(int n, int c) {
		this.n = n;
		this.c = c;
		max = f(n) / f(n-c);
	}

	@Override
	public Iterator<int[]> iterator() {
		return new Iterator<int[]>() {
			private long provided = 0;
			public boolean hasNext() { return provided < max; }
			public int[] next() {
				int[] t = new int[c];
				int used = 0;
				long z = provided++;
				for (int i = 0; i < c; i++) {
					int u = n - i;
					int p = (int) Math.floorMod(z, u);
					int x = 0;
					while ((used & 1<<x) != 0 || p != 0) {
						while ((used & 1<<x) != 0) x++;
						if (p != 0) { x++; p--; }
					}
					used |= 1<<x;
					t[i] = x;
					z /= u;
				}
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
