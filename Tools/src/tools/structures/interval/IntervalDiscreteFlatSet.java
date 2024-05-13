package tools.structures.interval;

import java.util.Collection;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.TreeSet;

import tools.collections.multi.Lll;
import tools.tuple.DD;
import tools.tuple.LL;


@SuppressWarnings("serial")
public class IntervalDiscreteFlatSet extends TreeSet<LL> {
	
	public IntervalDiscreteFlatSet() {}

	public IntervalDiscreteFlatSet(Collection<LL> col) { addAll(col); }

	public void add(long a, long b) { add(new LL(a, b)); }
	public boolean add(LL toAdd) {
		boolean result = false;
		
		// First process the floor 
		LL floor = floor(toAdd);
		
		// If floor is null or too low, create a new 'floor' that will grow to the right.
		if (floor == null || floor.b < toAdd.a - 1) {
			floor = new LL(toAdd.a, toAdd.a);
			result = super.add(floor); // true
		}
		
		NavigableSet<LL> rest = tailSet(floor, false);
		floor.b = Math.max(floor.b, toAdd.b);
		
		Iterator<LL> it = rest.iterator();
		while (it.hasNext()) {
			LL seg = it.next();
			if (toAdd.b + 1 >= seg.a) {
				it.remove();
				floor.b = Math.max(floor.b, seg.b);
				result |= floor.b != seg.b;
			} else break;
		}
		return result;
	}

	public IntervalDiscreteFlatSet addAndExtract(long a, long b) { return addAndExtract(new LL(a, b)); }
	public IntervalDiscreteFlatSet addAndExtract(LL toAdd) {
		IntervalDiscreteFlatSet extracted = new IntervalDiscreteFlatSet();
		
		// First process the floor
		LL floor = floor(toAdd);

		// If floor is null or too low, create a new 'floor' that will grow to the right.
		long f;
		if (floor == null || floor.b < toAdd.a - 1) {
			floor = new LL(toAdd.a, toAdd.a);
			super.add(floor);
			f = floor.b;
		} else f = floor.b + 1;

		NavigableSet<LL> rest = tailSet(floor, false);
		floor.b = Math.max(floor.b, toAdd.b);

		Iterator<LL> it = rest.iterator();
		while (it.hasNext()) {
			LL seg = it.next();
			if (toAdd.b + 1 >= seg.a) {
				it.remove();
				floor.b = Math.max(floor.b, seg.b);
				extracted.add(f, Math.min(floor.b, seg.a - 1));
				f = seg.b + 1;
			} else break;
		}
		if (f <= floor.b) extracted.add(f, floor.b);
		return extracted;
	}

	public void remove(long a, long b) { remove(new LL(a, b)); }
	public void remove(LL toRemove) {
		// First process the floor, then process the rest. 
		LL floor = floor(toRemove);
		NavigableSet<LL> rest;
		
		// If floor is null, the 'rest' is the whole set.
		if (floor == null) rest = this;
		
		// Check whether parts before and after toRemove shall be added.
		// Then remove floor from the set. Floor is removed last to allow tailset to work correctly.
		else {
			super.remove(floor);
			if (floor.a < toRemove.a) super.add(new LL(floor.a, Math.min(floor.b, toRemove.a - 1)));
			if (floor.b > toRemove.b) super.add(new LL(toRemove.b + 1, floor.b));
			rest = tailSet(toRemove, false);
		}
		
		// Now process 'rest'.
		Iterator<LL> it = rest.iterator();
		while (it.hasNext()) {
			LL seg = it.next();
			if (seg.b <= toRemove.b) it.remove();
			else if (seg.a <= toRemove.b) seg.a = toRemove.b + 1;
			else break;
		}
	}

