package tools.collections.map;

import java.util.TreeMap;

import tools.collections.string.Ls;

@SuppressWarnings("serial")
public class Msls extends TreeMap<String, Ls> {
	public void add(String k, String v) {
		Ls l = get(k);
		if (l == null) {
			l = new Ls();
			put(k, l);
		}
		l.add(v);
	}

	public boolean remove(String k, String v) {
		Ls l = get(k);
		if (l != null) return l.remove(v);
		return false;
	}
	
	public Ls getOrEmpty(String k) {
		Ls l = get(k);
		if (l == null) l = new Ls();
		return l;
	}
}
