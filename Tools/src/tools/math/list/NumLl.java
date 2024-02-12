package tools.math.list;

import java.util.Map;

import tools.collections.int64.Ll;
import tools.math.Num;

public class NumLl {
	
	public static long gcd(Ll l) {
		return Num.gcd(l.array());
	}

	public static long lcm(Ll l) {
		return Num.lcm(l.array());
	}
	
    public static Ll max(Map<Long, Integer> m) {
    	int max = Num.max(m.values(), v -> v).o;
    	Ll l = new Ll();
    	for (Map.Entry<Long, Integer> e: m.entrySet()) if (e.getValue() == max) l.add(e.getKey());
    	return l;
    }
}
