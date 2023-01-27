package tools.dfs;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntBiFunction;

import tools.tuple.OPair;

public class DFSPermutation<A, B> {
	
	public final int n;
	public final B[] states;
	public final long[] index;
	
	@SuppressWarnings("unchecked")
	public DFSPermutation(A[] objects, B initialState, ConsumerOInt<B>Runnable init, ToIntBiFunction<B, A> func) {
		n = objects.length;
		states = (B[]) (Array.newInstance(initialState.getClass(), n));
	}
	
	public OPair<List<A>, B> process() {
		A result = null;
		long used = 0;
		long c = provided++;
		for (int i = 0; i < n; i++) {
			int u = n - i;
			int p = Math.floorMod(c, u);
			int x = 0;
			while ((used & 1<<x) != 0 || p != 0) {
				while ((used & 1<<x) != 0) x++;
				if (p != 0) { x++; p--; }
			}
			used |= 1<<x;
			list.add(l.get(x));
			c /= u;
		}
		
		return null;
	}
	
	public List<A> next() {
		List<A> list = new ArrayList<>(n);
		int used = 0;
		long c = provided++;
		for (int i = 0; i < n; i++) {
			int u = n - i;
			int p = Math.floorMod(c, u);
			int x = 0;
			while ((used & 1<<x) != 0 || p != 0) {
				while ((used & 1<<x) != 0) x++;
				if (p != 0) { x++; p--; }
			}
			used |= 1<<x;
			list.add(l.get(x));
			c /= u;
		}
		return list;
	}
}
