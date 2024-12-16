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
import tools.tuple.Pos3;

@SuppressWarnings("serial")
public class Lp3 extends ArrayList<Pos3> {
	
	public Lp3() { super(); }
	public Lp3(int capacity) { super(capacity); }
	public Lp3(Iterable<Pos3> it) { for (Pos3 p: it) add(p); }
	public Lp3(Pos3[] t) { for (Pos3 i: t) add(i); }
	public Lp3(int n, IntFunction<Pos3> o) { for (int i = 0; i < n; i++) add(o.apply(i)); }
	
	public static Lp3 of(Pos3... t) {
		Lp3 l = new Lp3();
		for (Pos3 p: t) l.add(p);
		return l;
	}

	public Pos3 g(int i) { return get(Math.floorMod(i, size())); }
	
	public Lp3 mapped(Function<Pos3, Pos3> f) {
		Lp3 l = new Lp3();
		for (Pos3 p: this) l.add(f.apply(p));
		return l;
	}

	public void foreach(Consumer<Pos3> c) {
		for (Pos3 p: this) c.accept(p);
	}

	public void foreach(IntObjConsumer<Pos3> c) {
		for (int i = 0; i < size(); i++) c.accept(i, get(i));
	}
	
	public Lp3 subbed(int s) { return subbed(s, size(), 1); }
	public Lp3 subbed(int s, int e) { return subbed(s, e, 1); }
	public Lp3 subbed(int s, int e, int k) {
		Lp3 l = new Lp3();
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
		for (Pos3 i: this) {
			if (w) sb.append(s);
			sb.append(i);
			w = true;
		}
		return sb.toString();
	}

	public Lp3 filtered(ToBooleanFunction<Pos3> f) {
		Lp3 l = new Lp3();
		for (Pos3 p: this) if (f.applyAsBoolean(p)) l.add(p);
		return l;
	}
	
	public Lp3 filtered(IntObjPredicate<Pos3> f) {
		Lp3 l = new Lp3();
		for (int i = 0; i < size(); i++) {
			Pos3 p = get(i);
			if (f.test(i, p)) l.add(p);
		}
		return l;
	}

	public Lp3 reversed() {
		Lp3 l = new Lp3();
		int max = size() - 1;
		for (int i = 0; i <= max; i++) l.add(get(max - i));
		return l;
	}

	public Lp3 shuffled() {
		Lp3 l = new Lp3(this);
		Collections.shuffle(l);
		return l;
	}

	public Lp3 copy() {
		return new Lp3(this);
	}

	public Pos3[] array() {
		Pos3[] t = new Pos3[size()];
		for (int i = 0; i < t.length; i++) t[i] = get(i);
		return t;
	}
	
	public void debug() {
		System.err.println(this);
	}
	
	public Lp3 distinct() {
		return new Lp3(new LinkedHashSet<>(this)); 
	}
}
