package tools.collections.float64;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.DoubleUnaryOperator;
import java.util.function.IntToDoubleFunction;

import tools.function.IntDoubleConsumer;
import tools.function.IntDoublePredicate;
import tools.tuple.ID;

@SuppressWarnings("serial")
public class Ld extends ArrayList<Double> {

	public Ld() { super(); }
	public Ld(Iterable<Double> it) { for (double i: it) add(i); }
	public Ld(int[] t) { for (double i: t) add(i); }
	public Ld(long[] t) { for (double i: t) add(i); }
	public Ld(byte[] t) { for (double i: t) add(i); }
	public Ld(char[] t) { for (double i: t) add(i); }
	public Ld(float[] t) { for (double i: t) add(i); }
	public Ld(double[] t) { for (double i: t) add(i); }
	public Ld(String s) { for (String e: s.split("[^-.\\d]+")) if (!e.isEmpty()) add(Double.parseDouble(e)); }
	public Ld(int n, IntToDoubleFunction o) { for (int i = 0; i < n; i++) add(o.applyAsDouble(i)); }
	
	public static Ld of(double... t) { return new Ld(t); }

	public static Ld rangeExc(double n) { return rangeExc(0, n, 1); }
	public static Ld rangeExc(double s, double n) { return rangeExc(s, n, 1); }
	public static Ld rangeExc(double s, double n, double step) {
		Ld l = new Ld();
		if (step > 0) for (double i = s; i < n; i += step) l.add(i);
		else for (double i = s; i > n; i -= step) l.add(i);
		return l;
	}
	public static Ld rangeInc(double n) { return rangeInc(0, n, 1); }
	public static Ld rangeInc(double s, double n) { return rangeInc(s, n, 1); }
	public static Ld rangeInc(double s, double n, double step) {
		Ld l = new Ld();
		if (step > 0) for (double i = s; i <= n; i += step) l.add(i);
		else for (double i = s; i >= n; i -= step) l.add(i);
		return l;
	}
	
	public double g(int i) { return get(i); }
	
	public Ld mapped(DoubleUnaryOperator f) {
		Ld l = new Ld();
		for (double i: this) l.add(f.applyAsDouble(i));
		return l;
	}

	public void foreach(DoubleConsumer c) {
		for (double i: this) c.accept(i);
	}

	public void foreach(IntDoubleConsumer c) {
		for (int i = 0; i < size(); i++) c.accept(i, get(i));
	}
	
	public Ld subbed(int s) { return subbed(s, size(), 1); }
	public Ld subbed(int s, int e) { return subbed(s, e, 1); }
	public Ld subbed(int s, int e, int k) {
		Ld l = new Ld();
		while (s < 0) s += size();
		while (e < 0) e += size();
		if (k > 0) for (int i = s; i < e; i += k) l.add(get(i));
		else for (int i = e - 1; i >= s; i += k) l.add(get(i));
		return l;
	}

	public String join() { return join(" "); };
	public String join(String s) {
		StringBuilder sb = new StringBuilder();
		boolean w = false;
		for (double d: this) {
			if (w) sb.append(s);
			sb.append(d);
			w = true;
		}
		return sb.toString();
	}

	public Ld filtered(DoublePredicate f) {
		Ld l = new Ld();
		for (double d: this) if (f.test(d)) l.add(d);
		return l;
	}
	
	public Ld filtered(IntDoublePredicate f) {
		Ld l = new Ld();
		for (int i = 0; i < size(); i++) {
			double v = get(i);
			if (f.test(i, v)) l.add(v);
		}
		return l;
	}

	public Ld sortedUp() {
		Ld l = new Ld(this);
		l.sort(Comparator.naturalOrder());
		return l;
	}

	public Ld sortedDown() {
		Ld l = new Ld(this);
		l.sort(Comparator.reverseOrder());
		return l;
	}

	public Ld reversed() {
		Ld l = new Ld();
		int max = size() - 1;
		for (int i = 0; i <= max; i++) l.add(get(max - i));
		return l;
	}

	public Ld shuffled() {
		Ld l = new Ld(this);
		Collections.shuffle(l);
		return l;
	}

	public Ld copy() {
		return new Ld(this);
	}
	
	public double sum() {
		double s = 0;
		for (double d: this) s += d;
		return s; 
	}
	
	public double mul() {
		double p = 1;
		for (double d: this) p *= d;
		return p; 
	}

	public double min() {
		double min = Double.POSITIVE_INFINITY;
		for (int i = 0; i < size(); i++) if (get(i) < min) min = get(i);
		return min;
	}
	
	public ID minID() {
		ID min = new ID(-1, Double.POSITIVE_INFINITY);
		for (int i = 0; i < size(); i++) if (get(i) < min.value) {
			min.index = i;
			min.value = get(i);
		}
		return min;
	}

	public double max() {
		double max = Double.NEGATIVE_INFINITY;
		for (int i = 0; i < size(); i++) if (get(i) > max) max = get(i);
		return max;
	}

	public ID maxID() {
		ID max = new ID(-1, Double.NEGATIVE_INFINITY);
		for (int i = 0; i < size(); i++) if (get(i) > max.value) {
			max.index = i;
			max.value = get(i);
		}
		return max;
	}

	public double[] array() {
		double[] t = new double[size()];
		for (int i = 0; i < t.length; i++) t[i] = get(i);
		return t;
	}
	
	public int count(double d) {
		int n = 0;
		for (double v: this) if (d == v) n++;
		return n;
	}
	
	public int count(DoublePredicate p) {
		int n = 0;
		for (double v: this) if (p.test(v)) n++;
		return n;
	}
	
	public void debug() {
		System.err.println(this);
	}
	
	public Ld distinct() {
		return new Ld(new LinkedHashSet<>(this)); 
	}
	
	public double first() { return get(0); }
	
	public double last() { return get(size() - 1); }
	
	public double mean() { return sum() / size(); }
	
	public double median() {
		int n = size();
		if (n % 2 == 0) return (get(n / 2 - 1) + get(n / 2)) / 2;
		return get(n / 2);
	}
}

