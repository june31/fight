package tools.enumeration.combinations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BoolCombinations implements Iterable<List<Boolean>> {

	private final int n;
	private final int c;
	private final long max;

	public BoolCombinations(int nTrue, int nFalse) {
		n = nTrue + nFalse;
		this.c = nFalse;
		max = cnp(n, c);
	}
	
	@Override
	public Iterator<List<Boolean>> iterator() {
		return new Iterator<List<Boolean>>() {
			private long id = 0;
			private long provided = 0;
			public boolean hasNext() { return provided < max; }
			public List<Boolean> next() {
				while (Long.bitCount(id) != c) id++;
				List<Boolean> list = new ArrayList<>(n);
				for (int i = 0; i < n; i++) list.add((id & 1<<i) == 0);
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
	
	public static void main(String[] args) {
        for (var l: new BoolCombinations(5, 2)) {
            System.out.println(l);
        }
    }
}
