package tools.collections.map;

import java.util.TreeMap;

import tools.collections.int32.Si;

@SuppressWarnings("serial")
public class Mssi extends TreeMap<String, Si> {
	public void add(String k, int v) {
		Si s = get(k);
		if (s == null) {
			s = new Si();
			put(k, s);
		}
		s.add(v);
	}

	public boolean remove(String k, int v) {
		Si s = get(k);
		if (s != null) return s.remove((Integer) v);
		return false;
	}
	
	public Si getOrEmpty(String k) {
		Si s = get(k);
		if (s == null) s = new Si();
		return s;
	}
}
