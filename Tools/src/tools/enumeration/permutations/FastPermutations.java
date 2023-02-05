package tools.enumeration.permutations;

import java.lang.reflect.Array;
import java.util.Iterator;

public class FastPermutations<A> implements Iterable<A[]> {

	private final A[] t;
	private final A[] r; 
	private final int n;
	public final long nb;

	@SuppressWarnings("unchecked")
	public FastPermutations(A[] table) {
		t = table;
		n = table.length;
		long m = 1;
		for (int i = 2; i <= n; i++) m *= i;
		nb = n == 0 ? 0 : m;
		r = n == 0 ? null : (A[]) Array.newInstance(t[0].getClass(), n);
	}

	@Override
	public Iterator<A[]> iterator() {
		return new Iterator<A[]>() {
			private long provided = 0;
			public boolean hasNext() { return provided < nb; }
			public A[] next() {
				int used = 0;
				long c = provided++;
				for (int i = 0; i < n; i++) {
					int u = n - i;
					int p = Math.floorMod(c, u);
					int x = 0;
					while ((used & 1<<x) != 0 || p != 0) {
						while ((used & 1<<x) != 0) x++;
						if (p != 0) { x++; p--; }
					}
					used |= 1<<x;
					r[i] = t[x];
					c /= u;
				}
				return r;
			}
		};
	}
}
