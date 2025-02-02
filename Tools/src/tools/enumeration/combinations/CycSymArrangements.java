package tools.enumeration.combinations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CycSymArrangements<A> implements Iterable<List<A>> {

	public final List<A> l;
	public final int n;
	public final int c;
	public final long max;

	public CycSymArrangements(A[] table, int c) {
		l = new ArrayList<>();
		for (A a: table) l.add(a);
		n = table.length;
		this.c = c;
		max = n < c ? 0 : f(n) / ((c<3?1:2) * (c==0?1:c) * f(n-c));
	}
	
	public CycSymArrangements(List<A> list, int c) {
		l = list;
		n = list.size();
		this.c = c;
		max = n < c ? 0 : f(n) / ((c<3?1:2) * (c==0?1:c) * f(n-c));
	}

	@Override
	public Iterator<List<A>> iterator() {
		return new Iterator<List<A>>() {
			private long provided = 0;
			private long id = 0;
			public boolean hasNext() { return provided < max; }
			public List<A> next() {
				List<A> list = new ArrayList<>(n);
				do {
					int used = 0;
					int first = 0;
					int lowest = n;
					long z = id++;
					for (int i = 0; i < c; i++) {
						int u = n - i;
						int p = (int) Math.floorMod(z, u);
						int x = 0;
						while ((used & 1<<x) != 0 || p != 0) {
							while ((used & 1<<x) != 0) x++;
							if (p != 0) { x++; p--; }
						}
						if (i == 0) first = x;
						else {
							if (x < lowest && 2 * i < c) lowest = x;
							if (x < first || (x < lowest && 2 * i > c)) { list.clear(); break; }
						}
						used |= 1<<x;
						list.add(l.get(x));
						z /= u;
					}
				} while (list.size() < c);
				provided++;
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
