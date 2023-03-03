package codingame;

import tools.math.Num;
import tools.scanner.Scan;
import tools.tuple.IL;

public class Solution_ {
    private static final int MAX = 1_500_000 + 1;
    private static final int[] cache = new int[MAX];
    
	public static void main(String[] args) {
		int n = Scan.readInt();
		IL max = score(n);
		System.out.println(max.b + " " + (max.a + 1));
	}

	private static IL score(int n) {
		if (n <= 0) return new IL(0, 0);
		if (cache[n] != 0) return new IL(0, cache[n]);
		IL max = Num.max(n, x -> 1 + score(x - 1).b + score(n - x - 1).b);
		cache[n] = (int) max.b;
		return max;
	}
}
