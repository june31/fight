package currentCG;

import tools.chrono.Chrono;
import tools.scanner.Scan;

public class CGS_Current {
	public static void main(String[] args) {
		Chrono.start();
		int n = Scan.readInt();
        long maxI = -1;
        long maxV = 0;
        for (int i = 0; i < n; i++) {
        	long s = 1;
			if (i >= 2) s += 1 + score(i - 3);
			if (i <= n-3) s += 1 + score(n - i - 4);
			if (s > maxV) {
				maxV = s;
				maxI = i;
			}
		}
		System.out.println(maxV + " " + (maxI + 1));
		Chrono.stop();
	}

	private static long score(int n) {
		if (n <= 0) return 0;
		return 1 + score(n/2 - 1) + score((n+1)/2 - 2);
	}
}
