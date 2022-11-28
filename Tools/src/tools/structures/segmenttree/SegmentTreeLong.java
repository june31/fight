package tools.structures.segmenttree;

import java.util.function.BiFunction;

public class SegmentTreeLong {

	private long[][] tab;
	private int n;
	private long defaultValue;
	private BiFunction<Long, Long, Long> func;
	
	public SegmentTreeLong(long[] vals, BiFunction<Long, Long, Long> f) { this(vals, 0l, f); }
	
	public SegmentTreeLong(long[] vals, long def, BiFunction<Long, Long, Long> f) {
		defaultValue = def;
		func = f;
		n = vals.length;
		if (n == 0) return;
		int log = log2(n) + 1;
		tab = new long[log][];
		tab[0] = vals;
		int size = n / 2;
		for (int i = 1; i < log; i++) {
			tab[i] = new long[size];
			for (int j = 0; j < size; j++) tab[i][j] = f.apply(tab[i-1][j*2], tab[i-1][j*2 + 1]); 
			size /= 2;
		}
	}
	
	public long get(int a, int b) {
		if (a > b) return defaultValue;
		b++;
		long res = 0;
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

	public void set(int a, long val) {
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
