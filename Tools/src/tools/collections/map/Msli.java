package tools.collections.map;

import java.util.TreeMap;

import tools.collections.int32.L;

@SuppressWarnings("serial")
public class Msli extends TreeMap<String, L> {
	public void add(String k, int v) {
		L l = get(k);
		if (l == null) {
			l = new L();
			put(k, l);
		}
		l.add(v);
	}

	public boolean remove(String k, int v) {
		L l = get(k);
		if (l != null) return l.remove((Integer) v);
		return false;
	}
	
	public L getOrEmpty(String k) {
		L l = get(k);
		if (l == null) l = new L();
		return l;
	}
}
