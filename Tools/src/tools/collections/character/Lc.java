package tools.collections.character;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

@SuppressWarnings("serial")
public class Lc extends ArrayList<Character> {
	
	public Lc() { }
	public Lc(Iterable<Character> it) { for (char c: it) add(c); }
	public Lc(int[] t) { for (int i: t) add((char) i); }
	public Lc(byte[] t) { for (byte b: t) add((char) b); }
	public Lc(char[] t) { for (char c: t) add(c); }
	public Lc(String s, String regex) { for (String e: s.split(regex)) if (!e.isEmpty()) add(e.charAt(0)); }
	
	public static Lc of(char... t) { return new Lc(t); }
	
	public char g(int i) { return get(Math.floorMod(i, size())); }
	
	public Lc mapped(UnaryOperator<Character> f) {
		Lc l = new Lc();
		for (char c: this) l.add(f.apply(c));
		return l;
	}
	
	public void foreach(Consumer<Character> co) {
		for (char c: this) co.accept(c);
	}

	public Lc subbed(int s) { return subbed(s, size(), 1); }
	public Lc subbed(int s, int e) { return subbed(s, e, 1); }
	public Lc subbed(int s, int e, int k) {
		Lc l = new Lc();
		while (s < 0) s += size();
		while (e < 0) e += size();
		if (k > 0) for (int i = s; i < e; i += k) l.add(get(i));
		else for (int i = e - 1; i >= s; i += k) l.add(get(i));
		return l;
	}

	public String join() { return join(""); };
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

	public Lc filtered(Predicate<Character> f) {
		Lc l = new Lc();
		for (char c: this) if (f.test(c)) l.add(c);
		return l;
	}

	public Lc sortedUp() {
		Lc l = new Lc(this);
		l.sort(Comparator.naturalOrder());
		return l;
	}

	public Lc sortedDown() {
		Lc l = new Lc(this);
		l.sort(Comparator.reverseOrder());
		return l;
	}

	public Lc reversed() {
		Lc l = new Lc();
		int max = size() - 1;
		for (int i = 0; i <= max; i++) l.add(get(max - i));
		return l;
	}

	public Lc shuffled() {
		Lc l = new Lc(this);
		Collections.shuffle(l);
		return l;
	}

	public Lc copy() {
		return new Lc(this);
	}
	

	public int first() {
		return get(0);
	}

	public int last() {
		return get(size() - 1);
	}
	
	public char min() {
		char min = 0;
		for (int i = 0; i < size(); i++) if (get(i) < min) min = get(i);
		return min;
	}
	
	public char max() {
		char max = 0xffff;
		for (int i = 0; i < size(); i++) if (get(i) > max) max = get(i);
		return max;
	}

	public char[] array() {
		char[] t = new char[size()];
		for (int i = 0; i < t.length; i++) t[i] = get(i);
		return t;
	}
	
	public int count(char c) {
		int n = 0;
		for (char v: this) if (c == v) n++;
		return n;
	}

	public int count(Predicate<Character> p) {
		int n = 0;
		for (char v: this) if (p.test(v)) n++;
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

	public Lc distinct() {
		return new Lc(new LinkedHashSet<>(this)); 
	}
	
	public Lc uppercased() {
		return mapped(x -> x >= 'a' && x <= 'z' ? (char) (x - 32) : x); 
	}
	
	public Lc lowercased() {
		return mapped(x -> x >= 'A' && x <= 'Z' ? (char) (x + 32) : x); 
	}
	
	public Lc replace(char a, char b) {
		for (int i = 0; i < size(); i++) if (get(i) == a) set(i, b);
		return this;
	}
	
	public void add(char... cs) {
		for (char c: cs) add(c);
	}
	
	public Lc added(char c) {
		Lc l = copy();
		l.add(c);
		return l;
	}
	
	public Lc added(char... cs) {
		Lc l = copy();
		for (char c: cs) l.add(c);
		return l;
	}
	
	public Lc addedAll(Iterable<Character> cs) {
		Lc l = copy();
		for (char c: cs) l.add(c);
		return l;
	}

	public Lc filled(int n, char v) {
		Lc l = new Lc();
		for (int i = 0; i < n; i++) l.add(v);
		return l;
	}
}
