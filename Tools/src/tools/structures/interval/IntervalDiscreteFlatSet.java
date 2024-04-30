package tools.structures.interval;

import java.util.Collection;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.TreeSet;

import tools.collections.segment.LInterval;
import tools.tuple.Interval;


@SuppressWarnings("serial")
public class IntervalDiscreteFlatSet extends TreeSet<Interval> {
	
	public IntervalDiscreteFlatSet() {}

	public IntervalDiscreteFlatSet(Collection<Interval> col) {
		addAll(col);
	}

	public void add(long a, long b) { add(new Interval(a, b)); }

	public boolean add(Interval r) {
		boolean result = false;
		Interval floor = floor(r);
		if (floor == null || floor.b + 1 < r.a) {
			floor = new Interval(r.a, r.b);
			result = super.add(floor);
		}
		floor.b = Math.max(floor.b, r.b);
		NavigableSet<Interval> sub = tailSet(floor, false);
		Iterator<Interval> it = sub.iterator();
		while (it.hasNext()) {
			Interval seg = it.next();
			if (r.b + 1 >= seg.a) {
				it.remove();
				floor.b = Math.max(r.b, seg.b);
				result |= floor.b != seg.b;
			} else break;
		}
		return result;
	}

	public IntervalDiscreteFlatSet addAndExtract(long a, long b) { return addAndExtract(new Interval(a, b)); }

	public IntervalDiscreteFlatSet addAndExtract(Interval r) {
		IntervalDiscreteFlatSet extracted = new IntervalDiscreteFlatSet();
		extracted.add(r);
		Interval floor = floor(r);
		if (floor == null || floor.b + 1 < r.a) {
			floor = new Interval(r.a, r.b); 
			super.add(floor);
			return extracted;
		}
		floor.b = Math.max(floor.b, r.b);
		NavigableSet<Interval> sub = tailSet(floor, false);
		Iterator<Interval> it = sub.iterator();
		while (it.hasNext()) {
			Interval seg = it.next();
			if (r.b + 1 >= seg.a) {
				it.remove();
				extracted.remove(seg);
				floor.b = Math.max(r.b, seg.b);
			} else break;
		}
		return extracted;
	}
	
	public void remove(Interval toRemove) {
		// We first process the floor, then we process the rest. 
		Interval floor = floor(toRemove);
		
		NavigableSet<Interval> rest;
		// If floor is null, the rest is the whole set.
		if (floor == null) rest = this;
		
		// If floor is not null, we need to process it first.
		else if (floor.b >= toRemove.a) {
			// Floor intersects toRemove. We will remove it at the end,
			// but for now add the part of floor that is before toRemove.
			if (floor.a < toRemove.a) floor.b = toRemove.a - 1;
			
			// Retrieve everything that is after toRemove. It contains floor
			// if it strictly (inclusive = false) overlaps with toRemove.  
			rest = tailSet(toRemove, false);
			
			// Now we can remove floor from the set. It might still be present in 'rest'. 
			super.remove(floor);
		}
		// Floor does not intersect toRemove. The rest is everything after floor.
		else rest = tailSet(floor, false);
		
		// Now we can process the rest.
		Iterator<Interval> it = rest.iterator();
		while (it.hasNext()) {
			Interval seg = it.next();
			if (seg.b <= toRemove.b) it.remove();
			else if (seg.a <= toRemove.b) seg.a = toRemove.b + 1;
			else break;
		}
	}

	public void remove(long a, long b) {
		remove(new Interval(a, b));
	}
	
	// Same as remove, but returns IntervalLongFlatSet actually removed
	public IntervalDiscreteFlatSet removeAndExtract(long a, long b) {
		return removeAndExtract(new Interval(a, b));
	}

	// Same as remove, but returns IntervalLongFlatSet actually removed
	public IntervalDiscreteFlatSet removeAndExtract(Interval r) {
		IntervalDiscreteFlatSet extracted = new IntervalDiscreteFlatSet();
		NavigableSet<Interval> sub;
		Interval floor = floor(r);
		if (floor == null) sub = this;
		else {
			if (floor.a == r.a) {
				remove(floor);
				extracted.add(floor);
			} else if (floor.b >= r.a) {
				if (floor.b > r.b) {
					super.add(new Interval(r.b + 1, floor.b));
					extracted.add(r.a, r.b);
					return extracted;
				}
				extracted.add(r.a, floor.b);
				floor.b = r.a - 1;
			}
			sub = tailSet(floor, false);
		}
		Iterator<Interval> it = sub.iterator();
		while (it.hasNext()) {
			Interval seg = it.next();
			if (seg.b <= r.b) {
				it.remove();
				extracted.add(seg);
			} else if (seg.a <= r.b) {
				Interval s = new Interval(seg.a, r.b);
				extracted.add(s);
				seg.a = r.b + 1;
			} else
				break;
		}
		return extracted;
	}
	
	
	public void remove(IntervalDiscreteFlatSet mr) {
		for (Interval r: mr) remove(r);
	}

	public void intersect(Interval r) {
		remove(reverse(r));
	}

	public void intersect(IntervalDiscreteFlatSet mr) {
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
		Interval floor = floor(r);
		if (floor != null && floor.b + 1 > r.a) return true;
		Interval ceil = ceiling(r);
		if (ceil != null && ceil.a - 1 < r.b) return true;
		return false;
	}

	public LInterval toList() {
		return new LInterval(this);
	}

	public IntervalDiscreteFlatSet reversed() {
		IntervalDiscreteFlatSet mr = new IntervalDiscreteFlatSet();
		mr.add(Long.MIN_VALUE, Long.MAX_VALUE);
		for (Interval r: this) mr.remove(r);
		return mr;
	}

	public static IntervalDiscreteFlatSet full() {
		IntervalDiscreteFlatSet mr = new IntervalDiscreteFlatSet();
		mr.add(new Interval(Long.MIN_VALUE, Long.MAX_VALUE));
		return mr;
	}

	public static IntervalDiscreteFlatSet reverse(Interval r) {
		IntervalDiscreteFlatSet mr = new IntervalDiscreteFlatSet();
		mr.add(new Interval(Long.MIN_VALUE, r.a - 1));
		mr.add(new Interval(r.b + 1, Long.MAX_VALUE));
		return mr;
	}
	
	public static IntervalDiscreteFlatSet intersection(LInterval lr) {
		if (lr.size() == 0) return new IntervalDiscreteFlatSet();
		IntervalDiscreteFlatSet mr = IntervalDiscreteFlatSet.full();
		for (Interval r: lr) mr.intersect(r);
		return mr;
	}

	public boolean isEmpty() {
		return isEmpty();
	}
	
	public static void main(String[] args) {
		IntervalDiscreteFlatSet m;
		m = new IntervalDiscreteFlatSet();
		m.add(10, 30);
		m.remove(10, 30);
		System.out.println("Removed: " + m);
		System.out.println();
		
		m = new IntervalDiscreteFlatSet();
		m.add(10, 20);
		m.add(30, 40);
		m.add(50, 60);
		System.out.println("Initial: " + m);
		//m.remove(15, 55);
		System.out.println("Extracted: " + m.addAndExtract(15, 55));
		System.out.println("Final: " + m);
		
		m = new IntervalDiscreteFlatSet();
		m.add(10, 20);
		m.remove(9, 10);
		System.out.println(m);
	}
}
