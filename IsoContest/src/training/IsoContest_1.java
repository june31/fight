package training;

import tools.scanner.Scan;

public class IsoContest_1 {

	public static void main(String[] args) {
		int o = Scan.readInt();
		int n = Scan.readInt();
		int sum = 0;
		int[] w = new int[n];
		for (int i = 0; i < n; i++) {
			int h = Scan.readInt();
			w[i] = h;
			sum += h;
		}
		int min = sum;
		if (o > sum) System.out.println(0);
		else {
			int limit = (sum - o) / 2 + 1;
			int[][] T = new int[2][limit];
			for (int i = 0; i < n; i++) {
				for (int c = 1; c < limit; c++) {
					int cur = i & 1;
					int old = cur ^ 1;
					int v;
					if (c >= w[i]) v = Math.max(T[old][c], T[old][c-w[i]] + w[i]);
					else v = T[old][c];
					T[cur][c] = v;
					int z = (v == T[old][c]) ? v + w[i] : 0;
					if (z >= limit && min > z) min = z;
				}
			}
			System.out.println(min);
		}
	}
}