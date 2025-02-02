package tools.collections.multi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import tools.collections.int32.L;
import tools.collections.pos.Lp;
import tools.tuple.PI;
import tools.tuple.Pos;

@SuppressWarnings("serial")
public class Lpi extends ArrayList<PI> {
	public Lpi() { }
	public Lpi(Iterable<PI> it) { for (PI PI: it) add(PI); }
	public Lpi(PI[] t) { for (PI PI: t) add(PI); }
	
	public static Lpi of(Iterable<Pos> it) {
		Map<Pos, PI> m = new LinkedHashMap<>();
		for (Pos p : it) {
			PI PI = m.get(p);
			if (PI == null) m.put(p, new PI(p, 1));
			else PI.i++;
		}
		return new Lpi(m.values()); 
	}		

	public L integers() {
		L l = new L();
		for (PI PI : this)
			l.add(PI.i);
		return l;
	}

	public Lp Positions() {
		Lp l = new Lp();
		for (PI PI : this)
			l.add(PI.p);
		return l;
	}
	
	public Lpi filtered(Predicate<PI> f) {
		Lpi l = new Lpi();
		for (PI PI: this) if (f.test(PI)) l.add(PI);
		return l;
	}

	public Lpi mapped(Function<PI, PI> f) {
		Lpi l = new Lpi();
		for (PI is: this) l.add(f.apply(is));
		return l;
	}

	public Lpi sortedUp() {
		Lpi l = new Lpi(this);
		l.sort((s1, s2) -> s1.i - s2.i);
		return l;
	}

	public Lpi sortedDown() {
		Lpi l = new Lpi(this);
		l.sort((s1, s2) -> s2.i - s1.i);
		return l;
	}

	public Lpi reversed() {
		Lpi l = new Lpi();
		int max = size() - 1;
		for (int i = 0; i <= max; i++) l.add(get(max - i));
		return l;
	}

	public Lpi shuffled() {
		Lpi l = new Lpi(this);
		Collections.shuffle(l);
		return l;
	}

	public Lpi copy() {
		return new Lpi(this);
	}
	
	public int sum() {
		int s = 0;
		for (PI PI: this) s += PI.i;
		return s;
	}
	
	public int mul() {
		int p = 1;
		for (PI PI: this) p *= PI.i;
		return p; 
	}
	
	public long mulLong() {
		long p = 1;
		for (PI PI: this) p *= PI.i;
		return p; 
	}
	
	public PI first() {
		return get(0);
	}

	public PI last() {
		return get(size() - 1);
	}
	
	public double mean() {
		return (double) sum() / size();
	}
	
	public double median() {
		Lpi l = sortedUp();
		int n = size();
		if (n % 2 == 1) return l.get(n / 2).i;
		return (l.get(n / 2 - 1).i + l.get(n / 2).i) / 2d;
	}

	public PI min() {
		PI min = new PI(null, Integer.MAX_VALUE);
		for (PI PI : this) if (PI.i < min.i) min = PI;
		return min;
	}

	public PI max() {
		PI max = new PI(null, Integer.MIN_VALUE);
		for (PI PI : this) if (PI.i > max.i) max = PI;
		return max;
	}

	public PI[] array() {
		PI[] t = new PI[size()];
		for (int i = 0; i < t.length; i++) t[i] = get(i);
		return t;
	}
	public void debug() {
		System.err.println(this);
	}
	
	public Lpi addPI(int i, Pos p) {
		add(new PI(p, i));
		return this;
	}
}
