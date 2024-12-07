package tools.enumeration.any;

import java.util.Iterator;

public class AnyInts implements Iterable<int[]> {

	private final int[] t;
	private final int n;
	private final long maxN;
	private final int maxV;

	public AnyInts(int tabSize, int nb) {
		t = new int[tabSize];
		n = t.length;
		maxN = (long) Math.pow(nb, tabSize);
		maxV = nb;
	}

	@Override
	public Iterator<int[]> iterator() {
		return new Iterator<int[]>() {
			private long id = 0;
			public boolean hasNext() { 
				return id < maxN;
			}
			public int[] next() {
				long idc = id;
				for (int i = 0; i < n; i++) {
					t[i] = Math.floorMod(idc, maxV);
					idc /= maxV;
				}
				id++;
				return t; 
			}
		};
	}
}
