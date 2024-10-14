package tools.collections.multi.convert;

import tools.collections.multi.Lis;
import tools.collections.multi.Lsi;
import tools.tuple.IS;
import tools.tuple.SI;

public class LsiToLis {
	public static Lis convert(Lsi lsi) {
		Lis lis = new Lis();
		for (SI si : lsi) lis.add(new IS(si.i, si.s));
		return lis;
	}
}
