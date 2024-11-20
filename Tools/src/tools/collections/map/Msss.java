package tools.collections.map;

import java.util.TreeMap;

import tools.collections.string.Ss;

@SuppressWarnings("serial")
public class Msss extends TreeMap<String, Ss> {
	public void add(String k, String v) {
		Ss s = get(k);
		if (s == null) {
			s = new Ss();
			put(k, s);
		}
		s.add(v);
	}

	public boolean remove(String k, String v) {
		Ss s = get(k);
		if (s != null) return s.remove(v);
		return false;
	}
	
	public Ss getOrEmpty(String k) {
		Ss s = get(k);
		if (s == null) s = new Ss();
		return s;
	}
}
