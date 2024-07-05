package tools.collections.map;

import java.util.TreeMap;

import tools.collections.int32.L;

@SuppressWarnings("serial")
public class Mili extends TreeMap<Integer, L> {
	public void put(int k, int v) {
		L l = get(k);
		if (l == null) {
			l = new L();
			put(k, l);
		}
		l.add(v);
	}

	public boolean remove(int k, int v) {
		L l = get(k);
		if (l != null) return l.remove((Integer) v);
		return false;
	}
	
	public L getOrEmpty(int k) {
		L l = get(k);
		if (l == null) l = new L();
		return l;
	}
}
