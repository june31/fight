package tools.structures.segmenttree;

import java.util.function.BiFunction;

public class SegmentTreeInt {
	
	private int[][] tab;
	private int n;
	private int defaultValue;
	private BiFunction<Integer, Integer, Integer> func;
	
	public SegmentTreeInt(int[] vals, BiFunction<Integer, Integer, Integer> f) { this(vals, 0, f); }
	
	public SegmentTreeInt(int[] vals, int def, BiFunction<Integer, Integer, Integer> f) {
		defaultValue = def;
		func = f;
		n = vals.length;
		if (n == 0) return;
		int log = log2(n) + 1;
		tab = new int[log][];
		tab[0] = vals;
		int size = n / 2;
		for (int i = 1; i < log; i++) {
			tab[i] = new int[size];
			for (int j = 0; j < size; j++) tab[i][j] = f.apply(tab[i-1][j*2], tab[i-1][j*2 + 1]); 
			size /= 2;
		}
	}
	
	public int get(int a, int b) {
		if (a > b) return defaultValue;
		b++;
		int res = 0;
		boolean start = true;
		while (a != b) {
			int z = trailingZeros(a);
			while (a + (1<<z) > b) z--;
			if (start) {
				start = false;
				res = tab[z][a >> z];
			} else {
				res = func.apply(res, tab[z][a >> z]);
			}
			a += 1<<z;
		}
		return res;
	}

	public void set(int a, int val) {
		int mask = ~1;
		tab[0][a] = val;
		int j = a;
		for (int i = 1; i < tab.length && a < (n & mask); i++) {
			int r = j & 1;
			tab[i][j / 2] = r == 0 ? func.apply(tab[i-1][j], tab[i-1][j+1]) : func.apply(tab[i-1][j-1], tab[i-1][j]);
			j /= 2;
			mask ^= 1 << i;
		}
	}
	
	private static int log2(int i) { return 31 - Integer.numberOfLeadingZeros(i); }
	private static int trailingZeros(int i) { return i == 0 ? 30 : Integer.numberOfTrailingZeros(i); } // So that 1<<tz(0) > 0
}
