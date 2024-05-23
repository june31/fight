package tools.collections.map;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

@SuppressWarnings("serial")
public class Misl extends TreeMap<Integer, TreeSet<Long>> {
	public void put(int k, long v) {
		TreeSet<Long> s = get(k);
		if (s == null) {
			s = new TreeSet<Long>();
			put(k, s);
		}
		s.add(v);
	}

	public boolean remove(int k, long v) {
		Set<Long> s = get(k);
		if (s != null) return s.remove((Long) v);
		return false;
	}
}
