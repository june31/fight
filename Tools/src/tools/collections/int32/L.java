package tools.collections.int32;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.IntConsumer;

import tools.function.BiIntConsumer;
import tools.function.IntIntToBooleanFunction;
import tools.function.IntToBooleanFunction;
import tools.function.IntToIntFunction;
import tools.math.Num;

@SuppressWarnings("serial")
public class L extends ArrayList<Integer> {
	
	public L() { super(); }
	public L(int capacity) { super(capacity); }
	public L(Iterable<Integer> it) { for (int i: it) add(i); }
	public L(int[] t) { for (int i: t) add(i); }
	public L(byte[] t) { for (int i: t) add(i); }
	public L(char[] t) { for (int i: t) add(i); }
	public L(String s) { for (String e: s.split("[^-\\d]+")) add(Integer.parseInt(e)); }
	
	public static L of(int... t) {
		L l = new L();
		for (int i: t) l.add(i);
		return l;
	}

	public static L range(int n) { return range(0, n); }
	public static L range(int s, int n) {
		L l = new L();
		for (int i = s; i < n; i++) l.add(i);
		return l;
	}

	public L map(IntToIntFunction f) {
		L l = new L();
		for (int i: this) l.add(f.applyAsInt(i));
		return l;
	}

	public void foreach(IntConsumer c) {
		for (int i: this) c.accept(get(i));
	}

	public void foreach(BiIntConsumer c) {
		for (int i: this) c.accept(i, get(i));
	}
	
	public L sub(int s) { return sub(s, size()); }
	public L sub(int s, int e) {
		L l = new L();
		if (s < 0) s += size();
		if (e < 0) e += size();
		for (int i = s; i < e; i++) l.add(get(i));
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

	public L filter(IntToBooleanFunction f) {
		L l = new L();
		for (int i: this) if (f.applyAsBoolean(i)) l.add(i);
		return l;
	}
	
	public L filter(IntIntToBooleanFunction f) {
		L l = new L();
		for (int i = 0; i < size(); i++) if (f.applyAsBoolean(i, get(i))) l.add(i);
		return l;
	}

	public L sortUp() {
		L l = new L(this);
		l.sort(Comparator.naturalOrder());
		return l;
	}

	public L sortDown() {
		L l = new L(this);
		l.sort(Comparator.reverseOrder());
		return l;
	}

	public L reverse() {
		L l = new L(this);
		int max = size() - 1;
		for (int i = 0; i <= max; i++) l.add(get(max - i));
		return l;
	}

	public L shuffle() {
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

	public int gcd() {
		return Num.gcd(array());
	}

	public int lcm() {
		return Num.lcm(array());
	}

	public int min() {
		return Num.min(array());
	}

	public int max() {
		return Num.max(array());
	}

	public int[] array() {
		int[] t = new int[size()];
		for (int i = 0; i < t.length; i++) t[i] = get(i);
		return t;
	}
}
