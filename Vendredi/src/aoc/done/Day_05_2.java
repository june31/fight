package aoc.done;

import java.util.ArrayList;
import java.util.List;

import tools.collections.int64.Ll;
import tools.scanner.Scan;
import tools.tuple.LL;

public class Day_05_2 {

	public static void main(String[] args) {
		Ll seeds = new Ll(Scan.readLine());
		var ranges = new ArrayList<LL>();
		for (int i = 0; i < seeds.size() / 2; i++) ranges.add(new LL(seeds.get(2 * i), seeds.get(2 * i) + seeds.get(2 * i + 1) - 1));
		Scan.readLine();
		for (int z = 0; z < 7; z++) {
			Scan.readLine();
			var shiftedLL = new ArrayList<LL>();
			String[] lines = Scan.readRawLines();
			for (String line: lines) {
				Ll l = new Ll(line);
				LL source = new LL(l.get(1), l.get(1) + l.get(2) - 1);
				long shift = l.get(0) - l.get(1);
				boolean found;
				do {
					found = false;
					for (int i = 0; i < ranges.size(); i++) {
						LL r = ranges.get(i);
						LL inter = intersect(r, source);
						if (inter != null) {
							found = true;
							ranges.remove(i);
							var subs = sub(r, inter);
							ranges.addAll(subs);
							LL shifted = new LL(inter.a + shift, inter.b + shift);
							shiftedLL.add(shifted);
							break;
						}
					}
				} while (found);
			}
			ranges.addAll(shiftedLL);
		}
		long min = Long.MAX_VALUE;
		for (LL ll: ranges) if (ll.a < min) min = ll.a;
		System.out.println(min);
	}

	private static LL intersect(LL l1, LL l2) {
		LL l = new LL(Math.max(l1.a, l2.a), Math.min(l1.b, l2.b));
		if (l.a > l.b) return null;
		return l;
	}

	private static List<LL> sub(LL l, LL s) {
		var list = new ArrayList<LL>();
		if (l.a < s.a && l.b > s.b) {
			list.add(new LL(l.a, s.a - 1));
			list.add(new LL(s.b + 1, l.b));
		}
		else if (!l.equals(s)) 
			list.add(new LL(l.a == s.a ? s.b + 1 : l.a, l.b == s.b ? s.a - 1 : l.b));
		return list;
	}
}
