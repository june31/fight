package tools.structures.interval;

import java.util.Iterator;
import java.util.NavigableSet;
import java.util.TreeSet;

import tools.collections.segment.LRange;
import tools.tuple.Range;


public class MultiRange {
	public TreeSet<Range> rs = new TreeSet<>();
	
	public boolean strict;
	
	/**
	 * strict = true:  [1, 3] + [4, 7] = [1, 3], [4, 7]
	 * strict = false: [1, 3] + [4, 7] = [1, 7]
	 * 
	 * @param strict strict mode?
	 */
	public MultiRange(boolean strict) {
		this.strict = strict;
	}

	public MultiRange() {
		this(false);
	}

	public void addAll(LRange l) {
		for (Range s: l) add(s);
	}

	public void add(long a, long b) { add(new Range(a, b)); }

	public void add(long[] t) { add(new Range(t[0], t[1])); }
	
	public void add(Range r) {
		int bonus = strict ? 0 : 1;
		Range floor = rs.floor(r);
		if (floor == null || floor.b + bonus < r.a) {
			floor = new Range(r.a, r.b);
			rs.add(floor);
		}
		floor.b = Math.max(floor.b, r.b);
		NavigableSet<Range> sub = rs.tailSet(floor, false);
		Iterator<Range> it = sub.iterator();
		while (it.hasNext()) {
			Range seg = it.next();
			if (r.b + bonus >= seg.a) {
				it.remove();
				floor.b = Math.max(r.b, seg.b);
			} else break;
		}
	}

	public void remove(Range r) {
		NavigableSet<Range> sub;
		Range floor = rs.floor(r);
		if (floor == null) sub = rs;
		else {
			if (floor.a == r.a) rs.remove(floor);
			else if (floor.b >= r.a) {
				if (floor.b > r.b) rs.add(new Range(r.b + 1, floor.b));
				floor.b = r.a - 1;
			}
			sub = rs.tailSet(floor, false);
		}
		Iterator<Range> it = sub.iterator();
		while (it.hasNext()) {
			Range seg = it.next();
			if (seg.b <= r.b) it.remove();
			else if (seg.a <= r.b) seg.a = r.b + 1;
			else break;
		}
	}

	public void remove(long a, long b) {
		remove(new Range(a, b));
	}
	
	// Same as remove, but returns MultiRange actually removed
	public MultiRange removeAndGet(long a, long b) {
		MultiRange mr = new MultiRange(strict);
		Range r = new Range(a, b);
		NavigableSet<Range> sub;
		Range floor = rs.floor(r);
		if (floor == null)
			sub = rs;
		else {
			if (floor.a == r.a) {
				rs.remove(floor);
				mr.add(floor);
			} else if (floor.b >= r.a) {
				if (floor.b > r.b) {
					rs.add(new Range(r.b + 1, floor.b));
					mr.add(new Range(r.b + 1, floor.b));
				}
				floor.b = r.a - 1;
			}
			sub = rs.tailSet(floor, false);
		}
		Iterator<Range> it = sub.iterator();
		while (it.hasNext()) {
			Range seg = it.next();
			if (seg.b <= r.b) {
				it.remove();
				mr.add(seg);
			} else if (seg.a <= r.b) {
				Range s = new Range(seg.a, r.b);
				mr.add(s);
				seg.a = r.b + 1;
			} else
				break;
		}
		return mr;
	}
	
	
	public void remove(MultiRange mr) {
		for (Range r: mr.rs) remove(r);
	}

	public void intersect(Range r) {
		remove(reverse(r));
	}

	public void intersect(MultiRange mr) {
		remove(mr.reversed());
	}
	
	public boolean contains(Range r) {
		Range floor = rs.floor(r);
		if (floor == null) return false;
		return floor.a <= r.a && floor.b >= r.b;
	}

	public boolean intersects(Range r) {
		return intersects(r, strict);
	}
	
	public boolean intersects(Range r, boolean strict) {
		int bonus = strict ? 0 : 1;
		Range floor = rs.floor(r);
		if (floor != null && floor.b + bonus > r.a) return true;
		Range ceil = rs.ceiling(r);
		if (ceil != null && ceil.a - bonus < r.b) return true;
		return false;
	}

	public LRange getSegments() {
		return new LRange(rs);
	}
	
	@Override
	public String toString() {
		return rs.toString();
	}

	public MultiRange reversed() {
		MultiRange mr = new MultiRange(strict);
		mr.add(Long.MIN_VALUE, Long.MAX_VALUE);
		for (Range r: rs) mr.remove(r);
		return mr;
	}

	public static MultiRange full() {
		MultiRange mr = new MultiRange();
		mr.rs.add(new Range(Long.MIN_VALUE, Long.MAX_VALUE));
		return mr;
	}

	public static MultiRange reverse(Range r) {
		MultiRange mr = new MultiRange();
		mr.rs.add(new Range(Long.MIN_VALUE, r.a - 1));
		mr.rs.add(new Range(r.b + 1, Long.MAX_VALUE));
		return mr;
	}
	
	public static MultiRange intersection(LRange lr) {
		if (lr.size() == 0) return new MultiRange();
		MultiRange mr = MultiRange.full();
		for (Range r: lr) mr.intersect(r);
		return mr;
	}

	public boolean isEmpty() {
		return rs.isEmpty();
	}
	
	public static void main(String[] args) {
		MultiRange m = new MultiRange();
		m.add(10, 20);
		m.add(30, 40);
		m.add(50, 60);
		System.out.println(m.removeAndGet(15, 55));
		System.out.println(m);
	}
}
