package tools.math.list;

import tools.collections.int32.L;
import tools.math.Num;

public class NumL {
	
	public static int gcd(L l) {
		return Num.gcd(l.array());
	}

	public static long lcm(L l) {
		return Num.lcm(l.array());
	}
}
