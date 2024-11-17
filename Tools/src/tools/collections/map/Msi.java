package tools.collections.map;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import tools.collections.multi.Lsi;
import tools.tuple.SI;

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
	
	public Lsi maxes() {
		Lsi l = new Lsi();
		int max = Integer.MIN_VALUE;
		for (int i: values()) if (i > max) max = i;
		for (Map.Entry<String, Integer> e: entrySet()) if (e.getValue() == max) l.addSI(e.getKey(), e.getValue());
		return l;
	}
	
	public Lsi mins() {
		Lsi l = new Lsi();
		int min = Integer.MAX_VALUE;
		for (int i: values()) if (i < min) min = i;
		for (Map.Entry<String, Integer> e: entrySet()) if (e.getValue() == min) l.addSI(e.getKey(), e.getValue());
		return l;
	}

	public SI max() {
		SI max = new SI(null, Integer.MIN_VALUE);
		for (Map.Entry<String, Integer> e: entrySet()) if (e.getValue() > max.i) max = new SI(e.getKey(), e.getValue());
		return max;
	}
	
	public SI min() {
		SI min = new SI(null, Integer.MAX_VALUE);
		for (Map.Entry<String, Integer> e: entrySet()) if (e.getValue() < min.i) min = new SI(e.getKey(), e.getValue());
		return min;
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
	
	public int getOrZero(String s) {
		Integer n = get(s);
		return n == null ? 0 : n;
	}
}
