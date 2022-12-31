package tools.enumeration.permutations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import tools.enumeration.SelectMode;

public class MixPermutation<A> implements Iterable<List<A>> {

	private final List<A> l;
	private final int n;
	private final long max;
	private final SelectMode mode;
	private final int symMid;

	/** ALL mode by default */
	public MixPermutation(List<A> list) { this(list, SelectMode.ALL); }
	
	public MixPermutation(List<A> list, SelectMode mode) {
		this.mode = mode;
		l = list;
		n = list.size();

		long p = 1;
		int m = n;
		while (m > 1) p *= m--;
		switch (mode) {
		case CYCLIC: max = n == 0 ? 1 : p / n; break;
		case SYMMETRICAL: max = (p + 1) / 2; break;
		case ANY: max = 1; break;
		default: max = p; // NORMAL
		}
		symMid = mode == SelectMode.SYMMETRICAL && n % 2 == 1 ? (n + 1) / 2 : -1;
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
					int depth = n;
					if (mode == SelectMode.CYCLIC && depth > 0) {
						list.add(l.get(0));
						depth--;
						used[0] = true;
					}
					for (int i = 0; i < depth; i++) {
						int u = depth - i;
						int p = Math.floorMod(c, u);
						int x = 0;
						while (used[x] || p != 0) {
							while (used[x]) x++;
							if (p != 0) { x++; p--; }
						}
						if (mode == SelectMode.SYMMETRICAL && x == first && i > (n-1) / 2) break;
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
	
	public static void main(String[] args) {
		MixPermutation<String> mix = new MixPermutation<>(List.of("A", "B", "C", "D", "E", "F", "G"), SelectMode.ANY);
		Set<String> used = new HashSet<>();
		int i = 0;
		for (var l : mix) {
			String s = l.toString();
			System.out.println(s + (used.contains(s) ? " *" : ""));
			Collections.reverse(l);
			used.add(l.toString());
			i++;
		}
		System.out.println(i);
	}
}
