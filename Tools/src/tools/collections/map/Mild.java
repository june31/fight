package tools.collections.map;

import java.util.TreeMap;

import tools.collections.float64.Ld;

@SuppressWarnings("serial")
public class Mild extends TreeMap<Integer, Ld> {
	public void add(int k, double v) {
		Ld l = get(k);
		if (l == null) {
			l = new Ld();
			put(k, l);
		}
		l.add(v);
	}

	public boolean remove(int k, double v) {
		Ld l = get(k);
		if (l != null) return l.remove((Double) v);
		return false;
	}
	
	public Ld getOrSetEmpty(int k) {
		Ld l = get(k);
		if (l == null) {
			l = new Ld();
			put(k, l);
		}
		return l;
	}
}
