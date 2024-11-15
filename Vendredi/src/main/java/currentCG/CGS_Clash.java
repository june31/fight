package currentCG;

import tools.collections.bool.Lb;
import tools.scanner.list.ScanLb;
import tools.strings.S;

public class CGS_Clash {
	
    public static void main(String[] args) throws Exception {
    	S.o(recurse(ScanLb.readLine(), ScanLb.readLine()));
    }

	private static int recurse(Lb start, Lb end) {
		if (start.isEmpty()) return 0;
		if (start.first() == end.first()) return recurse(start.subbedFrom(1), end.subbedFrom(1));
		int s = start.size();
		if (s == 1) return 1;
		Lb mid = new Lb(1 << (s - 2), s - 1);
		return 1 + recurse(start.subbedFrom(1), mid) + recurse(mid, end.subbedFrom(1));
	}
}