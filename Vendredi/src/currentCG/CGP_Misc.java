package currentCG;

import tools.dichotomy.Search;
import tools.math.Num;
import tools.scanner.Scan;

public class CGP_Misc {
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
		int m = Search.maxTrue(n - 1, i -> dist(i) > dist(i+1));
		System.out.println(main + dist(m));
	}
	
	private static long dist(int y) {
		long d = 0;
		for (int i = 0; i < n; i++) d += Math.abs(ys[i] - y);
		return d;
	}
}
