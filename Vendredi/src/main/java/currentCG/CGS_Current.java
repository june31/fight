package currentCG;

import tools.collections.int32.L;
import tools.scanner.Scan;
import tools.strings.S;

public class CGS_Current {
	static L l1 = new L();
	static L l2 = new L();
	public static void main(String[] args) {
		for (int i = 1; i <= 20; i++) {
			l1.add(i);
			l1.add(3*i);
		}
		l1.add(25);
		for (int i = 1; i <= 20; i++) l2.add(2*i);
		l2.add(50);
		int s = Scan.readInt();
		int d = Scan.readInt();
		S.o(rec(s, d, false));
	}

	private static int rec(int s, int d, boolean ds) {
		if (s == 0 && ds) return 1;
		if (s <= 0 || d == 0) return 0;
		int z = 0;
		for (int i: l1) z += rec(s-i, d-1, false);
		for (int i: l2) z += rec(s-i, d-1, true);
		return z;
	}
}
