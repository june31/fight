package tools.collections.map;

import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("serial")
public class Mos<T> extends TreeMap<T, String> {
	
	public Mos() {}
	public Mos(Map<T, String> m) { super(m); }
	
	@SuppressWarnings("unchecked")
	public static <T> Mos<T> of(Object... vals) {
		Mos<T> m = new Mos<T>();
		for (int i = 0; i < vals.length; i += 2) {
			m.put((T) vals[i], (String) vals[i + 1]);
		}
		return m;
	}
}
