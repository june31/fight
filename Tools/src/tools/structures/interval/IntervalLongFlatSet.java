package tools.structures.interval;

import java.util.Iterator;
import java.util.NavigableSet;
import java.util.TreeSet;

import tools.collections.segment.LRange;
import tools.tuple.Interval;


public class IntervalSet {
	public TreeSet<Interval> rs = new TreeSet<>();
	
	public boolean strict;
	
	/**
	 * strict = true:  [1, 3] + [4, 7] = [1, 3], [4, 7]
	 * strict = false: [1, 3] + [4, 7] = [1, 7]
	 * 
	 * @param strict strict mode?
	 */
	public IntervalSet(boolean strict) {
		this.strict = strict;
	}

	public IntervalSet() {
		this(false);
	}

	public void addAll(LRange l) {
		for (Interval s: l) add(s);
	}

	public void add(long a, long b) { add(new Interval(a, b)); }

	public void add(long[] t) { add(new Interval(t[0], t[1])); }
	
	public void add(Interval r) {
		int bonus = strict ? 0 : 1;
		Interval floor = rs.floor(r);
		if (floor == null || floor.b + bonus < r.a) {
			floor = new Interval(r.a, r.b);
			rs.add(floor);
		}
		floor.b = Math.max(floor.b, r.b);
		NavigableSet<Interval> sub = rs.tailSet(floor, false);
		Iterator<Interval> it = sub.iterator();
		while (it.hasNext()) {
			Interval seg = it.next();
			if (r.b + bonus >= seg.a) {
				it.remove();
				floor.b = Math.max(r.b, seg.b);
			} else break;
		}
	}

	public void remove(Interval r) {
		NavigableSet<Interval> sub;
		Interval floor = rs.floor(r);
		if (floor == null) sub = rs;
		else {
			if (floor.a == r.a) rs.remove(floor);
			else if (floor.b >= r.a) {
				if (floor.b > r.b) rs.add(new Interval(r.b + 1, floor.b));
				floor.b = r.a - 1;
			}
			sub = rs.tailSet(floor, false);
		}
		Iterator<Interval> it = sub.iterator();
		while (it.hasNext()) {
			Interval seg = it.next();
			if (seg.b <= r.b) it.remove();
			else if (seg.a <= r.b) seg.a = r.b + 1;
			else break;
		}
	}

	public void remove(long a, long b) {
		remove(new Interval(a, b));
	}
	
	// Same as remove, but returns IntervalSet actually removed
	public IntervalSet removeAndGet(long a, long b) {
		return removeAndGet(new Interval(a, b));
	}

	// Same as remove, but returns IntervalSet actually removed
	public IntervalSet removeAndGet(Interval r) {
		IntervalSet mr = new IntervalSet(strict);
		NavigableSet<Interval> sub;
		Interval floor = rs.floor(r);
		if (floor == null)
			sub = rs;
		else {
			if (floor.a == r.a) {
				rs.remove(floor);
				mr.add(floor);
			} else if (floor.b >= r.a) {
				if (floor.b > r.b) {
					rs.add(new Interval(r.b + 1, floor.b));
					mr.add(new Interval(r.b + 1, floor.b));
				}
				floor.b = r.a - 1;
			}
			sub = rs.tailSet(floor, false);
		}
		Iterator<Interval> it = sub.iterator();
		while (it.hasNext()) {
			Interval seg = it.next();
			if (seg.b <= r.b) {
				it.remove();
				mr.add(seg);
			} else if (seg.a <= r.b) {
				Interval s = new Interval(seg.a, r.b);
				mr.add(s);
				seg.a = r.b + 1;
			} else
				break;
		}
		return mr;
	}
	
	
	public void remove(IntervalSet mr) {
		for (Interval r: mr.rs) remove(r);
	}

	public void intersect(Interval r) {
		remove(reverse(r));
	}

	public void intersect(IntervalSet mr) {
		remove(mr.reversed());
	}
	
	public boolean contains(Interval r) {
		Interval floor = rs.floor(r);
		if (floor == null) return false;
		return floor.a <= r.a && floor.b >= r.b;
	}

	public boolean intersects(Interval r) {
		return intersects(r, strict);
	}
	
	public boolean intersects(Interval r, boolean strict) {
		int bonus = strict ? 0 : 1;
		Interval floor = rs.floor(r);
		if (floor != null && floor.b + bonus > r.a) return true;
		Interval ceil = rs.ceiling(r);
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

	public IntervalSet reversed() {
		IntervalSet mr = new IntervalSet(strict);
		mr.add(Long.MIN_VALUE, Long.MAX_VALUE);
		for (Interval r: rs) mr.remove(r);
		return mr;
	}

	public static IntervalSet full() {
		IntervalSet mr = new IntervalSet();
		mr.rs.add(new Interval(Long.MIN_VALUE, Long.MAX_VALUE));
		return mr;
	}

	public static IntervalSet reverse(Interval r) {
		IntervalSet mr = new IntervalSet();
		mr.rs.add(new Interval(Long.MIN_VALUE, r.a - 1));
		mr.rs.add(new Interval(r.b + 1, Long.MAX_VALUE));
		return mr;
	}
	
	public static IntervalSet intersection(LRange lr) {
		if (lr.size() == 0) return new IntervalSet();
		IntervalSet mr = IntervalSet.full();
		for (Interval r: lr) mr.intersect(r);
		return mr;
	}

	public boolean isEmpty() {
		return rs.isEmpty();
	}
	
	public static void main(String[] args) {
		IntervalSet m = new IntervalSet();
		m.add(10, 20);
		m.add(30, 40);
		m.add(50, 60);
		System.out.println(m.removeAndGet(15, 55));
		System.out.println(m);
	}
}
