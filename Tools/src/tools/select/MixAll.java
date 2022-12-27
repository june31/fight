package tools.select;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MixAll<A> implements Iterable<List<A>> {

	private final List<A> l;
	private final int n;
	private final long max;
	private final boolean sym;
	private final int symMid;

	public MixAll(List<A> list) { this(list, false); }
	
	// Symmetrical if ABCDE = EDCBA
	public MixAll(List<A> list, boolean symmetrical) {
		l = list;
		n = list.size();

		long p = n == 0 ? 0 : 1;
		int m = n;
		while (m > 1) p *= m--;
		max = symmetrical ? (p + 1) / 2 : p;
		sym = symmetrical;
		symMid = n % 2 == 1 && symmetrical ? (n+1) / 2 : -1;
	}

	@Override
	public Iterator<List<A>> iterator() {
		return new Iterator<List<A>>() {
			private boolean[] used = new boolean[n];
			private long id = 0;
			private long provided = 0;
			public boolean hasNext() { return provided < max; }
			public List<A> next() {
				List<A> list = null;
				do {
					int first = 0;
					for (int i = 0; i < n; i++) used[i] = false;
					list = new ArrayList<>();
					long c = id;
					for (int i = 0; i < n; i++) {
						int u = n - i;
						int p = Math.floorMod(c, u);
						int x = 0;
						while (used[x] || p != 0) {
							while (used[x]) x++;
							if (p != 0) { x++; p--; }
						}
						if (sym && x == first && i > (n-1) / 2) break;
						used[x] = true;
						if (u == symMid && x == 0) first = 1;
						list.add(l.get(x));
						c /= u;
					}
					id++;
				} while (list.size() < n);
				provided++;
				return list;
			}
		};
	}
}
