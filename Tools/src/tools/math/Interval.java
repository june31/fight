package tools.math;

import java.util.ArrayList;
import java.util.List;

import tools.tuple.LPair;
import tools.tuple.Pair;

public class Interval {
	public static Pair intersection(Pair p1, Pair p2) {
		if (p1.a > p2.b || p1.b < p2.a) return null;
		return new Pair(Math.max(p1.a, p2.a), Math.min(p1.b, p2.b));
	}

	public static LPair intersection(LPair p1, LPair p2) {
		if (p1.a > p2.b || p1.b < p2.a) return null;
		return new LPair(Math.max(p1.a, p2.a), Math.min(p1.b, p2.b));
	}

	public static void flatten(List<Pair> list) {
		list.sort((p1, p2) -> p1.a - p2.a);
		var newList = new ArrayList<Pair>();
		int n = list.size();
		for (int i = 0; i < n; i++) {
			Pair m = list.get(i);
			int max = m.b;
			for (int j = i + 1; j < n; j++) {
				Pair o = list.get(j);
				if (o.a <= max) {
					i++;
					if (max < o.b) max = o.b;
				} else break;
			}
			m.b = max;
			newList.add(m);
		}
		if (list.size() != newList.size()) {
			list.clear();
			list.addAll(newList);
		}
	}
	
	public static void flattenLong(List<LPair> list) {
		list.sort((p1, p2) -> p1.a == p2.a ? 0 : (p1.a < p2.a ? -1 : 1));
		var newList = new ArrayList<LPair>();
		int n = list.size();
		for (int i = 0; i < n; i++) {
			LPair m = list.get(i);
			long max = m.b;
			for (int j = i + 1; j < n; j++) {
				LPair o = list.get(j);
				if (o.a <= max) {
					i++;
					if (max < o.b) max = o.b;
				} else break;
			}
			m.b = max;
			newList.add(m);
		}
		if (list.size() != newList.size()) {
			list.clear();
			list.addAll(newList);
		}
	}
}
