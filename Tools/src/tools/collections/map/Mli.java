package tools.collections.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("serial")
public class Mli extends TreeMap<Long, Integer> {
	
	public Mli() {}
	public Mli(Map<Long, Integer> m) { super(m); }
	public Mli(Collection<Long> c) {
		for (Long l: c) {
			Integer n = get(l);
			put(l, n == null ? 1 : n + 1);
		}
	}
	
	public static Mli of(Object... vals) {
		Mli m = new Mli();
		for (int i = 0; i < vals.length; i += 2) {
			m.put((Long) vals[i], (Integer) vals[i + 1]);
		}
		return m;
	}
	
	public List<Map.Entry<Long, Integer>> max() {
		List<Map.Entry<Long, Integer>> l = new ArrayList<>();
		int max = Integer.MIN_VALUE;
		for (int i: values()) if (i > max) max = i;
		for (Map.Entry<Long, Integer> e: entrySet()) if (e.getValue() == max) l.add(e);
		return l;
	}
	
	public List<Map.Entry<Long, Integer>> min() {
		List<Map.Entry<Long, Integer>> l = new ArrayList<>();
		int min = Integer.MAX_VALUE;
		for (int i: values()) if (i < min) min = i;
		for (Map.Entry<Long, Integer> e: entrySet()) if (e.getValue() == min) l.add(e);
		return l;
	}
}
