package tools.dfs;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleBiFunction;

import tools.misc.Copyable;

// Max subset size: 64
public class DFSFastSubSet<A, B extends Copyable<B>> {

	public final int n;
	private final A[] t;
	private final B[] states;
	public long visited = 0;
	public long validNodes = 0;
	public long mask = 1;

	// If bestEffort, the best solution will calculated. If !bestEffort, only the exact solution will be searched for.
	final private boolean bestEffort;
	public double max;
	public List<A> best;

	public DFSFastSubSet(A[] objects, B initialState) { this(objects, initialState, true); }
	@SuppressWarnings("unchecked")
	public DFSFastSubSet(A[] objects, B initialState, boolean bestEffort) {
		n = objects.length;
		if (n > 64) throw new IllegalArgumentException("DFSFastSubSet cannot be used for more than 64 choices. Use DFSSubSet instead.");
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
	}

	public List<A> findNext(ToDoubleBiFunction<B, A> f) {
		best = new ArrayList<>(n);
		return check(f);
	}
	
	// It is assumed that f returns increasing values, and initial state equals 0;
	private List<A> check(ToDoubleBiFunction<B, A> f) {
		if (mask == 0) return null; 
		max = 0;
		long bit = Long.highestOneBit(mask);
		int pos = Long.numberOfTrailingZeros(bit) + 1;
		while (true) {
			double r = f.applyAsDouble(states[pos], t[pos-1]);
			visited++;
			if ((bestEffort && max < r) || r == Double.POSITIVE_INFINITY) {
				max = r;
				best.clear();
				for (int h = 0; h < n; h++) if ((mask & 1<<h) != 0) best.add(t[h]);
			}
			if (r > Double.NEGATIVE_INFINITY) validNodes++;
			if (bit == 1<<(n-1)) {
				mask ^= bit;
				bit = Long.highestOneBit(mask);
				if (bit == 0) return bestEffort ? best : null;
				mask ^= bit;
			} else if (r == Double.NEGATIVE_INFINITY) {
				mask ^= bit;
			}
			int ref = 64 - Long.numberOfLeadingZeros(mask);
			bit <<= 1;
			mask ^= bit;
			pos = Long.numberOfTrailingZeros(bit) + 1;
			states[ref].copyTo(states[pos]);
			if (r == Double.POSITIVE_INFINITY) return best;
		}
	}
}
