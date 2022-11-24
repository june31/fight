package tools.structures.sparsetable;

import java.lang.reflect.Array;
import java.util.function.BiFunction;

public class SparseTable<A> {
	private A defaultValue;
	private A[] tab;
	private int n;
	private BiFunction<A, A, A> func;
	
	public SparseTable(Class<A> clazz, A[] vals, BiFunction<A, A, A> f) { this(clazz, vals, null, f); }
	
	@SuppressWarnings("unchecked")
	public SparseTable(Class<A> clazz, A[] vals, A def, BiFunction<A, A, A> f) {
		defaultValue = def;
		func = f;
		n = vals.length;
		int log = log2(n) + 1;
		tab = (A[]) Array.newInstance(clazz, n * log);
		System.arraycopy(vals, 0, tab, 0, n);
		int start = 0;
		int prevStart = 0;
		int end = 1;
		for (int i = 1; i < log; i++) {
			start += n;
			int limit = n - end;
			for (int j = 0; j < n; j++) {
				tab[start + j] = j < limit ? f.apply(tab[prevStart + j], tab[prevStart + j + end]) : tab[prevStart + j]; 
			}
			prevStart += n;
			end *= 2;
		}
	}
	
	public A get(int a, int b) {
		if (a > b) return defaultValue;
		if (a == b) return tab[a];
		int la = log2(b - a);
		return func.apply(tab[la * n + a], tab[la * n + b + 1 - (1<<la)]);
	}

	private static int log2(int i) { return 31 - Integer.numberOfLeadingZeros(i); }
}
