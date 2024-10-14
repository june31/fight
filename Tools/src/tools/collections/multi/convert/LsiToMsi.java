package tools.collections.multi.convert;

import tools.collections.map.Msi;
import tools.collections.multi.Lsi;
import tools.tuple.SI;

public class LsiToMsi {

	public static Msi convert(Lsi lsi) {
		Msi msi = new Msi();
		for (SI si: lsi) msi.put(si.s, si.i);
		return msi;
	}
}
