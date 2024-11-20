package tools.collections.map;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

@SuppressWarnings("serial")
public class Misd extends TreeMap<Integer, TreeSet<Double>> {
	public void add(int k, double v) {
		TreeSet<Double> s = get(k);
		if (s == null) {
			s = new TreeSet<Double>();
			put(k, s);
		}
		s.add(v);
	}

	public boolean remove(int k, double v) {
		Set<Double> s = get(k);
		if (s != null) return s.remove((Double) v);
		return false;
	}
}
