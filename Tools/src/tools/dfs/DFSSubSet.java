package tools.dfs;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntBiFunction;

import tools.misc.Copyable;

public class DFSSubSet<A, B extends Copyable<B>> {

	public final int n;
	private final A[] t;
	private final B[] states;
	public int depth = 0;
	public long visited = 0;
	public long validNodes = 0;
	public long mask = 0;

	// If bestEffort, the best solution will calculated. If !bestEffort, only the exact solution will be searched for.
	final private boolean bestEffort;
	public int max;
	public List<A> best;

	public DFSSubSet(A[] objects, B initialState) { this(objects, initialState, true); }
	@SuppressWarnings("unchecked")
	public DFSSubSet(A[] objects, B initialState, boolean bestEffort) {
		n = objects.length;
		t = objects;
		this.bestEffort = bestEffort;
		states = (B[]) (Array.newInstance(initialState.getClass(), n + 1));
		for (int i = 0; i <= n; i++) {
			try {
				states[i] = (B) initialState.getClass().getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				throw new IllegalArgumentException("Copyable class " + initialState.getClass() + " shall be public and have an empty constructor!");
			}
		}
		initialState.copyTo(states[0]);
	}

	public List<A> findNext(ToIntBiFunction<B, A> f) {
		max = Integer.MIN_VALUE;
		best = new ArrayList<>(n);
		return check(f);
	}
	
	private List<A> check(ToIntBiFunction<B, A> f) {
		if (depth == n) return null;
		states[depth].copyTo(states[depth+1]);
		depth++;
		var l = check(f);
		if (l != null) return l;
		mask |= 1<<depth;
		mask &= (1<<(depth+1)) - 1;
		int r = f.applyAsInt(states[depth], t[depth]);
		if ((bestEffort && max < r) || r == Integer.MAX_VALUE) {
			max = r;
			best.clear();
			for (int h = 0; h <= depth; h++) if ((mask & 1<<h) == 1) best.add(t[h]);
		}
		if (r == Integer.MAX_VALUE) return best;
		return null;
	}
}
