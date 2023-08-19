package tools.enumeration.permutations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MixPermutations<A> implements Iterable<List<A>> {

	private final List<A> l;
	private final int n;
	public final long nb;

	public MixPermutations(List<A> list) {
		l = list;
		n = list.size();
		long m = 1;
		for (int i = 2; i <= n; i++) m *= i;
		nb = n == 0 ? 0 : m;
	}

	@Override
	public Iterator<List<A>> iterator() {
		return new Iterator<List<A>>() {
			private long provided = 0;
			public boolean hasNext() { return provided < nb; }
			public List<A> next() {
				List<A> list = new ArrayList<>(n);
				int used = 0;
				long c = provided++;
				for (int i = 0; i < n; i++) {
					int u = n - i;
					int p = (int) Math.floorMod(c, u);
					int x = 0;
					while ((used & 1<<x) != 0 || p != 0) {
						while ((used & 1<<x) != 0) x++;
						if (p != 0) { x++; p--; }
					}
					used |= 1<<x;
					list.add(l.get(x));
					c /= u;
				}
				return list;
			}
		};
	}
}
