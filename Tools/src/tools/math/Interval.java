package tools.math;

import java.util.ArrayList;
import java.util.List;

import tools.tuple.II;
import tools.tuple.LL;

public class Interval {
	public static II intersection(II p1, II p2) {
		if (p1.index > p2.value || p1.value < p2.index) return null;
		return new II(Math.max(p1.index, p2.index), Math.min(p1.value, p2.value));
	}

	public static LL intersection(LL p1, LL p2) {
		if (p1.index > p2.value || p1.value < p2.index) return null;
		return new LL(Math.max(p1.index, p2.index), Math.min(p1.value, p2.value));
	}

	public static void flatten(List<II> list) {
		list.sort((p1, p2) -> p1.index - p2.index);
		List<II> newList = new ArrayList<>();
		int n = list.size();
		for (int i = 0; i < n; i++) {
			II m = list.get(i);
			int max = m.value;
			for (int j = i + 1; j < n; j++) {
				II o = list.get(j);
				if (o.index <= max) {
					i++;
					if (max < o.value) max = o.value;
				} else break;
			}
			m.value = max;
			newList.add(m);
		}
		if (list.size() != newList.size()) {
			list.clear();
			list.addAll(newList);
		}
	}
	
	public static void flattenLong(List<LL> list) {
		list.sort((p1, p2) -> p1.index == p2.index ? 0 : (p1.index < p2.index ? -1 : 1));
		List<LL> newList = new ArrayList<>();
		int n = list.size();
		for (int i = 0; i < n; i++) {
			LL m = list.get(i);
			long max = m.value;
			for (int j = i + 1; j < n; j++) {
				LL o = list.get(j);
				if (o.index <= max) {
					i++;
					if (max < o.value) max = o.value;
				} else break;
			}
			m.value = max;
			newList.add(m);
		}
		if (list.size() != newList.size()) {
			list.clear();
			list.addAll(newList);
		}
	}
}
