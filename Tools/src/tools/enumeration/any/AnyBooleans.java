package tools.enumeration.any;

import java.util.Iterator;

public class AnyBooleans implements Iterable<boolean[]> {

	private final boolean[] t;
	private final int n;
	private final long max;

	public AnyBooleans(int size) {
		t = new boolean[size];
		n = t.length;
		max = 1 << n;
	}

	@Override
	public Iterator<boolean[]> iterator() {
		return new Iterator<boolean[]>() {
			private long id = 0;
			public boolean hasNext() { 
				return id < max;
			}
			public boolean[] next() {
				for (int i = 0; i < n; i++) t[i] = (id>>i & 1) != 0;
				id++;
				return t; 
			}
		};
	}
}
