package tools.collections.multi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import tools.collections.int32.L;
import tools.collections.string.Ls;
import tools.tuple.SI;

@SuppressWarnings("serial")
public class Lsi extends ArrayList<SI> {
	
	public Lsi() { }
	public Lsi(Iterable<SI> it) { for (SI si: it) add(si); }
	public Lsi(SI[] t) { for (SI si: t) add(si); }
	
	public static Lsi of(Iterable<String> it) {
		Map<String, SI> m = new LinkedHashMap<>();
		for (String s : it) {
			SI si = m.get(s);
			if (si == null) m.put(s, new SI(s, 1));
			else si.i++;
		}
		return new Lsi(m.values()); 
	}		

	public L integers() {
		L l = new L();
		for (SI si : this)
			l.add(si.i);
		return l;
	}

	public Ls strings() {
		Ls l = new Ls();
		for (SI si : this)
			l.add(si.s);
		return l;
	}
	
	public Lsi filtered(Predicate<SI> f) {
		Lsi l = new Lsi();
		for (SI si: this) if (f.test(si)) l.add(si);
		return l;
	}

	public Lsi mapped(Function<SI, SI> f) {
		Lsi l = new Lsi();
		for (SI si: this) l.add(f.apply(si));
		return l;
	}

	public Lsi sortedAlpha() {
		Lsi l = new Lsi(this);
		l.sort((s1, s2) -> s1.s.compareTo(s2.s));
		return l;
	}

	public Lsi sortedUp() {
		Lsi l = new Lsi(this);
		l.sort((s1, s2) -> s1.i - s2.i);
		return l;
	}

	public Lsi sortedDown() {
		Lsi l = new Lsi(this);
		l.sort((s1, s2) -> s2.i - s1.i);
		return l;
	}

	public Lsi reversed() {
		Lsi l = new Lsi();
		int max = size() - 1;
		for (int i = 0; i <= max; i++) l.add(get(max - i));
		return l;
	}

	public Lsi shuffled() {
		Lsi l = new Lsi(this);
		Collections.shuffle(l);
		return l;
	}

	public Lsi copy() {
		return new Lsi(this);
	}
	
	public int sum() {
		int s = 0;
		for (SI si: this) s += si.i;
		return s;
	}
	
	public int mul() {
		int p = 1;
		for (SI si: this) p *= si.i;
		return p; 
	}
	
	public long mulLong() {
		long p = 1;
		for (SI si: this) p *= si.i;
		return p; 
	}
	
	public SI first() {
		return get(0);
	}

	public SI last() {
		return get(size() - 1);
	}
	
	public double mean() {
		return (double) sum() / size();
	}
	
	public double median() {
		Lsi l = sortedUp();
		int n = size();
		if (n % 2 == 1) return l.get(n / 2).i;
		return (l.get(n / 2 - 1).i + l.get(n / 2).i) / 2d;
	}

	public SI min() {
		SI min = new SI(null, Integer.MAX_VALUE);
		for (SI si : this) if (si.i < min.i) min = si;
		return min;
	}

	public SI max() {
		SI max = new SI(null, Integer.MIN_VALUE);
		for (SI si : this) if (si.i > max.i) max = si;
		return max;
	}

	public SI[] array() {
		SI[] t = new SI[size()];
		for (int i = 0; i < t.length; i++) t[i] = get(i);
		return t;
	}

	public Lsi added() {
		Map<String, SI> m = new LinkedHashMap<>();
		for (SI si : this) {
			SI s = m.get(si.s);
			if (s == null) m.put(si.s, si);
			else s.i += si.i;
		}
		return new Lsi(m.values());
	}
	
	public void debug() {
		System.err.println(this);
	}
	
	public SI find(String s) {
		for (int i = 0; i < size(); i++) if (get(i).s.equals(s)) return get(i);
		return null;
	}
	
	public SI findOrCreate(String s) {
		for (int i = 0; i < size(); i++) if (get(i).s.equals(s)) return get(i);
		SI si = new SI(s, 0);
		add(si);
		return si;
	}
}
