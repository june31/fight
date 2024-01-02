package tools.mapper;

import tools.collections.bool.Lb;
import tools.collections.int32.L;

public class MapLb {
	public static Lb fromL(L l) {
		Lb lb = new Lb();
		for (int i: l) lb.add(i != 0);
		return lb;
	}
	public static L toL(Lb lb) {
		L l = new L();
		for (boolean b: lb) l.add(b ? 1 : 0);
		return l;
	}
}
