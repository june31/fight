package currentCG;

import tools.collections.int32.L;
import tools.mapper.MapLs;
import tools.scanner.Scan;
import tools.scanner.list.ScanLs;
import tools.strings.S;

public class CGS_Alt {
	
	private static final int WRONG = -1_000_000; 
	
	public static void main(String[] args) throws Exception {
		for (int z = 0; z < Scan.readOnce(); z++) {
			try {
				L l = MapLs.toL(ScanLs.readLine(), Integer::parseInt);
				int r = check(l);
				S.o(r < 0 ? -1 : r); 
			} catch (Exception ex) { S.o(-1); }
		}
	}

	private static int check(L l) {
		if (l.size() % 2 == 1) return WRONG;
		if (l.first() >= 0) return WRONG;
		if (l.first() + l.last() != 0) return WRONG;
		if (l.size() == 2) return 1;
		int r = 1;
		int i = 2;
		int s = 0;
		int z = 0;
		do {
			while (l.get(i) != -l.get(r)) i++;
			z += l.get(i);
			s += check(l.subbed(r, ++i));
			r = i;
		} while (++i < l.size());
		if (z >= l.last()) return WRONG;
		return s;
	}
}
