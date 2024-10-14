package tools.collections.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import tools.tuple.Pos;

@SuppressWarnings("serial")
public class Mpi extends TreeMap<Pos, Integer> {
	
	public Mpi() {}
	public Mpi(Map<Pos, Integer> m) { super(m); }
	public Mpi(Collection<Pos> c) {
		for (Pos p: c) {
			Integer n = get(p);
			put(p, n == null ? 1 : n + 1);
		}
	}
	
	public List<Map.Entry<Pos, Integer>> max() {
		List<Map.Entry<Pos, Integer>> l = new ArrayList<>();
		int max = Integer.MIN_VALUE;
		for (int i: values()) if (i > max) max = i;
		for (Map.Entry<Pos, Integer> e: entrySet()) if (e.getValue() == max) l.add(e);
		return l;
	}
	
	public List<Map.Entry<Pos, Integer>> min() {
		List<Map.Entry<Pos, Integer>> l = new ArrayList<>();
		int min = Integer.MAX_VALUE;
		for (int i: values()) if (i < min) min = i;
		for (Map.Entry<Pos, Integer> e: entrySet()) if (e.getValue() == min) l.add(e);
		return l;
	}
	
	public int getOrZero(Pos p) {
		Integer n = get(p);
		return n == null ? 0 : n;
	}
}
