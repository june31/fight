package tools.collections.multi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import tools.collections.float64.Ld;
import tools.collections.string.Ls;
import tools.tuple.DS;

@SuppressWarnings("serial")
public class Lds extends ArrayList<DS> {
	public Lds() { }
	public Lds(Iterable<DS> it) { for (DS DS: it) add(DS); }
	public Lds(DS[] t) { for (DS DS: t) add(DS); }
	
	public Ld doubles() {
		Ld l = new Ld();
		for (DS DS : this)
			l.add(DS.d);
		return l;
	}

	public Ls strings() {
		Ls l = new Ls();
		for (DS DS : this)
			l.add(DS.s);
		return l;
	}
	
	public Lds filtered(Predicate<DS> f) {
		Lds l = new Lds();
		for (DS DS: this) if (f.test(DS)) l.add(DS);
		return l;
	}

	public Lds mapped(Function<DS, DS> f) {
		Lds l = new Lds();
		for (DS is: this) l.add(f.apply(is));
		return l;
	}

	public Lds sortedAlpha() {
		Lds l = new Lds(this);
		l.sort((s1, s2) -> s1.s.compareTo(s2.s));
		return l;
	}

	public Lds sortedUp() {
		Lds l = new Lds(this);
		l.sort((s1, s2) -> Double.compare(s1.d, s2.d));
		return l;
	}

	public Lds sortedDown() {
		Lds l = new Lds(this);
		l.sort((s1, s2) -> Double.compare(s2.d, s1.d));
		return l;
	}

	public Lds reversed() {
		Lds l = new Lds();
		int max = size() - 1;
		for (int i = 0; i <= max; i++) l.add(get(max - i));
		return l;
	}

	public Lds shuffled() {
		Lds l = new Lds(this);
		Collections.shuffle(l);
		return l;
	}

	public Lds copy() {
		return new Lds(this);
	}
	
	public double sum() {
		double s = 0;
		for (DS DS: this) s += DS.d;
		return s;
	}
	
	public double mul() {
		double p = 1;
		for (DS DS: this) p *= DS.d;
		return p; 
	}
	
	public DS first() {
		return get(0);
	}

	public DS last() {
		return get(size() - 1);
	}
	
	public double mean() {
		return (double) sum() / size();
	}
	
	public double median() {
		Lds l = sortedUp();
		int n = size();
		if (n % 2 == 1) return l.get(n / 2).d;
		return (l.get(n / 2 - 1).d + l.get(n / 2).d) / 2d;
	}

	public DS min() {
		DS min = new DS(Integer.MAX_VALUE, null);
		for (DS DS : this) if (DS.d < min.d) min = DS;
		return min;
	}

	public DS max() {
		DS max = new DS(Integer.MIN_VALUE, null);
		for (DS DS : this) if (DS.d > max.d) max = DS;
		return max;
	}

	public DS[] array() {
		DS[] t = new DS[size()];
		for (int i = 0; i < t.length; i++) t[i] = get(i);
		return t;
	}

	public Lds added() {
		Map<String, DS> m = new LinkedHashMap<>();
		for (DS DS : this) {
			DS s = m.get(DS.s);
			if (s == null) m.put(DS.s, DS);
			else s.d += DS.d;
		}
		return new Lds(m.values());
	}
	
	public void debug() {
		System.err.println(this);
	}
	
	public Lds addDS(double d, String s) {
		add(new DS(d, s));
		return this;
	}
}
