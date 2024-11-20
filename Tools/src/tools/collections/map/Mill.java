package tools.collections.map;

import java.util.TreeMap;

import tools.collections.int64.Ll;

@SuppressWarnings("serial")
public class Mill extends TreeMap<Integer, Ll> {
	public void add(int k, long v) {
		Ll l = get(k);
		if (l == null) {
			l = new Ll();
			put(k, l);
		}
		l.add(v);
	}

	public boolean remove(int k, long v) {
		Ll l = get(k);
		if (l != null) return l.remove((Long) v);
		return false;
	}
	
	public Ll getOrSetEmpty(int k) {
		Ll l = get(k);
		if (l == null) {
			l = new Ll();
			put(k, l);
		}
		return l;
	}
}
