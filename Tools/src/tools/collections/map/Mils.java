package tools.collections.map;

import java.util.TreeMap;

import tools.collections.string.Ls;

@SuppressWarnings("serial")
public class Mils extends TreeMap<Integer, Ls> {
	public void put(int k, String v) {
		Ls l = get(k);
		if (l == null) {
			l = new Ls();
			put(k, l);
		}
		l.add(v);
	}

	public boolean remove(int k, String v) {
		Ls l = get(k);
		if (l != null) return l.remove(v);
		return false;
	}
	
	public Ls getOrSetEmpty(int k) {
		Ls l = get(k);
		if (l == null) {
			l = new Ls();
			put(k, l);
		}
		return l;
	}
}
