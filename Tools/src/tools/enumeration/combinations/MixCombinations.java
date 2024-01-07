package tools.enumeration.combinations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MixCombinations<A> implements Iterable<List<A>> {

	private final List<A> l;
	private final int n;
	private final int c;
	private final long max;

	public MixCombinations(List<A> list, int c) {
		l = list;
		n = list.size();
		this.c = c;
		max = cnp(n, c);
	}

	@Override
	public Iterator<List<A>> iterator() {
		return new Iterator<List<A>>() {
			private long id = 0;
			private long provided = 0;
			public boolean hasNext() { return provided < max; }
			public List<A> next() {
				while (Long.bitCount(id) != c) id++;
				List<A> list = new ArrayList<>(c);
				for (int i = 0; i < n; i++) if ((id & 1<<i) != 0) list.add(l.get(i));
				id++;
				provided++;
				return list;
			}
		};
	}

	private static long cnp(int n, int p) {
		long m = 1;
		for (int i = 0; i < p; i++) m = m * (n-i) / (i + 1);
		return m;
	}
}
