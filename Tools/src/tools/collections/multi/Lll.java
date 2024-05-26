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
import tools.structures.interval.IntervalDiscreteFlatSet;
import tools.tuple.LL;

@SuppressWarnings("serial")
public class Lll extends ArrayList<LL> {

	public Lll() { super(); }
	public Lll(int capacity) { super(capacity); }
	public Lll(Iterable<LL> it) { for (LL p: it) add(p); }
	public Lll(LL[] t) { for (LL i: t) add(i); }
	public Lll(int n, IntFunction<LL> o) { for (int i = 0; i < n; i++) add(o.apply(i)); }

	public static Lll of(LL... t) {
		Lll l = new Lll();
		for (LL p: t) l.add(p);
		return l;
	}

	public void add(long a, long b) { add(new LL(a, b)); }

	public Lll mapped(Function<LL, LL> f) {
		Lll l = new Lll();
		for (LL p: this) l.add(f.apply(p));
		return l;
	}

	public void foreach(Consumer<LL> c) {
		for (LL p: this) c.accept(p);
	}

	public void foreach(IntObjConsumer<LL> c) {
		for (int i = 0; i < size(); i++) c.accept(i, get(i));
	}

	public Lll subbed(int s) { return subbed(s, size(), 1); }
	public Lll subbed(int s, int e) { return subbed(s, e, 1); }
	public Lll subbed(int s, int e, int k) {
		Lll l = new Lll();
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
		for (LL i: this) {
			if (w) sb.append(s);
			sb.append(i);
			w = true;
		}
		return sb.toString();
	}

	public Lll filtered(ToBooleanFunction<LL> f) {
		Lll l = new Lll();
		for (LL p: this) if (f.applyAsBoolean(p)) l.add(p);
		return l;
	}

	public Lll filtered(IntObjPredicate<LL> f) {
		Lll l = new Lll();
		for (int i = 0; i < size(); i++) {
			LL p = get(i);
			if (f.test(i, p)) l.add(p);
		}
		return l;
	}

	public Lll reversed() {
		Lll l = new Lll();
		int max = size() - 1;
		for (int i = 0; i <= max; i++) l.add(get(max - i));
		return l;
	}

	public Lll sortedUp() {
		Lll sortedList = new Lll();
		sortedList.addAll(this);
		Collections.sort(sortedList, Comparator.comparingLong((LL ll) -> ll.a).thenComparingLong(ll -> ll.b));
		return sortedList;
	}

	public Lll sortedDown() {
		Lll sortedList = new Lll();
		sortedList.addAll(this);
		Collections.sort(sortedList, Comparator.comparingLong((LL ll) -> ll.a).thenComparingLong(ll -> ll.b).reversed());
		return sortedList;
	}

	public Lll shuffled() {
		Lll l = new Lll(this);
		Collections.shuffle(l);
		return l;
	}

	public Lll copy() {
		return new Lll(this);
	}

	public LL[] array() {
		LL[] t = new LL[size()];
		for (int i = 0; i < t.length; i++) t[i] = get(i);
		return t;
	}

	public void debug() {
		System.err.println(this);
	}

	public Lll distinct() {
		return new Lll(new LinkedHashSet<>(this)); 
	}

	public IntervalDiscreteFlatSet toIntervalSet() {
		return new IntervalDiscreteFlatSet(this);
	}

	public Lll flattened() {
		Lll lr = sortedUp();
		int n = size();
		for (int i = 0; i < n; i++) {
			LL m = lr.get(i);
			long max = m.b;
			for (int j = i + 1; j < n; j++) {
				LL o = lr.get(j);
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
