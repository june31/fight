package tools.collections.multi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import tools.tuple.PS;
import tools.tuple.Pos;
import tools.collections.pos.Lp;

@SuppressWarnings("serial")
public class Lps extends ArrayList<PS> {

	public Lps() { }
	public Lps(Iterable<PS> it) { for (PS ps: it) add(ps); }
	public Lps(PS[] t) { for (PS ps: t) add(ps); }

	public static Lps of(Iterable<Pos> it, Function<Pos, String> strProvider) {
		Map<Pos, PS> m = new LinkedHashMap<>();
		for (Pos p : it) {
			PS ps = m.get(p);
			if (ps == null) m.put(p, new PS(p, strProvider.apply(p)));
		}
		return new Lps(m.values());
	}

	public Lp positions() {
		Lp l = new Lp();
		for (PS ps : this)
			l.add(ps.p);
		return l;
	}

	public ArrayList<String> strings() {
		ArrayList<String> l = new ArrayList<>();
		for (PS ps : this)
			l.add(ps.s);
		return l;
	}

	public Lps filtered(Predicate<PS> f) {
		Lps l = new Lps();
		for (PS ps: this) if (f.test(ps)) l.add(ps);
		return l;
	}

	public Lps mapped(Function<PS, PS> f) {
		Lps l = new Lps();
		for (PS ps: this) l.add(f.apply(ps));
		return l;
	}

	public Lps sortedAlpha() {
		Lps l = new Lps(this);
		l.sort((p1, p2) -> p1.s.compareTo(p2.s));
		return l;
	}

	public Lps sortedByPos() {
		Lps l = new Lps(this);
		l.sort((p1, p2) -> p1.p.compareTo(p2.p));
		return l;
	}

	public Lps reversed() {
		Lps l = new Lps();
		int max = size() - 1;
		for (int i = 0; i <= max; i++) l.add(get(max - i));
		return l;
	}

	public Lps shuffled() {
		Lps l = new Lps(this);
		Collections.shuffle(l);
		return l;
	}

	public Lps copy() {
		return new Lps(this);
	}

	public PS first() {
		return get(0);
	}

	public PS last() {
		return get(size() - 1);
	}

	public PS minPos() {
		PS min = null;
		for (PS ps : this) if (min == null || ps.p.compareTo(min.p) < 0) min = ps;
		return min;
	}

	public PS maxPos() {
		PS max = null;
		for (PS ps : this) if (max == null || ps.p.compareTo(max.p) > 0) max = ps;
		return max;
	}

	public PS[] array() {
		PS[] t = new PS[size()];
		for (int i = 0; i < t.length; i++) t[i] = get(i);
		return t;
	}

	public void debug() {
		System.err.println(this);
	}

	public PS find(Pos p) {
		for (int i = 0; i < size(); i++) if (get(i).p.equals(p)) return get(i);
		return null;
	}

	public PS findOrCreate(Pos p, String s) {
		for (int i = 0; i < size(); i++) if (get(i).p.equals(p)) return get(i);
		PS ps = new PS(p, s);
		add(ps);
		return ps;
	}

	public Lps addPS(Pos p, String s) {
		add(new PS(p, s));
		return this;
	}
}
