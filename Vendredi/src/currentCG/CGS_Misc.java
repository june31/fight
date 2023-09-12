package currentCG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tools.scanner.Scan;

public class CGS_Misc {
	public static void main(String[] args) {
		int n = Scan.readInt();
		int c = Scan.readInt();
		int[] t = Scan.readIntArray(n);
		Arrays.sort(t);
		List<Integer> g = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			int v = c / (n - i);
			int z = t[i] < v ? t[i] : v;
			c -= z;
			g.add(z);
		}
		if (c > 0) System.out.println("IMPOSSIBLE");
		else for (int i: g) System.out.println(i);
	}
}
