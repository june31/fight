package currentCG;

import tools.collections.bool.Lb;
import tools.strings.S;

public class CGS_Current {

	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 256; i++) S.o(i >> 4, i & 15, recurse(new Lb(i >> 4, 4), new Lb(i & 15, 4))); 
	}
	
	private static int recurse(Lb start, Lb end) {
		if (start.isEmpty()) return 0;
		if (start.first() == end.first()) return recurse(start.subbedStart(1), end.subbedStart(1));
		int s = start.size();
		if (s == 1) return 1;
		Lb mid = new Lb(1 << (s - 2), s - 1);
		return 1 + recurse(start.subbedStart(1), mid) + recurse(mid, end.subbedStart(1));
	}
}
