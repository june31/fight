package tools.math.list;

import tools.collections.int64.Ll;
import tools.math.Num;

public class NumLl {
	
	public static long gcd(Ll l) {
		return Num.gcd(l.array());
	}

	public static long lcm(Ll l) {
		return Num.lcm(l.array());
	}
}
