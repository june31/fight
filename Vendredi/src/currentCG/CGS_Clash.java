package currentCG;

import java.util.HashSet;
import java.util.Set;

import tools.chrono.Chrono;
import tools.collections.int32.L;
import tools.collections.int32.Si;
import tools.scanner.Scan;
import tools.scanner.list.ScanL;
import tools.strings.S;
import tools.tuple.II;

public class CGS_Clash {
	public static void main(String[] args) {
		Chrono.start();
		Scan.readInt();
		L l = new L();
		for (int z = 0; z < Scan.readOnce(); z++) l.addAll(ScanL.readChars());
		int n = Scan.readInt();
		for (int i = 0; i < n; i++) {
			used.clear();
			Si s = new Si();
			String[] ts = Scan.readString().split(",");
			for (String t: ts) {
				if (t.indexOf('-') == -1) s.add(t.charAt(0));
				else for (int j = t.charAt(0); j <= t.charAt(2); j++) s.add(j);
			}
			L cs = new L(s);
			II res = find(l, cs, 0, l.size());
			S.o(res.a(), res.b() - 1);
		}
		Chrono.stop();
	}

	private static Set<II> used = new HashSet<II>();
	private static II find(L l, L cs, int p, int q) {
		while (p < q && !cs.contains(l.get(p))) p++;
		while (p < q && !cs.contains(l.get(q-1))) q--;
		II ii = new II(p, q);
		if (used.contains(ii)) return null;
		used.add(ii);
		
		boolean[] tb = new boolean[cs.size()];
		for (int i=p; i<q; i++) for (int j=0; j<cs.size(); j++) if (l.get(i) == cs.get(j)) tb[j] = true;
		for (int i=0; i<cs.size(); i++) if (!tb[i]) return null;
		II a1 = find(l, cs, p, q-1);
		II a2 = find(l, cs, p+1, q);
		if (a1 != null && a2 != null) {
			if (a2.b() - a2.a() < a1.b() - a1.a()) return a2;
			return a1;
		}
		if (a1 != null) return a1;
		if (a2 != null) return a2;
		return new II(p, q);
	}
}