package tools.enumeration.combinations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Combinations<A> implements Iterable<List<A>> {

	private final List<A> l;
	private final int n;
	private final int c;
	private final long max;

	public Combinations(A[] table, int c) {
		l = new ArrayList<>();
		for (A a: table) l.add(a);
		n = table.length;
		this.c = c;
		max = cnp(n, c);
	}

	public Combinations(List<A> list, int c) {
		l = list;
		n = list.size();
		this.c = c;
		max = cnp(n, c);
	}

	@Override
	public Iterator<List<A>> iterator() {
		return new Iterator<List<A>>() {
			private long provided = 0;
			private int[] x = new int[c];
			{ for (int i = 0; i < c; i++) x[i] = i; }
			
			public boolean hasNext() { return provided < max; }
			public List<A> next() {
				List<A> list = new ArrayList<>(c);
				for (int i = 0; i < c; i++) list.add(l.get(x[i]));
				provided++;
				if (c != 0 && provided < max) {
					int a = c - 1;
					x[a]++;
					while (x[a] == n - c + a + 1) {
						a--;
						int z = x[a] + 1;
						for (int i = a; i < c; i++) x[i] = z + i - a;
					}
				}
				return list;
			}
		};
	}

	private static long cnp(int n, int p) {
		long m = 1;
		for (int i = 0; i < p; i++) m = m * (n-i) / (i + 1);
		return m;
	}
	
	public static void main(String[] args) {
		var ls = new Combinations<>(new Integer[] { 0, 1, 2, 3, 4 }, 0);
		for (var l : ls)
			System.out.println(l);
	}
}
