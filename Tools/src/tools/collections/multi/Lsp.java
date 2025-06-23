package tools.collections.multi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import tools.tuple.SP;
import tools.tuple.Pos;
import tools.collections.pos.Lp;

@SuppressWarnings("serial")
public class Lsp extends ArrayList<SP> {

	public Lsp() { }
	public Lsp(Iterable<SP> it) { for (SP sp: it) add(sp); }
	public Lsp(SP[] t) { for (SP sp: t) add(sp); }

	public static Lsp of(Iterable<String> it, Function<String, Pos> posProvider) {
		Map<String, SP> m = new LinkedHashMap<>();
		for (String s : it) {
			SP sp = m.get(s);
			if (sp == null) m.put(s, new SP(s, posProvider.apply(s)));
		}
		return new Lsp(m.values());
	}

	public Lp positions() {
		Lp l = new Lp();
		for (SP sp : this)
			l.add(sp.p);
		return l;
	}

	public ArrayList<String> strings() {
		ArrayList<String> l = new ArrayList<>();
		for (SP sp : this)
			l.add(sp.s);
		return l;
	}

	public Lsp filtered(Predicate<SP> f) {
		Lsp l = new Lsp();
		for (SP sp: this) if (f.test(sp)) l.add(sp);
		return l;
	}

	public Lsp mapped(Function<SP, SP> f) {
		Lsp l = new Lsp();
		for (SP sp: this) l.add(f.apply(sp));
		return l;
	}

	public Lsp sortedAlpha() {
		Lsp l = new Lsp(this);
		l.sort((s1, s2) -> s1.s.compareTo(s2.s));
		return l;
	}

	public Lsp sortedByPos() {
		Lsp l = new Lsp(this);
		l.sort((s1, s2) -> s1.p.compareTo(s2.p));
		return l;
	}

	public Lsp reversed() {
		Lsp l = new Lsp();
		int max = size() - 1;
		for (int i = 0; i <= max; i++) l.add(get(max - i));
		return l;
	}

	public Lsp shuffled() {
		Lsp l = new Lsp(this);
		Collections.shuffle(l);
		return l;
	}

	public Lsp copy() {
		return new Lsp(this);
	}

	public SP first() {
		return get(0);
	}

	public SP last() {
		return get(size() - 1);
	}

	public SP minPos() {
		SP min = null;
		for (SP sp : this) if (min == null || sp.p.compareTo(min.p) < 0) min = sp;
		return min;
	}

	public SP maxPos() {
		SP max = null;
		for (SP sp : this) if (max == null || sp.p.compareTo(max.p) > 0) max = sp;
		return max;
	}

	public SP[] array() {
		SP[] t = new SP[size()];
		for (int i = 0; i < t.length; i++) t[i] = get(i);
		return t;
	}

	public void debug() {
		System.err.println(this);
	}

	public SP find(String s) {
		for (int i = 0; i < size(); i++) if (get(i).s.equals(s)) return get(i);
		return null;
	}

	public SP findOrCreate(String s, Pos p) {
		for (int i = 0; i < size(); i++) if (get(i).s.equals(s)) return get(i);
		SP sp = new SP(s, p);
		add(sp);
		return sp;
	}

	public Lsp addSP(String s, Pos p) {
		add(new SP(s, p));
		return this;
	}
}
