package currentCG;

import tools.dichotomy.Search;
import tools.math.Num;
import tools.scanner.Scan;
import tools.tuple.LL;

public class CGS_Misc {
	static int n;
	static int[] ys;
	public static void main(String[] args) {
		n = Scan.readInt();
		int[] xs = new int[n];
		ys = new int[n];
		for (int i = 0; i < n; i++) {
			xs[i] = Scan.readInt();
			ys[i] = Scan.readInt();
		}
		long main = Num.max(xs) - Num.min(xs);
		LL m = Search.minLong(i -> dist(i));
		System.out.println(main + m.value);
	}
	
	private static long dist(long y) {
		long d = 0;
		for (int i = 0; i < n; i++) d += Math.abs(ys[i] - y);
		return d;
	}
}
