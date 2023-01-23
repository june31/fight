package tools.structures.segmenttree;

import java.lang.reflect.Array;
import java.util.function.BiFunction;

public class SegmentTree<A> {

	private A[][] tab;
	private int n;
	private A defaultValue;
	private BiFunction<A, A, A> func;
	
	public SegmentTree(Class<A> clazz, A[] vals, BiFunction<A, A, A> f) { this(clazz, vals, null, f); }
	
	@SuppressWarnings("unchecked")
	public SegmentTree(Class<A> clazz, A[] vals, A def, BiFunction<A, A, A> f) {
		defaultValue = def;
		func = f;
		n = vals.length;
		if (n == 0) return;
		int log = log2(n) + 1;
		A[] subTab = (A[]) Array.newInstance(clazz, 0);
		tab = (A[][]) Array.newInstance(subTab.getClass(), n * log);
		tab[0] = vals;
		int size = n / 2;
		for (int i = 1; i < log; i++) {
			tab[i] = (A[]) Array.newInstance(clazz, size);
			for (int j = 0; j < size; j++) tab[i][j] = f.apply(tab[i-1][j*2], tab[i-1][j*2 + 1]); 
			size /= 2;
		}
	}
	
	public A get(int a, int b) {
		if (a > b) return defaultValue;
		b++;
		A res = null;
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

	public void set(int a, A val) {
		tab[0][a] = val;
		revalidate(a);
	}
	
	public void revalidate(int a) {
		int mask = ~1;
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
