package tools.collections.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("serial")
public class Mii extends TreeMap<Integer, Integer> {
	
	public Mii() {}
	public Mii(Map<Integer, Integer> m) { super(m); }
	public Mii(Collection<Integer> c) {
		for (Integer i: c) {
			Integer n = get(i);
			put(i, n == null ? 1 : n + 1);
		}
	}
	
	public static Mii of(int... is) {
		Mii m = new Mii();
		for (int i = 0; i < is.length; i += 2) {
			m.put(is[i], is[i + 1]);
		}
		return m;
	}
	
	public List<Map.Entry<Integer, Integer>> max() {
		List<Map.Entry<Integer, Integer>> l = new ArrayList<>();
		int max = Integer.MIN_VALUE;
		for (int i: values()) if (i > max) max = i;
		for (Map.Entry<Integer, Integer> e: entrySet()) if (e.getValue() == max) l.add(e);
		return l;
	}
	
	public List<Map.Entry<Integer, Integer>> min() {
		List<Map.Entry<Integer, Integer>> l = new ArrayList<>();
		int min = Integer.MAX_VALUE;
		for (int i: values()) if (i < min) min = i;
		for (Map.Entry<Integer, Integer> e: entrySet()) if (e.getValue() == min) l.add(e);
		return l;
	}
	
	public int getOrZero(int i) {
		Integer n = get(i);
		return n == null ? 0 : n;
	}
}