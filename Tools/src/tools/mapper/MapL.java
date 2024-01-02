package tools.mapper;

import tools.collections.int32.L;
import tools.collections.int64.Ll;
import tools.collections.string.Ls;

public class MapL {
	public static L fromLl(Ll ll) {
		L l = new L();
		for (long x: ll) l.add((int) x);
		return l;
	}
	public static Ll toLl(L l) {
		Ll ll = new Ll();
		for (int i: l) ll.add(i);
		return ll;
	}
	public static L fromLs(Ls ls) {
		L l = new L();
		for (String s: ls) l.add(Integer.parseInt(s));
		return l;
	}
	public static Ls toLs(L l) {
		Ls ls = new Ls();
		for (int x: l) ls.add("" + x);
		return ls;
	}
}
