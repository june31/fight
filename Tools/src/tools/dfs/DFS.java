package tools.dfs;

import java.lang.reflect.Array;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class DFS<A> {

	public A[] t;

	@SuppressWarnings("unchecked")
	public DFS(Class<A> clazz, int depth, Consumer<A> def, Consumer<A> init) {
		try {
			t = (A[]) Array.newInstance(clazz, depth);
			for (int i = 0; i < t.length; i++) {
				t[i] = clazz.getConstructor().newInstance();
				def.accept(t[i]);
			}
			init.accept(t[0]);
		} catch (Exception ex) { throw new IllegalArgumentException(ex); }
	}
	
	@SuppressWarnings("unchecked")
	public final void run(final Predicate<A> precondition, final BiPredicate<A, A>... conds) {
		int[] currents = new int[t.length];
		int depth = 0;
		DFS: while (depth >= 0) {
			A a0 = t[depth];
			if (precondition.test(a0)) {
				A a1 = t[depth + 1];
				for (int i = currents[depth]; i < conds.length; i++) {
					currents[depth]++;
					if (conds[i].test(a0, a1)) {
						depth++;
						currents[depth + 1] = 0;
						continue DFS;
					}
				}
			}
			depth--;
		}
	}
}