	// Same as remove, but returns IntervalDiscreteFlatSet actually removed
	public IntervalDiscreteFlatSet removeAndExtract(long a, long b) { return removeAndExtract(new LL(a, b)); }
	// Same as remove, but returns IntervalDiscreteFlatSet actually removed
	public IntervalDiscreteFlatSet removeAndExtract(LL toRemove) {
		IntervalDiscreteFlatSet extracted = new IntervalDiscreteFlatSet();
		// First process the floor, then process the rest. 
		LL floor = floor(toRemove);
		NavigableSet<LL> rest;
		
		// If floor is null, the 'rest' is the whole set.
		if (floor == null) rest = this;
		
		// Check whether parts before and after toRemove shall be added.
		// Then remove floor from the set. Floor is removed last to allow tailset to work correctly.
		else {
			super.remove(floor);
			if (floor.a < toRemove.a) super.add(new LL(floor.a, Math.min(floor.b, toRemove.a - 1)));
			if (floor.b > toRemove.b) super.add(new LL(toRemove.b + 1, floor.b));

			// Fill extracted
			long low = Math.max(floor.a, toRemove.a);
			long high = Math.min(floor.b, toRemove.b);
			if (low <= high) extracted.add(low, high);
			
			rest = tailSet(toRemove, false);
		}
		
		// Now process 'rest'.
		Iterator<LL> it = rest.iterator();
		while (it.hasNext()) {
			LL seg = it.next();
			
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
	
	public boolean addAll(Collection<? extends LL> mr) {
		boolean result = false;
		for (LL r: mr) result |= add(r);
		return result;
	}
	
	public boolean removeAll(Collection<?> mr) {
		for (Object r: mr) remove((LL) r);
		return true; // unused
	}

	public void intersect(LL r) { removeAll(reverse(r)); }

	public void intersect(IntervalDiscreteFlatSet mr) { removeAll(mr.reversed()); }
	
	public boolean contains(long l) { return contains(new LL(l, l)); }
	public boolean contains(long l1, long l2) { return contains(new LL(l1, l2)); }
	public boolean contains(LL r) {
		LL floor = floor(r);
		if (floor == null) return false;
		return floor.a <= r.a && floor.b >= r.b;
	}
	
	public boolean intersects(long l1, long l2) { return intersects(new LL(l1, l2)); }
	public boolean intersects(LL r) {
		LL floor = floor(r);
		if (floor != null && floor.b + 1 > r.a) return true;
		LL ceil = ceiling(r);
		if (ceil != null && ceil.a - 1 < r.b) return true;
		return false;
	}

	public Lll toList() { return new Lll(this); }

	public IntervalDiscreteFlatSet reversed() {
		IntervalDiscreteFlatSet mr = new IntervalDiscreteFlatSet();
		mr.add(Long.MIN_VALUE, Long.MAX_VALUE);
		for (LL r: this) mr.remove(r);
		return mr;
	}

	public static IntervalDiscreteFlatSet full() {
		IntervalDiscreteFlatSet mr = new IntervalDiscreteFlatSet();
		mr.add(Long.MIN_VALUE, Long.MAX_VALUE);
		return mr;
	}

	public static IntervalDiscreteFlatSet reverse(LL r) {
		IntervalDiscreteFlatSet mr = new IntervalDiscreteFlatSet();
		mr.add(Long.MIN_VALUE, r.a - 1);
		mr.add(r.b + 1, Long.MAX_VALUE);
		return mr;
	}
	
	public static IntervalDiscreteFlatSet intersection(Lll lr) {
		if (lr.size() == 0) return new IntervalDiscreteFlatSet();
		IntervalDiscreteFlatSet mr = IntervalDiscreteFlatSet.full();
		for (LL r: lr) mr.intersect(r);
		return mr;
	}
	
	public long flatSize() {
		long size = 0;
		for (LL r : this) size += r.b - r.a + 1;
		return size;
	}

	public static void main(String[] args) {
		IntervalDiscreteFlatSet m;

		System.out.println("Removal");
		m = new IntervalDiscreteFlatSet();
		m.add(20, 30);
		m.remove(0, 0);
		System.out.println("Final: " + m);
		System.out.println();
		
		System.out.println("Removal and extraction");
		m = new IntervalDiscreteFlatSet();
		m.add(9, 12);
		m.add(14, 17);
		m.add(21, 22);
		m.add(26, 35);
		System.out.println("Initial: " + m);
		System.out.println("Extracted: " + m.removeAndExtract(10, 30));
		System.out.println("Final: " + m);
		System.out.println();
		
		System.out.println("Addition");
		m = new IntervalDiscreteFlatSet();
		m.add(20, 30);
		m.add(0, 10);
		System.out.println("Final: " + m);
		System.out.println();
		
		System.out.println("Addition and extraction");
		m = new IntervalDiscreteFlatSet();
		m.add(0, 10);
		System.out.println("Initial: " + m);
		System.out.println("Extracted: " + m.addAndExtract(4, 6));
		System.out.println("Final: " + m);
		System.out.println();
	}
}
