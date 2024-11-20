package tools.collections.multi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import tools.collections.float64.Ld;
import tools.collections.string.Ls;
import tools.tuple.SD;

@SuppressWarnings("serial")
public class Lsd extends ArrayList<SD> {
	public Lsd() { }
	public Lsd(Iterable<SD> it) { for (SD SD: it) add(SD); }
	public Lsd(SD[] t) { for (SD SD: t) add(SD); }
	
	public Ld doubles() {
		Ld l = new Ld();
		for (SD SD : this)
			l.add(SD.d);
		return l;
	}

	public Ls strings() {
		Ls l = new Ls();
		for (SD SD : this)
			l.add(SD.s);
		return l;
	}
	
	public Lsd filtered(Predicate<SD> f) {
		Lsd l = new Lsd();
		for (SD SD: this) if (f.test(SD)) l.add(SD);
		return l;
	}

	public Lsd mapped(Function<SD, SD> f) {
		Lsd l = new Lsd();
		for (SD is: this) l.add(f.apply(is));
		return l;
	}

	public Lsd sortedAlpha() {
		Lsd l = new Lsd(this);
		l.sort((s1, s2) -> s1.s.compareTo(s2.s));
		return l;
	}

	public Lsd sortedUp() {
		Lsd l = new Lsd(this);
		l.sort((s1, s2) -> Double.compare(s1.d, s2.d));
		return l;
	}

	public Lsd sortedDown() {
		Lsd l = new Lsd(this);
		l.sort((s1, s2) -> Double.compare(s2.d, s1.d));
		return l;
	}

	public Lsd reversed() {
		Lsd l = new Lsd();
		int max = size() - 1;
		for (int i = 0; i <= max; i++) l.add(get(max - i));
		return l;
	}

	public Lsd shuffled() {
		Lsd l = new Lsd(this);
		Collections.shuffle(l);
		return l;
	}

	public Lsd copy() {
		return new Lsd(this);
	}
	
	public double sum() {
		double s = 0;
		for (SD SD: this) s += SD.d;
		return s;
	}
	
	public double mul() {
		double p = 1;
		for (SD SD: this) p *= SD.d;
		return p; 
	}
	
	public SD first() {
		return get(0);
	}

	public SD last() {
		return get(size() - 1);
	}
	
	public double mean() {
		return (double) sum() / size();
	}
	
	public double median() {
		Lsd l = sortedUp();
		int n = size();
		if (n % 2 == 1) return l.get(n / 2).d;
		return (l.get(n / 2 - 1).d + l.get(n / 2).d) / 2d;
	}

	public SD min() {
		SD min = new SD(null, Integer.MAX_VALUE);
		for (SD SD : this) if (SD.d < min.d) min = SD;
		return min;
	}

	public SD max() {
		SD max = new SD(null, Integer.MIN_VALUE);
		for (SD SD : this) if (SD.d > max.d) max = SD;
		return max;
	}

	public SD[] array() {
		SD[] t = new SD[size()];
		for (int i = 0; i < t.length; i++) t[i] = get(i);
		return t;
	}

	public Lsd added() {
		Map<String, SD> m = new LinkedHashMap<>();
		for (SD SD : this) {
			SD s = m.get(SD.s);
			if (s == null) m.put(SD.s, SD);
			else s.d += SD.d;
		}
		return new Lsd(m.values());
	}
	
	public void debug() {
		System.err.println(this);
	}
	
	public Lsd addSD(String s, double d) {
		add(new SD(s, d));
		return this;
	}
}
