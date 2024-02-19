package tools.collections.segment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;

import tools.function.IntObjConsumer;
import tools.function.IntObjPredicate;
import tools.function.ToBooleanFunction;
import tools.tuple.Segment;

@SuppressWarnings("serial")
public class LSegment extends ArrayList<Segment> {
	
	public LSegment() { super(); }
	public LSegment(int capacity) { super(capacity); }
	public LSegment(Iterable<Segment> it) { for (Segment p: it) add(p); }
	public LSegment(Segment[] t) { for (Segment i: t) add(i); }
	public LSegment(int n, IntFunction<Segment> o) { for (int i = 0; i < n; i++) add(o.apply(i)); }
	
	public static LSegment of(Segment... t) {
		LSegment l = new LSegment();
		for (Segment p: t) l.add(p);
		return l;
	}

	public LSegment mapped(Function<Segment, Segment> f) {
		LSegment l = new LSegment();
		for (Segment p: this) l.add(f.apply(p));
		return l;
	}

	public void foreach(Consumer<Segment> c) {
		for (Segment p: this) c.accept(p);
	}

	public void foreach(IntObjConsumer<Segment> c) {
		for (int i = 0; i < size(); i++) c.accept(i, get(i));
	}
	
	public LSegment subbed(int s) { return subbed(s, size(), 1); }
	public LSegment subbed(int s, int e) { return subbed(s, e, 1); }
	public LSegment subbed(int s, int e, int k) {
		LSegment l = new LSegment();
		while (s < 0) s += size();
		while (e < 0) e += size();
		if (k > 0) for (int i = s; i < e; i++) l.add(get(i));
		else for (int i = e-1; i >= s; i++) l.add(get(i));
		return l;
	}

	public String join() { return join(", "); };
	public String join(String s) {
		StringBuilder sb = new StringBuilder();
		boolean w = false;
		for (Segment i: this) {
			if (w) sb.append(s);
			sb.append(i);
			w = true;
		}
		return sb.toString();
	}

	public LSegment filtered(ToBooleanFunction<Segment> f) {
		LSegment l = new LSegment();
		for (Segment p: this) if (f.applyAsBoolean(p)) l.add(p);
		return l;
	}
	
	public LSegment filtered(IntObjPredicate<Segment> f) {
		LSegment l = new LSegment();
		for (int i = 0; i < size(); i++) {
			Segment p = get(i);
			if (f.test(i, p)) l.add(p);
		}
		return l;
	}

	public LSegment reversed() {
		LSegment l = new LSegment();
		int max = size() - 1;
		for (int i = 0; i <= max; i++) l.add(get(max - i));
		return l;
	}

	public LSegment shuffled() {
		LSegment l = new LSegment(this);
		Collections.shuffle(l);
		return l;
	}

	public LSegment copy() {
		return new LSegment(this);
	}

	public Segment[] array() {
		Segment[] t = new Segment[size()];
		for (int i = 0; i < t.length; i++) t[i] = get(i);
		return t;
	}
	
	public void debug() {
		System.err.println(this);
	}
	
	public LSegment distinct() {
		return new LSegment(new LinkedHashSet<>(this)); 
	}
}
