package tools.collections.map;

import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("serial")
public class Mil extends TreeMap<Integer, Long> {
	public Mil() {}
	public Mil(Map<Integer, Long> m) { super(m); }
	
	public static Mil of(Object... vals) {
		Mil m = new Mil();
		for (int i = 0; i < vals.length; i += 2) {
			m.put((Integer) vals[i], (Long) vals[i + 1]);
		}
		return m;
	}
}