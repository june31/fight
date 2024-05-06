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

	public IntervalDiscreteFlatSet(Collection<Interval> col) { addAll(col); }

	public void add(long a, long b) { add(new Interval(a, b)); }
	public boolean add(Interval toAdd) {
		boolean result = false;
		
		// First process the floor 
		Interval floor = floor(toAdd);
		
		// If floor is null or too low, create a new 'floor' that will grow to the right.
		if (floor == null || floor.b < toAdd.a - 1) {
			floor = new Interval(toAdd.a, toAdd.a);
			result = super.add(floor); // true
		}
		
		NavigableSet<Interval> rest = tailSet(floor, false);
		floor.b = toAdd.b; // floor.b will be at least toAdd.b.
		
		Iterator<Interval> it = rest.iterator();
		while (it.hasNext()) {
			Interval seg = it.next();
			if (toAdd.b + 1 >= seg.a) {
				it.remove();
				floor.b = Math.max(floor.b, seg.b);
				result |= floor.b != seg.b;
			} else break;
		}
		return result;
	}

	public IntervalDiscreteFlatSet addAndExtract(long a, long b) { return addAndExtract(new Interval(a, b)); }
	public IntervalDiscreteFlatSet addAndExtract(Interval r) { return null; } // TODO

	public void remove(long a, long b) { remove(new Interval(a, b)); }
	public void remove(Interval toRemove) {
		// First process the floor, then process the rest. 
		Interval floor = floor(toRemove);
		NavigableSet<Interval> rest;
		
		// If floor is null, the 'rest' is the whole set.
		if (floor == null) rest = this;
		
		// Check whether parts before and after toRemove shall be added.
		// Then remove floor from the set. Floor is removed last to allow tailset to work correctly.
		else {
			if (floor.a < toRemove.a) super.add(new Interval(floor.a, toRemove.a - 1));
			if (floor.b > toRemove.b) super.add(new Interval(toRemove.b + 1, floor.b));
			rest = tailSet(toRemove, false);
			super.remove(floor);
		}
		
		// Now process 'rest'.
		Iterator<Interval> it = rest.iterator();
		while (it.hasNext()) {
			Interval seg = it.next();
			if (seg.b <= toRemove.b) it.remove();
			else if (seg.a <= toRemove.b) seg.a = toRemove.b + 1;
			else break;
		}
	}

	// Same as remove, but returns IntervalLongFlatSet actually removed
	public IntervalDiscreteFlatSet removeAndExtract(long a, long b) { return removeAndExtract(new Interval(a, b)); }
	// Same as remove, but returns IntervalLongFlatSet actually removed
	public IntervalDiscreteFlatSet removeAndExtract(Interval toRemove) {
		IntervalDiscreteFlatSet extracted = new IntervalDiscreteFlatSet();
		// First process the floor, then process the rest. 
		Interval floor = floor(toRemove);
		NavigableSet<Interval> rest;
		
		// If floor is null, the 'rest' is the whole set.
		if (floor == null) rest = this;
		
		// Check whether parts before and after toRemove shall be added.
		// Then remove floor from the set. Floor is removed last to allow tailset to work correctly.
		else {
			if (floor.a < toRemove.a) super.add(new Interval(floor.a, toRemove.a - 1));
			if (floor.b > toRemove.b) super.add(new Interval(toRemove.b + 1, floor.b));

			// Fill extracted
			long low = Math.max(floor.a, toRemove.a);
			long high = Math.min(floor.b, toRemove.b);
			if (low <= high) extracted.add(low, high);
			
			rest = tailSet(toRemove, false);
			super.remove(floor);
		}
		
		// Now process 'rest'.
		Iterator<Interval> it = rest.iterator();
		while (it.hasNext()) {
			Interval seg = it.next();
			
			// Fill extracted
			long low = Math.max(seg.a, toRemove.a);
			long high = Math.min(seg.b, toRemove.b);
			if (low <= high) extracted.add(low, high);

			if (seg.b <= toRemove.b) it.remove();
			else if (seg.a <= toRemove.b) seg.a = toRemove.b + 1;
			else break;
		}
		
		return extracted;
	}
	
	
	public void remove(IntervalDiscreteFlatSet mr) { for (Interval r: mr) remove(r); }

	public void intersect(Interval r) { remove(reverse(r)); }

	public void intersect(IntervalDiscreteFlatSet mr) { remove(mr.reversed()); }
	
	public boolean contains(long l) { return contains(new Interval(l, l)); }
	public boolean contains(long l1, long l2) { return contains(new Interval(l1, l2)); }
	public boolean contains(Interval r) {
		Interval floor = floor(r);
		if (floor == null) return false;
		return floor.a <= r.a && floor.b >= r.b;
	}
	
	public boolean intersects(long l1, long l2) { return intersects(new Interval(l1, l2)); }
	public boolean intersects(Interval r) {
		Interval floor = floor(r);
		if (floor != null && floor.b + 1 > r.a) return true;
		Interval ceil = ceiling(r);
		if (ceil != null && ceil.a - 1 < r.b) return true;
		return false;
	}

	public LInterval toList() { return new LInterval(this); }

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

	public static void main(String[] args) {
		IntervalDiscreteFlatSet m;
		m = new IntervalDiscreteFlatSet();
		m.add(9, 12);
		m.add(14, 17);
		m.add(21, 22);
		m.add(26, 35);
		m.remove(10, 30);
		System.out.println("Final: " + m);
		System.out.println();
		
		m = new IntervalDiscreteFlatSet();
		m.add(9, 12);
		m.add(14, 17);
		m.add(21, 22);
		m.add(26, 35);
		System.out.println("Initial: " + m);
		System.out.println("Extracted: " + m.removeAndExtract(10, 30));
		System.out.println("Final: " + m);
		System.out.println();
		
		m = new IntervalDiscreteFlatSet();
		m.add(9, 12);
		m.add(14, 17);
		m.add(21, 22);
		m.add(26, 35);
		m.add(10, 30);
		System.out.println("Final: " + m);
		System.out.println();
		
		m = new IntervalDiscreteFlatSet();
		m.add(9, 12);
		m.add(14, 17);
		m.add(21, 22);
		m.add(26, 35);
		System.out.println("Initial: " + m);
		System.out.println("Extracted: " + m.addAndExtract(10, 30));
		System.out.println("Final: " + m);
		System.out.println();
	}
}
