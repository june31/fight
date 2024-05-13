package tools.structures.interval;

import java.util.Collection;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.TreeSet;

import tools.collections.multi.Ldd;
import tools.tuple.DD;


@SuppressWarnings("serial")
public class IntervalContinuousFlatSet extends TreeSet<DD> {
	
	public IntervalContinuousFlatSet() {}

	public IntervalContinuousFlatSet(Collection<DD> col) { addAll(col); }

	public void add(double a, double b) { add(new DD(a, b)); }
	public boolean add(DD toAdd) {
		boolean result = false;
		
		// First process the floor 
		DD floor = floor(toAdd);
		
		// If floor is null or too low, create a new 'floor' that will grow to the right.
		if (floor == null || floor.b < toAdd.a) {
			floor = new DD(toAdd.a, toAdd.a);
			result = super.add(floor); // true
		}
		
		NavigableSet<DD> rest = tailSet(floor, false);
		floor.b = Math.max(floor.b, toAdd.b);
		
		Iterator<DD> it = rest.iterator();
		while (it.hasNext()) {
			DD seg = it.next();
			if (toAdd.b >= seg.a) {
				it.remove();
				floor.b = Math.max(floor.b, seg.b);
				result |= floor.b != seg.b;
			} else break;
		}
		return result;
	}

	public IntervalContinuousFlatSet addAndExtract(double a, double b) { return addAndExtract(new DD(a, b)); }
	public IntervalContinuousFlatSet addAndExtract(DD toAdd) {
		IntervalContinuousFlatSet extracted = new IntervalContinuousFlatSet();
		
		// First process the floor
		DD floor = floor(toAdd);

		// If floor is null or too low, create a new 'floor' that will grow to the right.
		
		if (floor == null || floor.b < toAdd.a) {
			floor = new DD(toAdd.a, toAdd.a);
			super.add(floor);
		}
		double f = floor.b;
		
		NavigableSet<DD> rest = tailSet(floor, false);
		floor.b = Math.max(floor.b, toAdd.b);

		Iterator<DD> it = rest.iterator();
		while (it.hasNext()) {
			DD seg = it.next();
			if (toAdd.b >= seg.a) {
				it.remove();
				floor.b = Math.max(floor.b, seg.b);
				extracted.add(f, Math.min(floor.b, seg.a));
				f = seg.b;
			} else break;
		}
		if (f <= floor.b) extracted.add(f, floor.b);
		return extracted;
	}

	public void remove(double a, double b) { remove(new DD(a, b)); }
	public void remove(DD toRemove) {
		// First process the floor, then process the rest. 
		DD floor = floor(toRemove);
		NavigableSet<DD> rest;
		
		// If floor is null, the 'rest' is the whole set.
		if (floor == null) rest = this;
		
		// Check whether parts before and after toRemove shall be added.
		// Then remove floor from the set. Floor is removed last to allow tailset to work correctly.
		else {
			super.remove(floor);
			if (floor.a < toRemove.a) super.add(new DD(floor.a, Math.min(floor.b, toRemove.a)));
			if (floor.b > toRemove.b) super.add(new DD(toRemove.b, floor.b));
			rest = tailSet(toRemove, false);
		}
		
		// Now process 'rest'.
		Iterator<DD> it = rest.iterator();
		while (it.hasNext()) {
			DD seg = it.next();
			if (seg.b <= toRemove.b) it.remove();
			else if (seg.a <= toRemove.b) seg.a = toRemove.b;
			else break;
		}
	}

