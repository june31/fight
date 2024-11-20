package tools.collections.map;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import tools.tuple.Pos;

@SuppressWarnings("serial")
public class Mplp extends TreeMap<Pos, List<Pos>> {
	public void add(Pos k, Pos v) {
		List<Pos> l = get(k);
		if (l == null) {
			l = new ArrayList<Pos>();
			put(k, l);
		}
		l.add(v);
	}

	public boolean remove(Pos k, Pos v) {
		List<Pos> l = get(k);
		if (l != null) return l.remove(v);
		return false;
	}
}
