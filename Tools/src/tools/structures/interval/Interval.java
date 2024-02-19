package tools.structures.interval;

import java.util.Iterator;
import java.util.NavigableSet;
import java.util.TreeSet;

import tools.collections.segment.LSegment;
import tools.tuple.Segment;


public class Interval {
	private TreeSet<Segment> ss = new TreeSet<>();
	
	public boolean strict;
	
	/**
	 * strict = true:  [1, 3] + [4, 7] = [1, 3], [4, 7]
	 * strict = false: [1, 3] + [4, 7] = [1, 7]
	 * 
	 * @param strict strict mode?
	 */
	public Interval(boolean strict) {
		this.strict = strict;
	}
	
	public void mergeAll(LSegment l) {
		for (Segment s: l) merge(s);
	}

	public void merge(Segment s) {
		int bonus = strict ? 0 : 1;
		Segment floor = ss.floor(s);
		if (floor == null || floor.b + bonus < s.a) {
			floor = new Segment(s.a, s.b);
			ss.add(floor);
		}
		floor.b = Math.max(floor.b, s.b);
		NavigableSet<Segment> sub = ss.tailSet(floor, false);
		Iterator<Segment> it = sub.iterator();
		while (it.hasNext()) {
			Segment seg = it.next();
			if (s.b + bonus >= seg.a) {
				it.remove();
				floor.b = Math.max(s.b, seg.b);
			} else break;
		}
	}

	public void remove(Segment s) {
		// TODO
	}
	
	public boolean contains(Segment s) {
		Segment floor = ss.floor(s);
		if (floor == null) return false;
		return floor.a <= s.a && floor.b >= s.b;
	}

	public boolean intersects(Segment s) {
		return intersects(s, strict);
	}
	
	public boolean intersects(Segment s, boolean strict) {
		int bonus = strict ? 0 : 1;
		Segment floor = ss.floor(s);
		if (floor != null && floor.b + bonus > s.a) return true;
		Segment ceil = ss.ceiling(s);
		if (ceil != null && ceil.a - bonus < s.b) return true;
		return false;
	}

	public LSegment getSegments() {
		return new LSegment(ss);
	}
	
	@Override
	public String toString() {
		return ss.toString();
	}
	
	public static void main(String[] args) {
		Interval in = new Interval(false);
		//in.merge(new Segment(8, 13));
		//System.out.println(in);
		in.merge(new Segment(2, 3));
		System.out.println(in);
		//in.merge(new Segment(2, 3));
		//System.out.println(in);
		//in.merge(new Segment(15, 19));
		//System.out.println(in);
		//in.merge(new Segment(12, 14));
		//System.out.println(in);
		in.merge(new Segment(4, 24));
		System.out.println(in);
	}
	
	
}
