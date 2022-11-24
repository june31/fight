package tools.structures.sparsetable;

import java.util.function.BiFunction;

public class SparseTableDouble {
	private double defaultValue;
	private double[] tab;
	private int n;
	private BiFunction<Double, Double, Double> func;
	
	public SparseTableDouble(long[] vals, BiFunction<Double, Double, Double> f) { this(vals, 0d, f); }
	
	public SparseTableDouble(long[] vals, double def, BiFunction<Double, Double, Double> f) {
		defaultValue = def;
		func = f;
		n = vals.length;
		int log = log2(n) + 1;
		tab = new double[n * log];
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
	
	public double get(int a, int b) {
		if (a > b) return defaultValue;
		if (a == b) return tab[a];
		int la = log2(b - a);
		return func.apply(tab[la * n + a], tab[la * n + b + 1 - (1<<la)]);
	}

	private static int log2(int i) { return 31 - Integer.numberOfLeadingZeros(i); }
}
