package tools.enumeration.any;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tools.enumeration.permutations.MixCycSymPermutations;

public class MixAnyCycSym<A> implements Iterable<List<A>> {

	private final List<A> l;
	private final int n;
	private final long max;
	private Iterator<List<A>> subIterator;

	public MixAnyCycSym(List<A> list) {
		l = list;
		n = list.size();
		max = 1 << n;
	}

	@Override
	public Iterator<List<A>> iterator() {
		return new Iterator<List<A>>() {
			private long id = 0;
			public boolean hasNext() { 
				if (subIterator != null && subIterator.hasNext()) return true;
				return id < max;
			}
			public List<A> next() {
				if (subIterator != null && subIterator.hasNext()) return subIterator.next();
				List<A> list = new ArrayList<>();
				for (int i = 0; i < n; i++) if ((id & 1<<i) != 0) list.add(l.get(i));
				subIterator = new MixCycSymPermutations<A>(list).iterator();
				id++;
				return subIterator.next(); 
			}
		};
	}
}
