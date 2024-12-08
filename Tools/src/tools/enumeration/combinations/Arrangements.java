package tools.enumeration.combinations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Arrangements<A> implements Iterable<List<A>> {

	public final List<A> l;
	public final int n;
	public final int c;
	public final long max;

	public Arrangements(A[] table, int c) {
		l = new ArrayList<>();
		for (A a: table) l.add(a);
		n = table.length;
		this.c = c;
		max = n < c ? 0 : f(n) / f(n-c);
	}

	public Arrangements(List<A> list, int c) {
		l = list;
		n = list.size();
		this.c = c;
		max = n < c ? 0 : f(n) / f(n-c);
	}

	@Override
	public Iterator<List<A>> iterator() {
		return new Iterator<List<A>>() {
			private long provided = 0;
			public boolean hasNext() { return provided < max; }
			public List<A> next() {
				List<A> list = new ArrayList<>(c);
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
					list.add(l.get(x));
					z /= u;
				}
				return list;
			}
		};
	}

	private static long f(int x) {
		long m = 1;
		for (int i = 2; i <= x; i++) m *= i;
		return m;
	}
}
