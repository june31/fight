package tools.enumeration.combinations;

import java.util.Iterator;

import tools.collections.int32.L;

public class IntCombinations implements Iterable<L> {

	public final int[] tab;
	public final int n;
	public final int c;
	public final long max;

	public IntCombinations(L t, int c) {
		tab = t.array();
		n = t.size();
		this.c = c;
		max = f(n)/(f(c)*f(n-c));
	}

	@Override
	public Iterator<L> iterator() {
		return new Iterator<L>() {
			private long id = 0;
			private long provided = 0;
			public boolean hasNext() { return provided < max; }
			public L next() {
				while (Long.bitCount(id) != c) id++;
				L l = new L();
				for (int i = 0; i < n; i++) if ((id & 1<<i) != 0) l.add(tab[i]);
				id++;
				provided++;
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
