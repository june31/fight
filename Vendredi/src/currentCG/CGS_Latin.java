package currentCG;

import tools.enumeration.combinations.IntCombinations1;
import tools.enumeration.permutations.MixPermutations;
import tools.scanner.Scan;
import tools.tables.Table;

public class CGS_Latin {
	private static boolean[][] fix; 
	public static void main(String[] args) {
		int n = Scan.readInt();
		int[][] t = Scan.readMap(n);
		fix = new boolean[n][n];
		Table.forEach(t, (l, c, v) -> fix[l][c] = v != 0);
		int count = 0;
	}
}
