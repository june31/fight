package tools.collections.map;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

@SuppressWarnings("serial")
public class Misi extends TreeMap<Integer, TreeSet<Integer>> {
	public void put(int k, int v) {
		TreeSet<Integer> s = get(k);
		if (s == null) {
			s = new TreeSet<Integer>();
			put(k, s);
		}
		s.add(v);
	}

	public boolean remove(int k, int v) {
		Set<Integer> s = get(k);
		if (s != null) return s.remove((Integer) v);
		return false;
	}
	
	public TreeSet<Integer> getOrSetEmpty(int k) {
		TreeSet<Integer> l = get(k);
		if (l == null) {
			l = new TreeSet<Integer>();
			put(k, l);
		}
		return l;
	}
}
