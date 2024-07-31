package tools.collections.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("serial")
public class Msi extends TreeMap<String, Integer> {
	
	public Msi() {}
	public Msi(Map<String, Integer> m) { super(m); }
	public Msi(Collection<String> c) {
		for (String s: c) {
			Integer n = get(s);
			put(s, n == null ? 1 : n + 1);
		}
	}
	
	public List<Map.Entry<String, Integer>> max() {
		List<Map.Entry<String, Integer>> l = new ArrayList<>();
		int max = Integer.MIN_VALUE;
		for (int i: values()) if (i > max) max = i;
		for (Map.Entry<String, Integer> e: entrySet()) if (e.getValue() == max) l.add(e);
		return l;
	}
	
	public List<Map.Entry<String, Integer>> min() {
		List<Map.Entry<String, Integer>> l = new ArrayList<>();
		int min = Integer.MAX_VALUE;
		for (int i: values()) if (i < min) min = i;
		for (Map.Entry<String, Integer> e: entrySet()) if (e.getValue() == min) l.add(e);
		return l;
	}
	
	public void inc(String s) {
		Integer n = get(s);
		put(s, n == null ? 1 : n + 1);
	}
	
	public void dec(String s) {
		Integer n = get(s);
		if (n == 1) remove(s);
		else put(s, n - 1);
	}
}
