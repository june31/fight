package tools.structures.segmenttree;

import java.util.function.BiFunction;

public class SegmentTreeIntIdx {
	
	private int[] ref;
	private int[][] tab;
	private int n;
	private BiFunction<Integer, Integer, Boolean> func;
	
	public SegmentTreeIntIdx(int[] vals, BiFunction<Integer, Integer,  Boolean> f) {
		ref = vals;
		func = f;
		n = vals.length;
		if (n == 0) return;
		int log = log2(n) + 1;
		tab = new int[log][];
		tab[0] = new int[n];
		for (int i = 0; i < n; i++) tab[0][i] = i;
		int size = n / 2;
		for (int i = 1; i < log; i++) {
			tab[i] = new int[size];
			for (int j = 0; j < size; j++) tab[i][j] = f.apply(ref[tab[i-1][j*2]], ref[tab[i-1][j*2 + 1]]) ? tab[i-1][j*2] : tab[i-1][j*2 + 1];  
			size /= 2;
		}
	}
	
	public int getIndex(int a, int b) {
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
				res = func.apply(ref[res], ref[tab[z][a >> z]]) ? res : tab[z][a >> z];
			}
			a += 1<<z;
		}
		return res;
	}

	public void set(int a, int val) {
		int mask = ~1;
		ref[a] = val;
		int j = a;
		for (int i = 1; i < tab.length && a < (n & mask); i++) {
			int r = j & 1;
			tab[i][j / 2] = r == 0
					? (func.apply(ref[tab[i-1][j]], ref[tab[i-1][j+1]]) ? tab[i-1][j] : tab[i-1][j+1])
					: (func.apply(ref[tab[i-1][j-1]], ref[tab[i-1][j]]) ? tab[i-1][j-1] : tab[i-1][j]);
			j /= 2;
			mask ^= 1 << i;
		}
	}
	
	private static int log2(int i) { return 31 - Integer.numberOfLeadingZeros(i); }
	private static int trailingZeros(int i) { return i == 0 ? 30 : Integer.numberOfTrailingZeros(i); } // So that 1<<tz(0) > 0
}
