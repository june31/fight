package tooltests.reference;

import tools.math.Dist;
import tools.reference.Dijkstra;
import tools.scanner.Scan;

// https://www.codechef.com/problems/REN2013G
public class CC_DijkstraEscape {
	public static void main(String[] args) {
		int n = Scan.readInt() + 2;
		int[] t1 = new int[n];
		int[] t2 = new int[n];
		for (int i = 1; i < n; i++) {
			t1[i] = Scan.readInt();
			t2[i] = Scan.readInt();
		}
		
		int[][] t = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				t[i][j] = Dist.square(t1[i], t2[i], t1[j], t2[j]);
				t[j][i] = Dist.square(t1[i], t2[i], t1[j], t2[j]);
			}
		}
		
		System.out.println(new Dijkstra(t).calc(0)[n-1]);
	}
}
