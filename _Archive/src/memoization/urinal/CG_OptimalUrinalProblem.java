package memoization.urinal;

import tools.math.Num;
import tools.scanner.Scan;
import tools.tuple.IL;

// The Optimal Urinal Problem
// https://www.codingame.com/training/medium/the-optimal-urinal-problem
class CG_OptimalUrinalProblem {
    private static final int MAX = 1_500_000 + 1;
    private static final int[] cache = new int[MAX];
    
	public static void main(String[] args) {
		int n = Scan.readInt();
		IL max = Num.maxLong(n, x -> {
			int s = 1;
			if (x >= 2) s += 1 + score(x - 3);
			if (x <= n-3) s += 1 + score(n - x - 4);
			return s;
		});
		System.out.println(max.value + " " + (max.index + 1));
	}

	private static int score(int n) {
		if (n <= 0) return 0;
		if (cache[n] != 0) return cache[n];
		cache[n] = 1 + score(n/2 - 1) + score((n+1)/2 - 2);
		return cache[n];
	}
}
