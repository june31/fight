package summer24;

import java.util.Random;

public class _BowTester {
	public static void main(String[] args) {
		Random rnd = new Random(0);
		
		for (int i = 0; i < 20; i++) {
			int n = rnd.nextInt(10) + 1;
			int[] a = new int[n];
			for (int j = 0; j < n; j++) {
				a[j] = rnd.nextInt(9) + 1;
			}

		}
		
	}
}
