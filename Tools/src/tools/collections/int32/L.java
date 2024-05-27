package tools.collections.int32;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;

import tools.function.BiIntConsumer;
import tools.function.BiIntPredicate;
import tools.tuple.II;

@SuppressWarnings("serial")
public class L extends ArrayList<Integer> {
	
	public L() { super(); }
	public L(Iterable<Integer> it) { for (int i: it) add(i); }
	public L(int[] t) { for (int i: t) add(i); }
	public L(byte[] t) { for (int i: t) add(i); }
	public L(char[] t) { for (int i: t) add(i); }
	public L(String s) { for (String e: s.split("[^-\\d]+")) if (!e.isEmpty()) try { add(Integer.parseInt(e)); } catch (NumberFormatException ex) {} }
	public L(int n, IntUnaryOperator o) { for (int i = 0; i < n; i++) add(o.applyAsInt(i)); }
	
	public static L of(int... t) { return new L(t); }

	public static L rangeExc(int n) { return rangeExc(0, n, 1); }
	public static L rangeExc(int s, int n) { return rangeExc(s, n, 1); }
	public static L rangeExc(int s, int n, int step) {
		L l = new L();
		if (step > 0) for (int i = s; i < n; i += step) l.add(i);
		else for (int i = s; i > n; i -= step) l.add(i);
		return l;
	}
	public static L rangeInc(int n) { return rangeInc(0, n, 1); }
	public static L rangeInc(int s, int n) { return rangeInc(s, n, 1); }
	public static L rangeInc(int s, int n, int step) {
		L l = new L();
		if (step > 0) for (int i = s; i <= n; i += step) l.add(i);
		else for (int i = s; i >= n; i -= step) l.add(i);
		return l;
	}
	
	public int g(int i) { return get(Math.floorMod(i, size())); }
	public int gc(int i) { return getC(Math.floorMod(i, size())); }
	
	public L mapped(IntUnaryOperator f) {
		L l = new L();
		for (int i: this) l.add(f.applyAsInt(i));
		return l;
	}

	public void foreach(IntConsumer c) {
		for (int i: this) c.accept(i);
	}

	public void foreach(BiIntConsumer c) {
		for (int i = 0; i < size(); i++) c.accept(i, get(i));
	}
	
	public L subbed(int s) { return subbed(s, size(), 1); }
	public L subbed(int s, int e) { return subbed(s, e, 1); }
	public L subbed(int s, int e, int k) {
		L l = new L();
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
		for (int i: this) {
			if (w) sb.append(s);
			sb.append(i);
			w = true;
		}
		return sb.toString();
	}

	public String joinC() {
		StringBuilder sb = new StringBuilder();
		for (int i: this) sb.append((char) i);
		return sb.toString();
	}

	public L filtered(IntPredicate f) {
		L l = new L();
		for (int i: this) if (f.test(i)) l.add(i);
		return l;
	}
	
	public L filtered(BiIntPredicate f) {
		L l = new L();
		for (int i = 0; i < size(); i++) {
			int v = get(i);
			if (f.test(i, v)) l.add(v);
		}
		return l;
	}

	public L sortedUp() {
		L l = new L(this);
		l.sort(Comparator.naturalOrder());
		return l;
	}

	public L sortedDown() {
		L l = new L(this);
		l.sort(Comparator.reverseOrder());
		return l;
	}

	public L reversed() {
		L l = new L();
		int max = size() - 1;
		for (int i = 0; i <= max; i++) l.add(get(max - i));
		return l;
	}

	public L shuffled() {
		L l = new L(this);
		Collections.shuffle(l);
		return l;
	}

	public L copy() {
		return new L(this);
	}
	
	public int sum() {
		int s = 0;
		for (int i: this) s += i;
		return s; 
	}
	
	public int mul() {
		int p = 1;
		for (int i: this) p *= i;
		return p; 
	}
	
	public long mulLong() {
		long p = 1;
		for (int i: this) p *= i;
		return p; 
	}
	
	public int first() {
		return get(0);
	}

	public int last() {
		return get(size() - 1);
	}
	
	public double mean() {
		return (double) sum() / size();
	}
	
	public double median() {
		L l = sortedUp();
		int n = size();
		if (n % 2 == 1) return l.get(n / 2);
		return (l.get(n / 2 - 1) + l.get(n / 2)) / 2d;
	}

	public char getC(int index) {
		Integer i = get(index);
		if (i == null) return 0;
		return (char) (int) i;
	}
	
	public char firstC() {
		return getC(0);
	}

	public char lastC() {
		return getC(size() - 1);
	}

	public int xor() {
		int x = 0;
		for (int i: this) x ^= i;
		return x;
	}

	public int and() {
		int x = 0;
		for (int i: this) x &= i;
		return x;
	}

	public int or() {
		int x = 0;
		for (int i: this) x |= i;
		return x;
	}

	public int min() {
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < size(); i++) if (get(i) < min) min = get(i);
		return min;
	}
	
	public II minII() {
		II min = new II(-1, Integer.MAX_VALUE);
		for (int i = 0; i < size(); i++) if (get(i) < min.value) {
			min.index = i;
			min.value = get(i);
		}
		return min;
	}

	public int max() {
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < size(); i++) if (get(i) > max) max = get(i);
		return max;
	}

	public II maxII() {
		II max = new II(-1, Integer.MIN_VALUE);
		for (int i = 0; i < size(); i++) if (get(i) > max.value) {
			max.index = i;
			max.value = get(i);
		}
		return max;
	}
	
	public L dec() { return mapped(x -> x-1); } 
	public L inc() { return mapped(x -> x+1); } 

	public int[] array() {
		int[] t = new int[size()];
		for (int i = 0; i < t.length; i++) t[i] = get(i);
		return t;
	}
	
	public int count(int i) {
		int n = 0;
		for (int v: this) if (i == v) n++;
		return n;
	}

	public Map<Integer, Integer> countAll() {
		Map<Integer, Integer> m = new TreeMap<>();
		for (int v: this) {
			Integer i = m.get(v);
			if (i == null) m.put(v, 1);
			else m.put(v, i + 1);
		}
		return m;
	}

	public int count(IntPredicate p) {
		int n = 0;
		for (int v: this) if (p.test(v)) n++;
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

	public void printLnC() {
		System.out.println(joinC());
	}

	public L distinct() {
		return new L(new LinkedHashSet<>(this)); 
	}
	
	public L uppercased() {
		return mapped(x -> x >= 'a' && x <= 'z' ? x - 32 : x); 
	}
	
	public L lowercased() {
		return mapped(x -> x >= 'A' && x <= 'Z' ? x + 32 : x); 
	}
	
	public L replace(int a, int b) {
		for (int i = 0; i < size(); i++) if (get(i) == a) set(i, b);
		return this;
	}
	
	public TreeMap<Integer, Integer> counts() {
		TreeMap<Integer, Integer> m = new TreeMap<>();
		for (Integer l: this) {
			Integer n = m.get(l);
			m.put(l, n == null ? 1 : n + 1);
		}
		return m;
	}
	
	public static L zeros(int z) {
		L l = new L();
		for (int i = 0; i < z; i++) l.add(0);
		return l;
	}
}
