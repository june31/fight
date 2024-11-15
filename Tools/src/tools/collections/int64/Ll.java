package tools.collections.int64;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.IntToLongFunction;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
import java.util.function.LongUnaryOperator;

import tools.function.IntLongConsumer;
import tools.function.IntLongPredicate;
import tools.tuple.IL;

@SuppressWarnings("serial")
public class Ll extends ArrayList<Long> {
	
	public Ll() { super(); }
	public Ll(Iterable<Long> it) { for (long i: it) add(i); }
	public Ll(long[] t) { for (long i: t) add(i); }
	public Ll(int[] t) { for (int i: t) add(i); }
	public Ll(byte[] t) { for (int i: t) add(i); }
	public Ll(char[] t) { for (int i: t) add(i); }
	public Ll(String s) { for (String e: s.split("[^-\\d]+")) if (!e.isEmpty()) add(Long.parseLong(e)); }
	public Ll(int n, IntToLongFunction o) { for (int i = 0; i < n; i++) add(o.applyAsLong(i)); }
	
	public boolean add(long l) { return super.add(l); }
	
	public static Ll of(long... t) { return new Ll(t); }

	public static Ll rangeExc(int n) { return rangeExc(0, n, 1); }
	public static Ll rangeExc(int s, int n) { return rangeExc(s, n, 1); }
	public static Ll rangeExc(int s, int n, int step) {
		Ll l = new Ll();
		if (step > 0) for (int i = s; i < n; i += step) l.add(i);
		else for (int i = s; i > n; i -= step) l.add(i);
		return l;
	}
	public static Ll rangeInc(int n) { return rangeInc(0, n, 1); }
	public static Ll rangeInc(int s, int n) { return rangeInc(s, n, 1); }
	public static Ll rangeInc(int s, int n, int step) {
		Ll l = new Ll();
		if (step > 0) for (int i = s; i <= n; i += step) l.add(i);
		else for (int i = s; i >= n; i -= step) l.add(i);
		return l;
	}

	public long g(int i) { return get(i); }

	public Ll mapped(LongUnaryOperator f) {
		Ll l = new Ll();
		for (long i: this) l.add(f.applyAsLong(i));
		return l;
	}

	public void foreach(LongConsumer c) {
		for (long i: this) c.accept(i);
	}

	public void foreach(IntLongConsumer c) {
		for (int i = 0; i < size(); i++) c.accept(i, get(i));
	}

	public Ll shifted0() {
		Ll l = new Ll();
		l.add(0);
		l.addAll(this);
		return l;
	}

	public Ll subbedFrom(int start) { return subbed(start, size(), 1); }
	public Ll subbedTo(int end) { return subbed(0, end, 1); }
	public Ll subbed(int s, int e) { return subbed(s, e, 1); }
	public Ll subbed(int s, int e, int k) {
		Ll l = new Ll();
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
		for (long i: this) {
			if (w) sb.append(s);
			sb.append(i);
			w = true;
		}
		return sb.toString();
	}

	public String joinC() {
		StringBuilder sb = new StringBuilder();
		for (long i: this) sb.append((char) i);
		return sb.toString();
	}

	public Ll filtered(LongPredicate f) {
		Ll l = new Ll();
		for (long i: this) if (f.test(i)) l.add(i);
		return l;
	}
	
	public Ll filtered(IntLongPredicate f) {
		Ll l = new Ll();
		for (int i = 0; i < size(); i++) {
			long v = get(i);
			if (f.test(i, v)) l.add(v);
		}
		return l;
	}

	public Ll sortedUp() {
		Ll l = new Ll(this);
		l.sort(Comparator.naturalOrder());
		return l;
	}

	public Ll sortedDown() {
		Ll l = new Ll(this);
		l.sort(Comparator.reverseOrder());
		return l;
	}

	public Ll reversed() {
		Ll l = new Ll();
		int max = size() - 1;
		for (int i = 0; i <= max; i++) l.add(get(max - i));
		return l;
	}

	public Ll shuffled() {
		Ll l = new Ll(this);
		Collections.shuffle(l);
		return l;
	}

	public Ll copy() {
		return new Ll(this);
	}
	
	public long sum() {
		long s = 0;
		for (long i: this) s += i;
		return s; 
	}
	
	public long mul() {
		long p = 1;
		for (long i: this) p *= i;
		return p; 
	}

	public long xor() {
		long x = 0;
		for (long i: this) x ^= i;
		return x;
	}

	public long and() {
		long x = 0;
		for (long i: this) x &= i;
		return x;
	}

	public long or() {
		long x = 0;
		for (long i: this) x |= i;
		return x;
	}

	public long min() {
		long min = Long.MAX_VALUE;
		for (int i = 0; i < size(); i++) if (get(i) < min) min = get(i);
		return min;
	}
	
	public IL minIL() {
		IL min = new IL(-1, Long.MAX_VALUE);
		for (int i = 0; i < size(); i++) if (get(i) < min.value) {
			min.index = i;
			min.value = get(i);
		}
		return min;
	}

	public long max() {
		long max = Long.MIN_VALUE;
		for (int i = 0; i < size(); i++) if (get(i) > max) max = get(i);
		return max;
	}

	public IL maxIL() {
		IL max = new IL(-1, Long.MIN_VALUE);
		for (int i = 0; i < size(); i++) if (get(i) > max.value) {
			max.index = i;
			max.value = get(i);
		}
		return max;
	}

	public long[] array() {
		long[] t = new long[size()];
		for (int i = 0; i < t.length; i++) t[i] = get(i);
		return t;
	}
	
	public int count(long l) {
		int n = 0;
		for (long v: this) if (l == v) n++;
		return n;
	}
	
	public Map<Long, Integer> countAll() {
		Map<Long, Integer> m = new TreeMap<>();
		for (long v: this) {
			Integer i = m.get(v);
			if (i == null) m.put(v, 1);
			else m.put(v, i + 1);
		}
		return m;
	}

	public int count(LongPredicate p) {
		int n = 0;
		for (long v: this) if (p.test(v)) n++;
		return n;
	}
	
	public void debug() {
		System.err.println(this);
	}
	
	public void printLn() {
		System.out.println(join());
	}

	public void printLn(String sep) {
		System.out.println(join(sep));
	}
	
	public Ll distinct() {
		return new Ll(new LinkedHashSet<>(this)); 
	}
	
	public Ll replace(long a, long b) {
		for (int i = 0; i < size(); i++) if (get(i) == a) set(i, b);
		return this;
	}
	
	public TreeMap<Long, Integer> counts() {
		TreeMap<Long, Integer> m = new TreeMap<>();
		for (Long l: this) {
			Integer n = m.get(l);
			m.put(l, n == null ? 1 : n + 1);
		}
		return m;
	}
	
	public long first() { return get(0); }
	
	public long last() { return get(size() - 1); }
	
	public double mean() {
		return (double) sum() / size();
	}
	
	public double median() {
		Ll l = sortedUp();
		int n = size();
		if (n % 2 == 1) return l.get(n / 2);
		return (l.get(n / 2 - 1) + l.get(n / 2)) / 2d;
	}
	
	public static Ll zeros(int n) {
		Ll l = new Ll();
		for (int i = 0; i < n; i++) l.add(0);
		return l;
	}
}

