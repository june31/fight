package training;

import tools.collections.int32.L;
import tools.math.list.NumL;
import tools.scanner.list.ScanL;
import tools.strings.S;

public class Iso_1 {

	public static void main(String[] args) {
		L l = ScanL.read();
		int id = -1;
		long maxP = NumL.lcm(l); 
		for (int i = 0; i < l.size(); i++) {
			L n = new L(l);
			n.set(i, n.get(i) + 1);
			long p = NumL.lcm(n);
			if (p > maxP) {
				id = i;
				maxP = p;
			}
		}
		S.o(id);
	}
}
