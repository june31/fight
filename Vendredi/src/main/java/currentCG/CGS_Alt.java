package currentCG;

import tools.collections.int32.L;
import tools.dichotomy.Search;
import tools.scanner.Scan;
import tools.scanner.list.ScanL;
import tools.strings.S;

public class CGS_Alt {
	public static void main(String[] args) {
		L l = ScanL.readChars().mapped(c -> c - 'a');
		int n = Scan.readInt();
		S.o(Search.maxTrue(z -> {
			L last = new L().filled(26, -1);
			for (int i = 0; i < l.size(); i++) {
				last.set(l.get(i), i);
				int f = i;
				if (i >= z - 1 && last.filtered(x -> x > f - z).size() <= n) return true;
			}
			return false;
		}));
	}
}
