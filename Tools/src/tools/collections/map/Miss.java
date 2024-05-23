package tools.collections.map;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

@SuppressWarnings("serial")
public class Miss extends TreeMap<Integer, TreeSet<String>> {
	public void put(int k, String v) {
		TreeSet<String> s = get(k);
		if (s == null) {
			s = new TreeSet<String>();
			put(k, s);
		}
		s.add(v);
	}

	public boolean remove(int k, String v) {
		Set<String> s = get(k);
		if (s != null) return s.remove(v);
		return false;
	}
}
