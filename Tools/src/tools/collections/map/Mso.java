package tools.collections.map;

import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("serial")
public class Mso<T> extends TreeMap<String, T> {
	
	public Mso() {}
	public Mso(Map<String, T> m) { super(m); }
	
	@SuppressWarnings("unchecked")
	public static <T> Mso<T> of(Object... vals) {
		Mso<T> m = new Mso<T>();
		for (int i = 0; i < vals.length; i += 2) {
			m.put((String) vals[i], (T) vals[i + 1]);
		}
		return m;
	}
}
