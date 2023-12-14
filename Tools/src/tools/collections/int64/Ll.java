package tools.collections.int64;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;

import tools.function.BiLongConsumer;
import tools.function.IntLongPredicate;
import tools.function.LongToLongFunction;
import tools.math.Num;

@SuppressWarnings("serial")
public class Ll extends ArrayList<Long> {
	
	public Ll() { super(); }
	public Ll(int capacity) { super(capacity); }
	public Ll(Iterable<Long> it) { for (long i: it) add(i); }
	public Ll(long[] t) { for (long i: t) add(i); }
	public Ll(int[] t) { for (int i: t) add(i); }
	public Ll(byte[] t) { for (int i: t) add(i); }
	public Ll(char[] t) { for (int i: t) add(i); }
	public Ll(String s) { for (String e: s.split("[^-\\d]+")) if (!e.isEmpty()) add(Long.parseLong(e)); }
	
	public boolean add(long l) { return super.add(l); }
	
	public static Ll of(long... t) {
		Ll l = new Ll();
		for (long i: t) l.add(i);
		return l;
	}

	public static Ll range(long n) { return range(0, n); }
	public static Ll range(long s, long n) {
		Ll l = new Ll();
		for (long i = s; i < n; i++) l.add(i);
		return l;
	}

	public Ll map(LongToLongFunction f) {
		Ll l = new Ll();
		for (long i: this) l.add(f.applyAsLong(i));
		return l;
	}

	public void foreach(LongConsumer c) {
		for (long i: this) c.accept(i);
	}

	public void foreach(BiLongConsumer c) {
		for (int i = 0; i < size(); i++) c.accept(i, get(i));
	}
	
	public Ll sub(int s) { return sub(s, size(), 1); }
	public Ll sub(int s, int e) { return sub(s, e, 1); }
	public Ll sub(int s, int e, int k) {
		Ll l = new Ll();
		if (s < 0) s += size();
		if (e < 0) e += size();
		for (int i = s; i < e; i += k) l.add(get(i));
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

	public Ll filter(LongPredicate f) {
		Ll l = new Ll();
		for (long i: this) if (f.test(i)) l.add(i);
		return l;
	}
	
	public Ll filter(IntLongPredicate f) {
		Ll l = new Ll();
		for (int i = 0; i < size(); i++) {
			long v = get(i);
			if (f.test(i, v)) l.add(v);
		}
		return l;
	}

	public Ll sortUp() {
		Ll l = new Ll(this);
		l.sort(Comparator.naturalOrder());
		return l;
	}

	public Ll sortDown() {
		Ll l = new Ll(this);
		l.sort(Comparator.reverseOrder());
		return l;
	}

	public Ll reverse() {
		Ll l = new Ll(this);
		int max = size() - 1;
		for (int i = 0; i <= max; i++) l.add(get(max - i));
		return l;
	}

	public Ll shuffle() {
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

	public long gcd() {
		return Num.gcd(array());
	}

	public long lcm() {
		return Num.lcm(array());
	}

	public long min() {
		return Num.min(array());
	}

	public long max() {
		return Num.max(array());
	}

	public long[] array() {
		long[] t = new long[size()];
		for (int i = 0; i < t.length; i++) t[i] = get(i);
		return t;
	}
}
