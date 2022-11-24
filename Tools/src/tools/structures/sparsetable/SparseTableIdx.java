package tools.structures.sparsetable;

import java.util.function.BiFunction;

public class SparseTableIdx<A> {
	private A[] ref;
	private int[] tab;
	private int n;
	private BiFunction<A, A, Boolean> func;
	
	public SparseTableIdx(Class<A> clazz, A[] vals, BiFunction<A, A, Boolean> f) {
		ref = vals;
		func = f;
		n = vals.length;
		int log = log2(n) + 1;
		tab = new int[n * log];
		for (int i = 0; i < n; i++) tab[i] = i;
		int start = 0;
		int prevStart = 0;
		int end = 1;
		for (int i = 1; i < log; i++) {
			start += n;
			int limit = n - end;
			for (int j = 0; j < n; j++) {
				tab[start + j] = j < limit
						? (f.apply(ref[tab[prevStart + j]], ref[tab[prevStart + j + end]]) ? tab[prevStart + j] : tab[prevStart + j + end])
						: tab[prevStart + j]; 
			}
			prevStart += n;
			end *= 2;
		}
	}
	
	public int getIndex(int a, int b) {
		if (a == b) return a;
		int la = log2(b - a);
		return func.apply(ref[tab[la * n + a]], ref[tab[la * n + b + 1 - (1<<la)]]) ? tab[la * n + a] : tab[la * n + b + 1 - (1<<la)];
	}

	private static int log2(int i) { return 31 - Integer.numberOfLeadingZeros(i); }
}
