package tools.dichotomy;

import java.util.function.Function;

public class Search {

	// The provided function shall return true, true... true, false... false.
	// If all false, maxTrue returns -1. If all true, it returns max.
	public static int maxTrue(int max, Function<Integer, Boolean> f) {
		int l = 0;
		int r = max;
		int res = -1;
		while (l <= r) {
			int i = l + (r - l) / 2;
			if (f.apply(i)) {
				res = i;
				l = i + 1;
			} else r = i - 1;
		}
		return res;
	}
}