	// Same as remove, but returns IntervalContinuousFlatSet actually removed
	public IntervalContinuousFlatSet removeAndExtract(double a, double b) { return removeAndExtract(new DD(a, b)); }
	// Same as remove, but returns IntervalContinuousFlatSet actually removed
	public IntervalContinuousFlatSet removeAndExtract(DD toRemove) {
		IntervalContinuousFlatSet extracted = new IntervalContinuousFlatSet();
		// First process the floor, then process the rest. 
		DD floor = floor(toRemove);
		NavigableSet<DD> rest;
		
		// If floor is null, the 'rest' is the whole set.
		if (floor == null) rest = this;
		
		// Check whether parts before and after toRemove shall be added.
		// Then remove floor from the set. Floor is removed last to allow tailset to work correctly.
		else {
			super.remove(floor);
			if (floor.a < toRemove.a) super.add(new DD(floor.a, Math.min(floor.b, toRemove.a)));
			if (floor.b > toRemove.b) super.add(new DD(toRemove.b, floor.b));

			// Fill extracted
			double low = Math.max(floor.a, toRemove.a);
			double high = Math.min(floor.b, toRemove.b);
			if (low <= high) extracted.add(low, high);
			
			rest = tailSet(toRemove, false);
		}
		
		// Now process 'rest'.
		Iterator<DD> it = rest.iterator();
		while (it.hasNext()) {
			DD seg = it.next();
			
			// Fill extracted
			double low = Math.max(seg.a, toRemove.a);
			double high = Math.min(seg.b, toRemove.b);
			if (low <= high) extracted.add(low, high);

			if (seg.b <= toRemove.b) it.remove();
			else if (seg.a <= toRemove.b) seg.a = toRemove.b;
			else break;
		}
		
		return extracted;
	}
	
	public boolean addAll(Collection<? extends DD> mr) {
		boolean result = false;
		for (DD r: mr) result |= add(r);
		return result;
	}
	
	public boolean removeAll(Collection<?> mr) {
		for (Object r: mr) remove((DD) r);
		return true; // unused
	}

	public void intersect(DD r) { removeAll(reverse(r)); }

	public void intersect(IntervalContinuousFlatSet mr) { removeAll(mr.reversed()); }
	
	public boolean contains(double l) { return contains(new DD(l, l)); }
	public boolean contains(double l1, double l2) { return contains(new DD(l1, l2)); }
	public boolean contains(DD r) {
		DD floor = floor(r);
		if (floor == null) return false;
		return floor.a <= r.a && floor.b >= r.b;
	}
	
	public boolean intersects(double l1, double l2) { return intersects(new DD(l1, l2)); }
	public boolean intersects(DD r) {
		DD floor = floor(r);
		if (floor != null && floor.b > r.a) return true;
		DD ceil = ceiling(r);
		if (ceil != null && ceil.a < r.b) return true;
		return false;
	}

	public Ldd toList() { return new Ldd(this); }

	public IntervalContinuousFlatSet reversed() {
		IntervalContinuousFlatSet mr = new IntervalContinuousFlatSet();
		mr.add(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		for (DD r: this) mr.remove(r);
		return mr;
	}

	public static IntervalContinuousFlatSet full() {
		IntervalContinuousFlatSet mr = new IntervalContinuousFlatSet();
		mr.add(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		return mr;
	}

	public static IntervalContinuousFlatSet reverse(DD r) {
		IntervalContinuousFlatSet mr = new IntervalContinuousFlatSet();
		mr.add(Double.NEGATIVE_INFINITY, r.a);
		mr.add(r.b, Double.POSITIVE_INFINITY);
		return mr;
	}
	
	public static IntervalContinuousFlatSet intersection(Ldd lr) {
		if (lr.size() == 0) return new IntervalContinuousFlatSet();
		IntervalContinuousFlatSet mr = IntervalContinuousFlatSet.full();
		for (DD r: lr) mr.intersect(r);
		return mr;
	}
	
	public double flatSize() {
		double size = 0;
		for (DD r : this) size += r.size();
		return size;
	}

	public static void main(String[] args) {
		IntervalContinuousFlatSet m;

		System.out.println("Removal");
		m = new IntervalContinuousFlatSet();
		m.add(10, 40);
		m.remove(20, 30);
		System.out.println("Final: " + m);
		System.out.println();
		
		System.out.println("Removal and extraction");
		m = new IntervalContinuousFlatSet();
		m.add(10, 40);
		System.out.println("Initial: " + m);
		System.out.println("Extracted: " + m.removeAndExtract(20, 30));
		System.out.println("Final: " + m);
		System.out.println();
		
		System.out.println("Addition");
		m = new IntervalContinuousFlatSet();
		m.add(10, 20);
		m.add(11, 13);
		System.out.println("Final: " + m);
		System.out.println();
		
		System.out.println("Addition and extraction");
		m = new IntervalContinuousFlatSet();
		m.add(0, 10);
		System.out.println("Initial: " + m);
		System.out.println("Extracted: " + m.addAndExtract(4, 6));
		System.out.println("Final: " + m);
		System.out.println();
	}
}
