package tools.enumeration.combinations;

import java.util.Iterator;

import tools.collections.int32.L;

public class IntArrangements0 implements Iterable<L> {

	public final int n;
	public final int c;
	public final long max;

	public IntArrangements0(int n, int c) {
		this.n = n;
		this.c = c;
		max = f(n) / f(n-c);
	}

	@Override
	public Iterator<L> iterator() {
		return new Iterator<L>() {
			private long provided = 0;
			public boolean hasNext() { return provided < max; }
			public L next() {
				L l = new L();
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
					l.add(x);
					z /= u;
				}
				return l;
			}
		};
	}

	private static long f(int x) {
		long m = 1;
		for (int i = 2; i <= x; i++) m *= i;
		return m;
	}
}
