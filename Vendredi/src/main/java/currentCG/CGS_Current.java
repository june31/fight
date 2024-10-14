package currentCG;

import tools.F;
import tools.scanner.Scan;
import tools.strings.S;
import tools.tables.Table;

public class CGS_Current {

    public static void main(String[] args) throws Exception {
    	int w = Scan.readInt();
		int h = Scan.readInt();
		int[][] t = Table.shift0(Scan.readIntArray(h, w));
		F.for1(h, w, (i, j) -> t[i][j] = Math.max(F.max0(i, k -> t[k][j] + t[i-k][j]).b(), F.max0(j, k -> t[i][k] + t[i][j-k]).b())); 
		S.o(t[h][w]);
    }
}
