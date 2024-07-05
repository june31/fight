package tools.collections.multi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;

import tools.function.IntObjConsumer;
import tools.function.IntObjPredicate;
import tools.function.ToBooleanFunction;
import tools.tuple.II;

@SuppressWarnings("serial")
public class Lii extends ArrayList<II> {
	
	public Lii() { super(); }
	public Lii(int capacity) { super(capacity); }
	public Lii(Iterable<II> it) { for (II p: it) add(p); }
	public Lii(II[] t) { for (II i: t) add(i); }
	public Lii(int n, IntFunction<II> o) { for (int i = 0; i < n; i++) add(o.apply(i)); }
	
	public static Lii of(II... t) {
		Lii l = new Lii();
		for (II p: t) l.add(p);
		return l;
	}

	public II g(int i) { return get(Math.floorMod(i, size())); }

	public Lii mapped(Function<II, II> f) {
		Lii l = new Lii();
		for (II p: this) l.add(f.apply(p));
		return l;
	}

	public void foreach(Consumer<II> c) {
		for (II p: this) c.accept(p);
	}

	public void foreach(IntObjConsumer<II> c) {
		for (int i = 0; i < size(); i++) c.accept(i, get(i));
	}
	
	public Lii subbed(int s) { return subbed(s, size(), 1); }
	public Lii subbed(int s, int e) { return subbed(s, e, 1); }
	public Lii subbed(int s, int e, int k) {
		Lii l = new Lii();
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
		for (II i: this) {
			if (w) sb.append(s);
			sb.append(i);
			w = true;
		}
		return sb.toString();
	}

	public Lii filtered(ToBooleanFunction<II> f) {
		Lii l = new Lii();
		for (II p: this) if (f.applyAsBoolean(p)) l.add(p);
		return l;
	}
	
	public Lii filtered(IntObjPredicate<II> f) {
		Lii l = new Lii();
		for (int i = 0; i < size(); i++) {
			II p = get(i);
			if (f.test(i, p)) l.add(p);
		}
		return l;
	}

    public Lii sortedUp() {
        Lii sortedList = new Lii();
        sortedList.addAll(this);
        Collections.sort(sortedList, Comparator.comparingInt((II ii) -> ii.index).thenComparingInt(ii -> ii.value));
        return sortedList;
    }

    public Lii sortedDown() {
        Lii sortedList = new Lii();
        sortedList.addAll(this);
        Collections.sort(sortedList, Comparator.comparingInt((II ii) -> ii.index).thenComparingInt(ii -> ii.value).reversed());
        return sortedList;
    }
	
	public Lii reversed() {
		Lii l = new Lii();
		int max = size() - 1;
		for (int i = 0; i <= max; i++) l.add(get(max - i));
		return l;
	}

	public Lii shuffled() {
		Lii l = new Lii(this);
		Collections.shuffle(l);
		return l;
	}

	public Lii copy() {
		return new Lii(this);
	}

	public II[] array() {
		II[] t = new II[size()];
		for (int i = 0; i < t.length; i++) t[i] = get(i);
		return t;
	}
	
	public void debug() {
		System.err.println(this);
	}
	
	public Lii distinct() {
		return new Lii(new LinkedHashSet<>(this)); 
	}
	
	public void add(int a, int b) {
		add(new II(a, b));
	}
}
