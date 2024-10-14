package tools.collections.multi.convert;

import tools.collections.multi.Lis;
import tools.collections.multi.Lsi;
import tools.tuple.IS;
import tools.tuple.SI;

public class LisToLsi {

	public static Lsi convert(Lis lis) {
		Lsi lsi = new Lsi();
		for (IS is : lis) lsi.add(new SI(is.s, is.i));
		return lsi;
	}
}
