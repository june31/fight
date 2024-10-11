package currentCG;

import tools.math.Num;
import tools.scanner.Scan;
import tools.strings.S;
import tools.tables.Table;

public class CGS_Clash {
	
    public static void main(String[] args) throws Exception {
    	int w = Scan.readInt();
		int h = Scan.readInt();
		int[][] ref = Table.wall(Scan.readIntArray(h, w), 0);
		int[][] t = new int[h + 1][w + 1];
		for (int i = 1; i <= h; i++)
			for (int j = 1; j <= w; j++) {
				int I = i, J = j;
				t[i][j] = Num.max(ref[i][j], Num.max(i, k -> t[k][J] + t[I-k][J]).b(), Num.max(j, k -> t[I][k] + t[I][J-k]).b()); 
			}
		S.o(t[h][w]);
    }
}