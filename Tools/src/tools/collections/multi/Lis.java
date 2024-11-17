package tools.collections.multi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import tools.collections.int32.L;
import tools.collections.string.Ls;
import tools.tuple.IS;

@SuppressWarnings("serial")
public class Lis extends ArrayList<IS> {
	public Lis() { }
	public Lis(Iterable<IS> it) { for (IS IS: it) add(IS); }
	public Lis(IS[] t) { for (IS IS: t) add(IS); }
	
	public static Lis of(Iterable<String> it) {
		Map<String, IS> m = new LinkedHashMap<>();
		for (String s : it) {
			IS IS = m.get(s);
			if (IS == null) m.put(s, new IS(1, s));
			else IS.i++;
		}
		return new Lis(m.values()); 
	}		

	public L integers() {
		L l = new L();
		for (IS IS : this)
			l.add(IS.i);
		return l;
	}

	public Ls strings() {
		Ls l = new Ls();
		for (IS IS : this)
			l.add(IS.s);
		return l;
	}
	
	public Lis filtered(Predicate<IS> f) {
		Lis l = new Lis();
		for (IS IS: this) if (f.test(IS)) l.add(IS);
		return l;
	}

	public Lis mapped(Function<IS, IS> f) {
		Lis l = new Lis();
		for (IS is: this) l.add(f.apply(is));
		return l;
	}

	public Lis sortedAlpha() {
		Lis l = new Lis(this);
		l.sort((s1, s2) -> s1.s.compareTo(s2.s));
		return l;
	}

	public Lis sortedUp() {
		Lis l = new Lis(this);
		l.sort((s1, s2) -> s1.i - s2.i);
		return l;
	}

	public Lis sortedDown() {
		Lis l = new Lis(this);
		l.sort((s1, s2) -> s2.i - s1.i);
		return l;
	}

	public Lis reversed() {
		Lis l = new Lis();
		int max = size() - 1;
		for (int i = 0; i <= max; i++) l.add(get(max - i));
		return l;
	}

	public Lis shuffled() {
		Lis l = new Lis(this);
		Collections.shuffle(l);
		return l;
	}

	public Lis copy() {
		return new Lis(this);
	}
	
	public int sum() {
		int s = 0;
		for (IS IS: this) s += IS.i;
		return s;
	}
	
	public int mul() {
		int p = 1;
		for (IS IS: this) p *= IS.i;
		return p; 
	}
	
	public long mulLong() {
		long p = 1;
		for (IS IS: this) p *= IS.i;
		return p; 
	}
	
	public IS first() {
		return get(0);
	}

	public IS last() {
		return get(size() - 1);
	}
	
	public double mean() {
		return (double) sum() / size();
	}
	
	public double median() {
		Lis l = sortedUp();
		int n = size();
		if (n % 2 == 1) return l.get(n / 2).i;
		return (l.get(n / 2 - 1).i + l.get(n / 2).i) / 2d;
	}

	public IS min() {
		IS min = new IS(Integer.MAX_VALUE, null);
		for (IS IS : this) if (IS.i < min.i) min = IS;
		return min;
	}

	public IS max() {
		IS max = new IS(Integer.MIN_VALUE, null);
		for (IS IS : this) if (IS.i > max.i) max = IS;
		return max;
	}

	public IS[] array() {
		IS[] t = new IS[size()];
		for (int i = 0; i < t.length; i++) t[i] = get(i);
		return t;
	}

	public Lis added() {
		Map<String, IS> m = new LinkedHashMap<>();
		for (IS IS : this) {
			IS s = m.get(IS.s);
			if (s == null) m.put(IS.s, IS);
			else s.i += IS.i;
		}
		return new Lis(m.values());
	}
	
	public void debug() {
		System.err.println(this);
	}
	
	public Lis addIS(int i, String s) {
		add(new IS(i, s));
		return this;
	}
}
