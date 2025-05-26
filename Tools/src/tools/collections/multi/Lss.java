package tools.collections.multi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import tools.collections.string.Ls;
import tools.tuple.SS;

@SuppressWarnings("serial")
public class Lss extends ArrayList<SS> {
	public Lss() { super(); }
	public Lss(int capacity) { super(capacity); }
	public Lss(Iterable<SS> it) { for (SS s : it) add(s); }
	public Lss(SS[] t) { for (SS s : t) add(s); }

	public static Lss of(SS... t) {
		Lss l = new Lss();
		for (SS s : t) l.add(s);
		return l;
	}

	public SS g(int i) { return get(Math.floorMod(i, size())); }

	public Lss mapped(Function<SS, SS> f) {
		Lss l = new Lss();
		for (SS s : this) l.add(f.apply(s));
		return l;
	}

	public Lss filtered(Predicate<SS> f) {
		Lss l = new Lss();
		for (SS s : this) if (f.test(s)) l.add(s);
		return l;
	}

	public void foreach(Consumer<SS> c) {
		for (SS s : this) c.accept(s);
	}

	public Lss reversed() {
		Lss l = new Lss();
		int max = size() - 1;
		for (int i = 0; i <= max; i++) l.add(get(max - i));
		return l;
	}

	public Lss shuffled() {
		Lss l = new Lss(this);
		Collections.shuffle(l);
		return l;
	}

	public Lss copy() {
		return new Lss(this);
	}

	public SS[] array() {
		SS[] t = new SS[size()];
		for (int i = 0; i < t.length; i++) t[i] = get(i);
		return t;
	}

	public String join() { return join(", "); }
	public String join(String sep) {
		StringBuilder sb = new StringBuilder();
		boolean w = false;
		for (SS s : this) {
			if (w) sb.append(sep);
			sb.append(s);
			w = true;
		}
		return sb.toString();
	}

	public void debug() {
		System.err.println(this);
	}

	public Lss distinct() {
		return new Lss(new LinkedHashSet<>(this));
	}

	public Lss sortedAlphaA() {
		Lss l = new Lss(this);
		l.sort((s1, s2) -> s1.a.compareTo(s2.a));
		return l;
	}
	public Lss sortedAlphaB() {
		Lss l = new Lss(this);
		l.sort((s1, s2) -> s1.b.compareTo(s2.b));
		return l;
	}
	public Lss sortedAlphaAB() {
		Lss l = new Lss(this);
		l.sort((s1, s2) -> {
			int cmp = s1.a.compareTo(s2.a);
			return cmp != 0 ? cmp : s1.b.compareTo(s2.b);
		});
		return l;
	}
	public Lss sortedAlphaBA() {
		Lss l = new Lss(this);
		l.sort((s1, s2) -> {
			int cmp = s1.b.compareTo(s2.b);
			return cmp != 0 ? cmp : s1.a.compareTo(s2.a);
		});
		return l;
	}
	public Lss sortedDownAlphaA() {
		Lss l = new Lss(this);
		l.sort((s1, s2) -> s2.a.compareTo(s1.a));
		return l;
	}
	public Lss sortedDownAlphaB() {
		Lss l = new Lss(this);
		l.sort((s1, s2) -> s2.b.compareTo(s1.b));
		return l;
	}
	public Lss sortedDownAlphaAB() {
		Lss l = new Lss(this);
		l.sort((s1, s2) -> {
			int cmp = s2.a.compareTo(s1.a);
			return cmp != 0 ? cmp : s2.b.compareTo(s1.b);
		});
		return l;
	}
	public Lss sortedDownAlphaBA() {
		Lss l = new Lss(this);
		l.sort((s1, s2) -> {
			int cmp = s2.b.compareTo(s1.b);
			return cmp != 0 ? cmp : s2.a.compareTo(s1.a);
		});
		return l;
	}

	public SS first() { return get(0); }
	public SS last() { return get(size() - 1); }

	public Ls toAList() {
		Ls l = new Ls();
		for (SS s : this) l.add(s.a);
		return l;
	}
	public Ls toBList() {
		Ls l = new Ls();
		for (SS s : this) l.add(s.b);
		return l;
	}
}
