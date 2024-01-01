package tools;

import java.util.function.ToIntFunction;

public class Test {
	public static <A> int max(A[] t, ToIntFunction<A> f) {
		int best = -1;
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < t.length; i++) {
			int v = f.applyAsInt(t[i]);
			if (max < v) {
				max = v;
				best = i;
			}
		}
		foo();
		return best;
	}
	public static void foo() {
		System.out.println("Foo");
	}
}
