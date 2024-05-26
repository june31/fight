package tools.collections.multi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;

import tools.function.IntObjConsumer;
import tools.function.IntObjPredicate;
import tools.function.ToBooleanFunction;
import tools.structures.interval.IntervalContinuousFlatSet;
import tools.tuple.DD;

@SuppressWarnings("serial")
public class Ldd extends ArrayList<DD> {
	
	public Ldd() { super(); }
	public Ldd(int capacity) { super(capacity); }
	public Ldd(Iterable<DD> it) { for (DD p: it) add(p); }
	public Ldd(DD[] t) { for (DD i: t) add(i); }
	public Ldd(int n, IntFunction<DD> o) { for (int i = 0; i < n; i++) add(o.apply(i)); }
	
	public static Ldd of(DD... t) {
		Ldd l = new Ldd();
		for (DD p: t) l.add(p);
		return l;
	}

	public DD g(int i) { return get(Math.floorMod(i, size())); }
	
	public void add(long a, long b) { add(new DD(a, b)); }
	
	public Ldd mapped(Function<DD, DD> f) {
		Ldd l = new Ldd();
		for (DD p: this) l.add(f.apply(p));
		return l;
	}

	public void foreach(Consumer<DD> c) {
		for (DD p: this) c.accept(p);
	}

	public void foreach(IntObjConsumer<DD> c) {
		for (int i = 0; i < size(); i++) c.accept(i, get(i));
	}
	
	public Ldd subbed(int s) { return subbed(s, size(), 1); }
	public Ldd subbed(int s, int e) { return subbed(s, e, 1); }
	public Ldd subbed(int s, int e, int k) {
		Ldd l = new Ldd();
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
		for (DD i: this) {
			if (w) sb.append(s);
			sb.append(i);
			w = true;
		}
		return sb.toString();
	}

	public Ldd filtered(ToBooleanFunction<DD> f) {
		Ldd l = new Ldd();
		for (DD p: this) if (f.applyAsBoolean(p)) l.add(p);
		return l;
	}
	
	public Ldd filtered(IntObjPredicate<DD> f) {
		Ldd l = new Ldd();
		for (int i = 0; i < size(); i++) {
			DD p = get(i);
			if (f.test(i, p)) l.add(p);
		}
		return l;
	}

	public Ldd reversed() {
		Ldd l = new Ldd();
		int max = size() - 1;
		for (int i = 0; i <= max; i++) l.add(get(max - i));
		return l;
	}

	public Ldd sorted() {
		Ldd l = new Ldd(this);
		Collections.sort(l);
		return l;
	}
	
	public Ldd shuffled() {
		Ldd l = new Ldd(this);
		Collections.shuffle(l);
		return l;
	}

	public Ldd copy() {
		return new Ldd(this);
	}

	public DD[] array() {
		DD[] t = new DD[size()];
		for (int i = 0; i < t.length; i++) t[i] = get(i);
		return t;
	}
	
	public void debug() {
		System.err.println(this);
	}
	
	public Ldd distinct() {
		return new Ldd(new LinkedHashSet<>(this)); 
	}
	
	public IntervalContinuousFlatSet toIntervalSet() {
		return new IntervalContinuousFlatSet(this);
	}
	
	public Ldd flattened() {
		Ldd lr = sorted();
		int n = size();
		for (int i = 0; i < n; i++) {
			DD m = lr.get(i);
			double max = m.b;
			for (int j = i + 1; j < n; j++) {
				DD o = lr.get(j);
				if (o.a <= max) {
					i++;
					if (max < o.b) max = o.b;
				} else break;
			}
			m.b = max;
			lr.add(m);
		}
		return lr;
	}
}
