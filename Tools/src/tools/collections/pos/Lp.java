package tools.collections.pos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;

import tools.function.IntObjConsumer;
import tools.function.IntObjPredicate;
import tools.function.ToBooleanFunction;
import tools.tuple.Pos;

@SuppressWarnings("serial")
public class Lp extends ArrayList<Pos> {
	
	public Lp() { super(); }
	public Lp(int capacity) { super(capacity); }
	public Lp(Iterable<Pos> it) { for (Pos p: it) add(p); }
	public Lp(Pos[] t) { for (Pos i: t) add(i); }
	public Lp(int n, IntFunction<Pos> o) { for (int i = 0; i < n; i++) add(o.apply(i)); }
	
	public static Lp of(Pos... t) {
		Lp l = new Lp();
		for (Pos p: t) l.add(p);
		return l;
	}

	public Lp mapped(Function<Pos, Pos> f) {
		Lp l = new Lp();
		for (Pos p: this) l.add(f.apply(p));
		return l;
	}

	public void foreach(Consumer<Pos> c) {
		for (Pos p: this) c.accept(p);
	}

	public void foreach(IntObjConsumer<Pos> c) {
		for (int i = 0; i < size(); i++) c.accept(i, get(i));
	}
	
	public Lp subbed(int s) { return subbed(s, size(), 1); }
	public Lp subbed(int s, int e) { return subbed(s, e, 1); }
	public Lp subbed(int s, int e, int k) {
		Lp l = new Lp();
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
		for (Pos i: this) {
			if (w) sb.append(s);
			sb.append(i);
			w = true;
		}
		return sb.toString();
	}

	public Lp filtered(ToBooleanFunction<Pos> f) {
		Lp l = new Lp();
		for (Pos p: this) if (f.applyAsBoolean(p)) l.add(p);
		return l;
	}
	
	public Lp filtered(IntObjPredicate<Pos> f) {
		Lp l = new Lp();
		for (int i = 0; i < size(); i++) {
			Pos p = get(i);
			if (f.test(i, p)) l.add(p);
		}
		return l;
	}

	public Lp reversed() {
		Lp l = new Lp();
		int max = size() - 1;
		for (int i = 0; i <= max; i++) l.add(get(max - i));
		return l;
	}

	public Lp shuffled() {
		Lp l = new Lp(this);
		Collections.shuffle(l);
		return l;
	}

	public Lp copy() {
		return new Lp(this);
	}

	public Pos[] array() {
		Pos[] t = new Pos[size()];
		for (int i = 0; i < t.length; i++) t[i] = get(i);
		return t;
	}
	
	public void debug() {
		System.err.println(this);
	}
	
	public Lp distinct() {
		return new Lp(new LinkedHashSet<>(this)); 
	}
	
	public Pos barycenter() {
		int l = 0, c = 0;
		for (Pos p : this) {
			l += p.l;
			c += p.c;
		}
		return new Pos(l / size(), c / size());
	}
}
