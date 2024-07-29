package tooltests.solver;

import tools.collections.multi.Lsi;
import tools.collections.string.Ls;
import tools.scanner.Scan;
import tools.scanner.list.ScanLs;
import tools.scanner.list.ScanLsi;
import tools.solver.Solver;
import tools.strings.S;

// https://www.codingame.com/ide/puzzle/the-barnyard
public class CGS_Barnyard {

	public static void main(String[] args) {
		Ls animals = Ls.of("Rabbits", "Chickens", "Cows", "Pegasi", "Demons");
		Ls parts = Ls.of("Heads", "Horns", "Legs", "Wings", "Eyes");
		double[][] map = {
				{ 1, 1, 1, 1, 1 }, // Heads
				{ 0, 0, 2, 0, 4 }, // Horns
				{ 4, 2, 4, 4, 4 }, // Legs
				{ 0, 2, 0, 2, 2 }, // Wings
				{ 2, 2, 2, 2, 4 }  // Eyes
		};
				
		int n = Scan.readInt();
		Ls lAn = ScanLs.readLine();
		Lsi lsi = ScanLsi.read(n);

		double[][] unk = new double[n][n];
		double[] res = new double[n];
		for (int i = 0; i < n; i++) {
			int p = parts.indexOf(lsi.get(i).s);
			res[i] = lsi.get(i).i;
			for (int j = 0; j < n; j++) unk[i][j] = map[p][animals.indexOf(lAn.get(j))];
		}
		
		double[] ans = Solver.linearSolve(unk, res);
		for (int i = 0; i < n; i++) S.o(lAn.get(i), (int) ans[i]); 
	}
}
