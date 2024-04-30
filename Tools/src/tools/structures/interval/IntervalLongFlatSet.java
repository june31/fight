package tools.structures.interval;

import java.util.Collection;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.TreeSet;

import tools.collections.segment.LInterval;
import tools.tuple.Interval;


@SuppressWarnings("serial")
public class IntervalLongFlatSet extends TreeSet<Interval> {
	
	public boolean strict;
	
	/**
	 * strict = true:  [1, 3] + [4, 7] = [1, 3], [4, 7]
	 * strict = false: [1, 3] + [4, 7] = [1, 7]
	 * 
	 * @param strict strict mode?
	 */
	public IntervalLongFlatSet(boolean strict) {
		this.strict = strict;
	}

	public IntervalLongFlatSet() {
		this(false);
	}

	public IntervalLongFlatSet(Collection<Interval> col, boolean strict) {
		this(strict);
		addAll(col);
	}

	public void add(long a, long b) { add(new Interval(a, b)); }

	public void add(long[] t) { add(new Interval(t[0], t[1])); }
	
	public boolean add(Interval r) {
		boolean result = false;
		int bonus = strict ? 0 : 1;
		Interval floor = floor(r);
		if (floor == null || floor.b + bonus < r.a) {
			floor = new Interval(r.a, r.b);
			result = add(floor);
		}
		floor.b = Math.max(floor.b, r.b);
		NavigableSet<Interval> sub = tailSet(floor, false);
		Iterator<Interval> it = sub.iterator();
		while (it.hasNext()) {
			Interval seg = it.next();
			if (r.b + bonus >= seg.a) {
				it.remove();
				floor.b = Math.max(r.b, seg.b);
				result |= floor.b != seg.b;
			} else break;
		}
		return result;
	}

	public void remove(Interval r) {
		NavigableSet<Interval> sub;
		Interval floor = floor(r);
		if (floor == null) sub = this;
		else {
			if (floor.a == r.a) remove(floor);
			else if (floor.b >= r.a) {
				if (floor.b > r.b) add(new Interval(r.b + 1, floor.b));
				floor.b = r.a - 1;
			}
			sub = tailSet(floor, false);
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
	public IntervalLongFlatSet removeAndExtract(long a, long b) {
		return removeAndExtract(new Interval(a, b));
	}

	// Same as remove, but returns IntervalSet actually removed
	public IntervalLongFlatSet removeAndExtract(Interval r) {
		IntervalLongFlatSet mr = new IntervalLongFlatSet(strict);
		NavigableSet<Interval> sub;
		Interval floor = floor(r);
		if (floor == null)
			sub = this;
		else {
			if (floor.a == r.a) {
				remove(floor);
				mr.add(floor);
			} else if (floor.b >= r.a) {
				if (floor.b > r.b) {
					add(new Interval(r.b + 1, floor.b));
					mr.add(new Interval(r.b + 1, floor.b));
				}
				floor.b = r.a - 1;
			}
			sub = tailSet(floor, false);
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
	
	
	public void remove(IntervalLongFlatSet mr) {
		for (Interval r: mr) remove(r);
	}

	public void intersect(Interval r) {
		remove(reverse(r));
	}

	public void intersect(IntervalLongFlatSet mr) {
		remove(mr.reversed());
	}
	
	public boolean contains(Interval r) {
		Interval floor = floor(r);
		if (floor == null) return false;
		return floor.a <= r.a && floor.b >= r.b;
	}
	
	public boolean contains(long l) {
		return contains(new Interval(l, l));
	}

	public boolean intersects(Interval r) {
		return intersects(r, strict);
	}
	
	public boolean intersects(Interval r, boolean strict) {
		int bonus = strict ? 0 : 1;
		Interval floor = floor(r);
		if (floor != null && floor.b + bonus > r.a) return true;
		Interval ceil = ceiling(r);
		if (ceil != null && ceil.a - bonus < r.b) return true;
		return false;
	}

	public LInterval toList() {
		return new LInterval(this);
	}
	
	@Override
	public String toString() {
		return toString();
	}

	public IntervalLongFlatSet reversed() {
		IntervalLongFlatSet mr = new IntervalLongFlatSet(strict);
		mr.add(Long.MIN_VALUE, Long.MAX_VALUE);
		for (Interval r: this) mr.remove(r);
		return mr;
	}

	public static IntervalLongFlatSet full() {
		IntervalLongFlatSet mr = new IntervalLongFlatSet();
		mr.add(new Interval(Long.MIN_VALUE, Long.MAX_VALUE));
		return mr;
	}

	public static IntervalLongFlatSet reverse(Interval r) {
		IntervalLongFlatSet mr = new IntervalLongFlatSet();
		mr.add(new Interval(Long.MIN_VALUE, r.a - 1));
		mr.add(new Interval(r.b + 1, Long.MAX_VALUE));
		return mr;
	}
	
	public static IntervalLongFlatSet intersection(LInterval lr) {
		if (lr.size() == 0) return new IntervalLongFlatSet();
		IntervalLongFlatSet mr = IntervalLongFlatSet.full();
		for (Interval r: lr) mr.intersect(r);
		return mr;
	}

	public boolean isEmpty() {
		return isEmpty();
	}
	
	public static void main(String[] args) {
		IntervalLongFlatSet m = new IntervalLongFlatSet();
		m.add(10, 20);
		m.add(30, 40);
		m.add(50, 60);
		System.out.println(m.removeAndExtract(15, 55));
		System.out.println(m);
		
		m = new IntervalLongFlatSet();
		m.add(10, 20);
		m.remove(9, 10);
		System.out.println(m);
	}
}
