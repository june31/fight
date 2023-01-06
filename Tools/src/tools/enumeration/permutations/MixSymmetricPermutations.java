package tools.enumeration.permutations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MixSymmetricPermutations<A> implements Iterable<List<A>> {

	private final List<A> l;
	private final int n;
	private final long max;
	private final int symMid;

	public MixSymmetricPermutations(List<A> list) {
		l = list;
		n = list.size();
		long m = 1;
		for (int i = 3; i <= n; i++) m *= i;
		max = m;
		symMid = n % 2 == 1 ? (n + 1) / 2 : -1;
	}

	@Override
	public Iterator<List<A>> iterator() {
		return new Iterator<List<A>>() {
			private long id = 0;
			private long provided = 0;
			public boolean hasNext() { return provided < max; }
			public List<A> next() {
				List<A> list = new ArrayList<>(n);
				do {
					int lowest = 0;
					int used = 0;
					long c = id++;
					for (int i = 0; i < n; i++) {
						int u = n - i;
						int p = Math.floorMod(c, u);
						int x = 0;
						while ((used & 1<<x) != 0 || p != 0) {
							while ((used & 1<<x) != 0) x++;
							if (p != 0) { x++; p--; }
						}
						if (x == lowest && i > (n-1) / 2) { list.clear(); break; }
						used |= 1<<x;
						if (u == symMid && x == 0) lowest = 1;
						list.add(l.get(x));
						c /= u;
					}
				} while (list.size() < n);
				provided++;
				return list;
			}
		};
	}
}
