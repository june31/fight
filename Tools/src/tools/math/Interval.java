package tools.math;

import java.util.ArrayList;
import java.util.List;

import tools.tuple.LL;
import tools.tuple.II;

public class Interval {
	public static II intersection(II p1, II p2) {
		if (p1.a > p2.b || p1.b < p2.a) return null;
		return new II(Math.max(p1.a, p2.a), Math.min(p1.b, p2.b));
	}

	public static LL intersection(LL p1, LL p2) {
		if (p1.a > p2.b || p1.b < p2.a) return null;
		return new LL(Math.max(p1.a, p2.a), Math.min(p1.b, p2.b));
	}

	public static void flatten(List<II> list) {
		list.sort((p1, p2) -> p1.a - p2.a);
		var newList = new ArrayList<II>();
		int n = list.size();
		for (int i = 0; i < n; i++) {
			II m = list.get(i);
			int max = m.b;
			for (int j = i + 1; j < n; j++) {
				II o = list.get(j);
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
	
	public static void flattenLong(List<LL> list) {
		list.sort((p1, p2) -> p1.a == p2.a ? 0 : (p1.a < p2.a ? -1 : 1));
		var newList = new ArrayList<LL>();
		int n = list.size();
		for (int i = 0; i < n; i++) {
			LL m = list.get(i);
			long max = m.b;
			for (int j = i + 1; j < n; j++) {
				LL o = list.get(j);
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
