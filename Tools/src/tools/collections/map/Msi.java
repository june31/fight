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
	
	public static Msi of(String... ss) {
		Msi m = new Msi();
		for (int i = 0; i < ss.length; i += 2) {
			m.put(ss[i], Integer.parseInt(ss[i + 1]));
		}
		return m;
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

	public void inc(String s, int v) {
		Integer n = get(s);
		put(s, n == null ? v : n + v);
	}

	public void dec(String s) {
		Integer n = get(s);
		put(s, n == null ? -1 : n - 1);
	}
	
	public void dec(String s, int v) {
		Integer n = get(s);
		put(s, n == null ? -v : n - v);
	}
	public int getOrZero(String s) {
		Integer n = get(s);
		return n == null ? 0 : n;
	}
	public Lsi sortedUp() {
		Lsi l = new Lsi();
		for (Map.Entry<String, Integer> e: entrySet()) l.addSI(e.getKey(), e.getValue());
		l.sort((s1, s2) -> {
			int cmp = Integer.compare(s1.i, s2.i);
			if (cmp != 0) return cmp;
			return s1.s.compareTo(s2.s);
		});
		return l;
	}
	public Lsi sortedDown() {
		Lsi l = new Lsi();
		for (Map.Entry<String, Integer> e: entrySet()) l.addSI(e.getKey(), e.getValue());
		l.sort((s1, s2) -> {
			int cmp = Integer.compare(s2.i, s1.i);
			if (cmp != 0) return cmp;
			return s1.s.compareTo(s2.s);
		});
		return l;
	}
	public Lsi toLsi() {
		Lsi l = new Lsi();
		for (Map.Entry<String, Integer> e: entrySet()) l.addSI(e.getKey(), e.getValue());
		return l;
	}
}
