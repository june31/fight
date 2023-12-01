package tools.collections.pos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Function;

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
	
	public static Lp of(Pos... t) {
		Lp l = new Lp();
		for (Pos p: t) l.add(p);
		return l;
	}

	public Lp map(Function<Pos, Pos> f) {
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
	
	public Lp sub(int s) { return sub(s, size()); }
	public Lp sub(int s, int e) {
		Lp l = new Lp();
		if (s < 0) s += size();
		if (e < 0) e += size();
		for (int i = s; i < e; i++) l.add(get(i));
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

	public Lp filter(ToBooleanFunction<Pos> f) {
		Lp l = new Lp();
		for (Pos p: this) if (f.applyAsBoolean(p)) l.add(p);
		return l;
	}
	
	public Lp filter(IntObjPredicate<Pos> f) {
		Lp l = new Lp();
		for (int i = 0; i < size(); i++) {
			Pos p = get(i);
			if (f.test(i, p)) l.add(p);
		}
		return l;
	}

	public Lp reverse() {
		Lp l = new Lp(this);
		int max = size() - 1;
		for (int i = 0; i <= max; i++) l.add(get(max - i));
		return l;
	}

	public Lp shuffle() {
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
}
