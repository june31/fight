package tools.collections.multi.convert;

import tools.collections.map.Msi;
import tools.collections.multi.Lis;
import tools.tuple.IS;

public class LisToMsi {
	public static Msi convert(Lis lis) {
		Msi msi = new Msi();
		for (IS is : lis) msi.put(is.s, is.i);
		return msi;
	}
}
