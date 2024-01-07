package tools.mapper;

import java.util.List;

import tools.collections.bool.Lb;
import tools.collections.int32.L;
import tools.collections.int64.Ll;

public class MapLb {
	public static Lb fromL(L l, int numberOfBits) {
		Lb lb = new Lb();
		for (int i: l) lb.addAll(new Lb(i, numberOfBits));
		return lb;
	}
	public static L toL(Lb lb, int numberOfBits) {
		L l = new L();
		for (boolean b: lb) l.add(b ? 1 : 0);
		return l;
	}
	public static L toL(List<Lb> lbs) {
		L l = new L();
		for (Lb lb: lbs) {
			int i = 0;
			for (boolean b: lb) {
				i <<= 1;
				if (b) i |= 1;
			}			
			l.add(i);
		}
		return l;
	}
	public static Ll toLl(List<Lb> lbs) {
		Ll l = new Ll();
		for (Lb lb: lbs) {
			long i = 0;
			for (boolean b: lb) {
				i <<= 1;
				if (b) i |= 1;
			}			
			l.add(i);
		}
		return l;
	}
}
