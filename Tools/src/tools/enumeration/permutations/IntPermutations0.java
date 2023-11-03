package tools.enumeration.permutations;

import java.util.Iterator;

public class IntPermutations0 implements Iterable<int[]> {

	private final int n;
	public final long nb;
	private final int[] t;

	public IntPermutations0(int n) {
		this.n = n;
		t = new int[n];
		long m = 1;
		for (int i = 2; i <= n; i++) m *= i;
		nb = n == 0 ? 0 : m;
	}

	@Override
	public Iterator<int[]> iterator() {
		return new Iterator<int[]>() {
			private long provided = 0;
			public boolean hasNext() { return provided < nb; }
			public int[] next() {
				int used = 0;
				long c = provided++;
				for (int i = 0; i < n; i++) {
					int u = n - i;
					int p = (int) Math.floorMod(c, u);
					int x = 0;
					while ((used & 1<<x) != 0 || p != 0) {
						while ((used & 1<<x) != 0) x++;
						if (p != 0) { x++; p--; }
					}
					used |= 1<<x;
					t[i] = x;
					c /= u;
				}
				return t;
			}
		};
	}
}
