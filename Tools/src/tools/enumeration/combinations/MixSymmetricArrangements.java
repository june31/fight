package tools.enumeration.combinations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tools.enumeration.permutations.MixSymmetricPermutations;

public class MixSymmetricArrangements<A> implements Iterable<List<A>> {

	private final List<A> l;
	private final int n;
	private final int c;
	private final long max;
	private Iterator<List<A>> subIterator;

	public MixSymmetricArrangements(List<A> list, int c) {
		l = list;
		n = list.size();
		this.c = c;
		max = f(n)/(f(c)*f(n-c));
	}

	@Override
	public Iterator<List<A>> iterator() {
		return new Iterator<List<A>>() {
			private long id = 0;
			private long provided = 0;
			public boolean hasNext() { 
				if (subIterator != null && subIterator.hasNext()) return true;
				return provided < max;
			}
			public List<A> next() {
				if (subIterator != null && subIterator.hasNext()) return subIterator.next();
				while (Long.bitCount(id) != c) id++;
				List<A> list = new ArrayList<>(c);
				for (int i = 0; i < n; i++) if ((id & 1<<i) != 0) list.add(l.get(i));
				subIterator = new MixSymmetricPermutations<A>(list).iterator();
				id++;
				provided++;
				return subIterator.next();
			}
		};
	}

	private static long f(int x) {
		long m = 1;
		for (int i = 2; i <= x; i++) m *= i;
		return m;
	}
}
