package tools.collections.map;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import tools.collections.multi.Loi;
import tools.tuple.OI;

@SuppressWarnings("serial")
public class Moi<T> extends TreeMap<T, Integer> {
	
	public Moi() {}
	public Moi(Map<T, Integer> m) { super(m); }
	public Moi(Collection<T> c) {
		for (T o: c) {
			Integer n = get(o);
			put(o, n == null ? 1 : n + 1);
		}
	}
	
	public Loi<T> maxes() {
		Loi<T> l = new Loi<>();
		int max = Integer.MIN_VALUE;
		for (int i: values()) if (i > max) max = i;
		for (Map.Entry<T, Integer> e: entrySet()) if (e.getValue() == max) l.addOI(e.getKey(), e.getValue());
		return l;
	}
	
	public Loi<T> mins() {
		Loi<T> l = new Loi<>();
		int min = Integer.MAX_VALUE;
		for (int i: values()) if (i < min) min = i;
		for (Map.Entry<T, Integer> e: entrySet()) if (e.getValue() == min) l.addOI(e.getKey(), e.getValue());
		return l;
	}

	public OI<T> max() {
		OI<T> max = new OI<>(null, Integer.MIN_VALUE);
		for (Map.Entry<T, Integer> e: entrySet()) if (e.getValue() > max.i) max = new OI<>(e.getKey(), e.getValue());
		return max;
	}
	
	public OI<T> min() {
		OI<T> min = new OI<>(null, Integer.MAX_VALUE);
		for (Map.Entry<T, Integer> e: entrySet()) if (e.getValue() < min.i) min = new OI<>(e.getKey(), e.getValue());
		return min;
	}

	public void inc(T s) {
		Integer n = get(s);
		put(s, n == null ? 1 : n + 1);
	}
	
	public void dec(T s) {
		Integer n = get(s);
		if (n == 1) remove(s);
		else put(s, n - 1);
	}
	
	public int getOrZero(T s) {
		Integer n = get(s);
		return n == null ? 0 : n;
	}
	
}
