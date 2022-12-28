package tools.enumeration.any;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import tools.enumeration.SelectMode;
import tools.enumeration.permutations.MixPermutation;

public class MixAny<A> implements Iterable<List<A>> {

	private final List<A> l;
	private final int n;
	private final long max;
	private final SelectMode select;
	private Iterator<List<A>> subIterator;

	/** ALL mode by default */
	public MixAny(List<A> list) { this(list, SelectMode.ALL); }

	public MixAny(List<A> list, SelectMode mode) {
		select = mode;
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
				if (select != SelectMode.ANY) {
					subIterator = new MixPermutation<A>(list, select).iterator();
					list = subIterator.next();
				}
				id++;
				return list; 
			}
		};
	}

	public static void main(String[] args) {
		MixAny<String> mix = new MixAny<>(List.of("A", "B", "C", "D", "E"), SelectMode.ALL);
		Set<String> used = new HashSet<>();
		int i = 0;
		int dup = 0;
		for (var l : mix) {
			String s = l.toString();
			System.out.println(s + (used.contains(s) ? " *" : ""));
			if (used.contains(s)) dup++; 
			Collections.reverse(l);
			used.add(l.toString());
			i++;
		}
		System.out.println(i + " - " + dup);
	}
}
