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
import tools.tuple.III;

@SuppressWarnings("serial")
public class Liii extends ArrayList<III> {
	
	public Liii() { super(); }
	public Liii(int capacity) { super(capacity); }
	public Liii(Iterable<III> it) { for (III p: it) add(p); }
	public Liii(III[] t) { for (III i: t) add(i); }
	public Liii(int n, IntFunction<III> o) { for (int i = 0; i < n; i++) add(o.apply(i)); }
	
	public static Liii of(III... t) {
		Liii l = new Liii();
		for (III p: t) l.add(p);
		return l;
	}

	public III g(int i) { return get(Math.floorMod(i, size())); }

	public Liii mapped(Function<III, III> f) {
		Liii l = new Liii();
		for (III p: this) l.add(f.apply(p));
		return l;
	}

	public void foreach(Consumer<III> c) {
		for (III p: this) c.accept(p);
	}

	public void foreach(IntObjConsumer<III> c) {
		for (int i = 0; i < size(); i++) c.accept(i, get(i));
	}
	
	public Liii subbed(int s) { return subbed(s, size(), 1); }
	public Liii subbed(int s, int e) { return subbed(s, e, 1); }
	public Liii subbed(int s, int e, int k) {
		Liii l = new Liii();
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
		for (III i: this) {
			if (w) sb.append(s);
			sb.append(i);
			w = true;
		}
		return sb.toString();
	}

	public Liii filtered(ToBooleanFunction<III> f) {
		Liii l = new Liii();
		for (III p: this) if (f.applyAsBoolean(p)) l.add(p);
		return l;
	}
	
	public Liii filtered(IntObjPredicate<III> f) {
		Liii l = new Liii();
		for (int i = 0; i < size(); i++) {
			III p = get(i);
			if (f.test(i, p)) l.add(p);
		}
		return l;
	}

	public Liii reversed() {
		Liii l = new Liii();
		int max = size() - 1;
		for (int i = 0; i <= max; i++) l.add(get(max - i));
		return l;
	}

	public Liii shuffled() {
		Liii l = new Liii(this);
		Collections.shuffle(l);
		return l;
	}

	public Liii copy() {
		return new Liii(this);
	}

	public III[] array() {
		III[] t = new III[size()];
		for (int i = 0; i < t.length; i++) t[i] = get(i);
		return t;
	}
	
	public void debug() {
		System.err.println(this);
	}
	
	public Liii distinct() {
		return new Liii(new LinkedHashSet<>(this)); 
	}

    public Liii sortedUp123() {
        Liii sortedList = new Liii();
        sortedList.addAll(this);
        sortedList.sort(java.util.Comparator.comparingInt((III iii) -> iii.line)
            .thenComparingInt(iii -> iii.col)
            .thenComparingInt(iii -> iii.value));
        return sortedList;
    }

    public Liii sortedUp312() {
        Liii sortedList = new Liii();
        sortedList.addAll(this);
        sortedList.sort(java.util.Comparator.comparingInt((III iii) -> iii.value)
            .thenComparingInt(iii -> iii.line)
            .thenComparingInt(iii -> iii.col));
        return sortedList;
    }

    public Liii sortedDown123() {
        Liii sortedList = new Liii();
        sortedList.addAll(this);
        sortedList.sort(java.util.Comparator.comparingInt((III iii) -> iii.line)
            .thenComparingInt(iii -> iii.col)
            .thenComparingInt(iii -> iii.value).reversed());
        return sortedList;
    }

    public Liii sortedDown312() {
        Liii sortedList = new Liii();
        sortedList.addAll(this);
        sortedList.sort(java.util.Comparator.comparingInt((III iii) -> iii.value)
            .thenComparingInt(iii -> iii.line)
            .thenComparingInt(iii -> iii.col).reversed());
        return sortedList;
    }
}
