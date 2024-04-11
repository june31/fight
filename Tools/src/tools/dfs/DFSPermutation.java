package tools.dfs;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntBiFunction;

import tools.misc.Copyable;

public class DFSPermutation<A, B extends Copyable<B>> {

	public final int n;
	private final A[] t;
	private final B[] states;
	private final long[] masks;
	private final int[] indexes;
	public int depth = 0;
	public long visited = 0;
	public long validNodes = 0;

	// If bestEffort, the best solution will calculated. If !bestEffort, only the exact solution will be searched for.
	final private boolean bestEffort;
	public int max;
	public List<A> best;

	public DFSPermutation(A[] objects, B initialState) { this(objects, initialState, true); }
	@SuppressWarnings("unchecked")
	public DFSPermutation(A[] objects, B initialState, boolean bestEffort) {
		n = objects.length;
		t = objects;
		this.bestEffort = bestEffort;
		states = (B[]) (java.lang.reflect.Array.newInstance(initialState.getClass(), n + 1));
		for (int i = 0; i <= n; i++) {
			try {
				states[i] = (B) initialState.getClass().getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				throw new IllegalArgumentException("Copyable class " + initialState.getClass() + " shall be public and have an empty constructor!");
			}
		}
		initialState.copyTo(states[0]);
		masks = new long[n + 1];
		indexes = new int[n + 1];
	}

	public List<A> findNext(ToIntBiFunction<B, A> f) {
		max = Integer.MIN_VALUE;
		best = new ArrayList<>();
		if (n == 0) return null;

		while (depth >= 0) {
			states[depth].copyTo(states[depth + 1]);
			masks[depth] |= 1l << indexes[depth];
			masks[depth + 1] = masks[depth];
			indexes[depth + 1] = 0;
			int r = f.applyAsInt(states[depth + 1], t[indexes[depth]]);
 			if ((bestEffort && max < r) || r == Integer.MAX_VALUE) {
				max = r;
				best.clear();
				for (int h = 0; h <= depth; h++) best.add(t[indexes[h]]);
			}
			if (r == Integer.MIN_VALUE) {
				masks[depth] = depth == 0 ? 0 : masks[depth - 1];
				indexes[depth]++;
			} else validNodes++;
			visited++;
			if (n == 1) {
				if (r == Integer.MAX_VALUE) return best;
				return null;
			}
			if (depth == n - 1) {
				depth--;
				masks[depth] = depth == 0 ? 0 : masks[depth - 1];
				indexes[depth]++;
			} else if (r != Integer.MIN_VALUE) depth++;
			do {
				while ((1<<indexes[depth] & masks[depth]) != 0) indexes[depth]++;
				if (indexes[depth] >= n) {
					depth--;
					if (depth < 0) break;
					masks[depth] = depth == 0 ? 0 : masks[depth - 1];
					indexes[depth]++;
				} else break;
			} while (depth >= 0);
			if (r == Integer.MAX_VALUE) return best;
		}
		return null;
	}
}
