package tools.enumeration.combinations;

import java.util.Iterator;

import tools.collections.int32.L;

public class IntArrangements implements Iterable<int[]> {

	public final int[] tab;
	public final int n;
	public final int c;
	public final long max;

	public IntArrangements(L t, int c) {
		tab = t.array();
		n = t.size();
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
					t[i] = tab[x];
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
