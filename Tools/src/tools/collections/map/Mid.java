package tools.collections.map;

import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("serial")
public class Mid extends TreeMap<Integer, Double> {
	public Mid() {}
	public Mid(Map<Integer, Double> m) { super(m); }
	
	public static Mid of(Object... vals) {
		Mid m = new Mid();
		for (int i = 0; i < vals.length; i += 2) {
			m.put((Integer) vals[i], (Double) vals[i + 1]);
		}
		return m;
	}
}