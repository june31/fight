package tools.mapper;

import tools.collections.int32.L;
import tools.collections.int64.Ll;
import tools.collections.string.Ls;

public class MapLl {
	public static Ll fromL(L l) {
		Ll ll = new Ll();
		for (int i: l) ll.add(i);
		return ll;
	}
	public static L toL(Ll ll) {
		L l = new L();
		for (long x: ll) l.add((int) x);
		return l;
	}
	public static Ll fromLs(Ls l) {
		Ll ll = new Ll();
		for (String s: l) ll.add(Long.parseLong(s));
		return ll;
	}
	public static Ls toLs(Ll ll) {
		Ls l = new Ls();
		for (long x: ll) l.add("" + x);
		return l;
	}
}
