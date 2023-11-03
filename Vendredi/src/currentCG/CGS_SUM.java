package currentCG;

import tools.scanner.Scan;
import tools.tables.Table;

public class CGS_SUM {
	
	public static void main(String[] args) {
        int N = Scan.readInt();
        int S = Scan.readInt();
		long[] vals = new long[2 * (S+1) + 1];
		vals[0] = 1;
		for (int n = 1; n <= N; n++) for (int s = 0; s <= S; s++) {
			vals[(n%2) * (S+1) + s] = 0;
			for (int j = 0; j <= 9; j++) if (s - j >= 0) vals[(n%2) * (S+1) + s] += vals[((n+1)%2)*(S+1) + (s-j)];
			vals[(n%2) * (S+1) + s] %= 4_293_918_721l;
		}
		System.out.println(vals[((N+1)%2) * (S+1) - 1]);
		Table.println(vals);
	}
}
