package tools.mapper;

import tools.collections.int32.L;
import tools.collections.int64.Ll;
import tools.collections.string.Ls;

public class MapLs {
	public static Ls fromLl(Ll ll) {
		Ls l = new Ls();
		for (long x: ll) l.add("" + x);
		return l;
	}
	public static Ll toLl(Ls l) {
		Ll ll = new Ll();
		for (String s: l) ll.add(Long.parseLong(s));
		return ll;
	}
	public static Ls fromL(L l) {
		Ls ls = new Ls();
		for (int x: l) ls.add("" + x);
		return ls;
	}
	public static L toL(Ls ls) {
		L l = new L();
		for (String s: ls) l.add(Integer.parseInt(s));
		return l;
	}
}
