package tools.enumeration.any;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MixAnyNoOrder<A> implements Iterable<List<A>> {

	private final List<A> l;
	private final int n;
	private final long max;

	public MixAnyNoOrder(List<A> list) {
		l = list;
		n = list.size();
		max = 1 << n;
	}

	@Override
	public Iterator<List<A>> iterator() {
		return new Iterator<List<A>>() {
			private long id = 0;
			public boolean hasNext() { return id < max; }
			public List<A> next() {
				List<A> list = new ArrayList<>();
				for (int i = 0; i < n; i++) if ((id & 1<<i) != 0) list.add(l.get(i));
				id++;
				return list;
			}
		};
	}
}
