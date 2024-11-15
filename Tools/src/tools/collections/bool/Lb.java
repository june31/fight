package tools.collections.bool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;

@SuppressWarnings("serial")
public class Lb extends ArrayList<Boolean> {
	public static List<Character> TRUE_CHARS = List.of((char) 1, '1', 'X', 'Y', 'x', 'y', '#', '+', '*'); 
	
	public Lb() { super(); }
	public Lb(Iterable<Boolean> it) { for (boolean b: it) add(b); }
	public Lb(int x, int numberOfBits) { for (int i = 1; i <= numberOfBits; i++) add((x >> (numberOfBits - i) & 1) == 1); }
	public Lb(long x, int numberOfBits) { for (int i = 1; i <= numberOfBits; i++) add((x >> (numberOfBits - i) & 1) == 1); }
	public Lb(boolean[] t) { for (boolean b: t) add(b); }
	public Lb(String s) { for (char c: s.toCharArray()) add(TRUE_CHARS.contains(c)); }
	public Lb(String s, char t) { for (char c: s.toCharArray()) add(c == t); }
	public Lb(int n, IntPredicate p) { for (int i = 0; i < n; i++) add(p.test(i)); }
	
	public static Lb of(boolean... bs) { return new Lb(bs); } 

	public boolean[] array() {
		boolean[] t = new boolean[size()];
		for (int i = 0; i < t.length; i++) t[i] = get(i);
		return t;
	}

	public int asInt() {
		int i = 0;
		for (boolean b: this) {
			i <<= 1;
			if (b) i |= 1;
		}
		return i;
	}

	public long asLong() {
		long i = 0;
		for (boolean b: this) {
			i <<= 1;
			if (b) i |= 1;
		}
		return i;
	}

	@Override
	public String toString() {
		return toString('1', '0');
	}

	public String toString(char t, char f) {
		StringBuilder sb = new StringBuilder();
		for (boolean b: this) sb.append(b ? t : f);
		return sb.toString();
	}

	public static boolean[][] to2DArray(List<Lb> lb) {
		int ln = lb.size();
		int cn = lb.get(0).size();
		boolean[][] res = new boolean[ln][cn];
		for (int l = 0; l < ln; l++) for (int c = 0; c < cn; c++) res[l][c] = lb.get(l).get(c);
		return res;
	}
	
	public static List<Lb> from2DArray(boolean[][] bs) {
		int ln = bs.length;
		int cn = bs[0].length;
		List<Lb> res = new ArrayList<>();
		for (int l = 0; l < ln; l++) {
			Lb lb = new Lb();
			for (int c = 0; c < cn; c++) lb.add(bs[l][c]);
			res.add(lb);
		}
		return res;
	}

	public int bitCount() { return countTrue(); } // Synonym
	public int countTrue() {
		int count = 0;
		for (boolean b: this) if (b) count++;
		return count;
	}
	
	public int countFalse() {
		int count = 0;
		for (boolean b: this) if (!b) count++;
		return count;
	}

	public Lb negated() {
		Lb inv = new Lb();
		for (boolean b: this) inv.add(!b);
		return inv;
	}

	public Lb reversed() {
		Lb lb = new Lb(this);
		Collections.reverse(lb);
		return lb;
	}

	public boolean or() {
		boolean r = false;
		for (boolean b: this) r |= b;
		return r;
	}
	
	public boolean and() {
		boolean r = true;
		for (boolean b: this) r &= b;
		return r;
	}

	public boolean xor() {
		boolean r = false;
		for (boolean b: this) r ^= b;
		return r;
	}
	
	public static Lb xor(Lb lb1, Lb lb2) {
		Lb xor = new Lb();
		for (int i = 0; i < lb1.size(); i++) xor.add(lb1.get(i) ^ lb2.get(i));
		return xor;
	}

	public Lb subbedFrom(int start) {
		while (start < 0) start += size();
		Lb sub = new Lb();
		for (int i = start; i < size(); i++) sub.add(get(i));
		return sub;
	}

	public Lb subbedTo(int end) {
		while (end < 0) end += size();
		Lb sub = new Lb();
		for (int i = 0; i < end; i++) sub.add(get(i));
		return sub;
	}

	public Lb subbed(int start, int end) {
		while (start < 0) end += size();
		while (end < 0) end += size();
		Lb sub = new Lb();
		for (int i = start; i < end; i++) sub.add(get(i));
		return sub;
	}
	
	public Lb subbed(int start, int end, int step) {
		while (start < 0) end += size();
		while (end < 0) end += size();
		Lb sub = new Lb();
		if (step > 0) for (int i = start; i < end; i += step) sub.add(get(i));
		else for (int i = end - 1; i >= start; i += step) sub.add(get(i));
		return sub;
	}
	
	public static Lb create(int n, IntFunction<Boolean> f) {
		Lb lb = new Lb();
		for (int i = 0; i < n; i++) lb.add(f.apply(i));
		return lb;
	}
	
	public void debug() {
		System.err.println(this);
	}
	
	public Lb distinct() {
		return new Lb(new LinkedHashSet<>(this)); 
	}
	
	public Object first() {
		return get(0);
	}
	
	public Object last() {
		return get(size() - 1);
	}

}
