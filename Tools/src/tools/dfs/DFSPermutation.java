package tools.dfs;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntBiFunction;

import tools.misc.Copyable;

public class DFSPermutation<A, B extends Copyable<B>> {
	
	public final int n;
	private final A[] t;
	private final B[] states;
	public final long[] masks;
	private final int[] indexes;
	public int depth = 0;
	final private ToIntBiFunction<B, A> f;

	// If bestEffort, the best solution will calculated. If !bestEffort, only the exact solution will be searched for.
	final private boolean bestEffort;
	public int max = Integer.MIN_VALUE;
	public List<A> best;

	public DFSPermutation(A[] objects, B initialState, ToIntBiFunction<B, A> func) { this(objects, initialState, true, func); }
	@SuppressWarnings("unchecked")
	public DFSPermutation(A[] objects, B initialState, boolean bestEffort, ToIntBiFunction<B, A> func) {
		n = objects.length;
		t = objects;
		this.bestEffort = bestEffort;
		states = (B[]) (Array.newInstance(initialState.getClass(), n + 1));
		for (int i = 0; i < n; i++) {
			try {
				states[i] = (B) initialState.getClass().getConstructor().newInstance();
			} catch (Exception e) {
				throw new IllegalArgumentException("Copyable class " + initialState.getClass() + " shall have an empty constructor!");
			}
		}
		initialState.copyTo(states[0]);
		masks = new long[n];
		indexes = new int[n];
		f = func;
	}
	
	public List<A> findNext() {
		best = new ArrayList<>(n);
		while (depth >= 0) {
			states[depth].copyTo(states[depth+1]);
			int r = f.applyAsInt(states[depth+1], t[indexes[depth]]);
			if (r == Integer.MIN_VALUE) {
				depth--;
				continue;
			}
			if (bestEffort && max < r) {
				max = r;
				best.clear();
				for (int i = 0; i < depth; i++) best.add(t[indexes[i]]);
			}
			if (depth == n) {
				while () {
					
				}
			} else {
			}
			if (r == Integer.MAX_VALUE) {
				System.out.println("found");
				if (!bestEffort) {
					
				}
				return best;
			}
				
		A result = null;
		long used = 0;
		long c = 111;
		for (int i = 0; i < n; i++) {
			int u = n - i;
			int p = Math.floorMod(c, u);
			int x = 0;
			while ((used & 1<<x) != 0 || p != 0) {
				while ((used & 1<<x) != 0) x++;
				if (p != 0) { x++; p--; }
			}
			used |= 1<<x;
			//list.add(l.get(x));
			c /= u;
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		Zz z = new Zz();
		var d = new DFSPermutation<String, Zz>(new String[1], z, null);
		z.clone();
	}
}

class Zz implements Copyable {
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
}