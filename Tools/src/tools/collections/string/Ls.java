package tools.collections.string;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;

import tools.function.IntObjConsumer;
import tools.function.IntObjPredicate;

@SuppressWarnings("serial")
public class Ls extends ArrayList<String> {
	public Ls() { super(); }
	public Ls(int capacity) { super(capacity); }
	public Ls(Iterable<String> it) { for (Object o: it) add(o.toString()); }
	public Ls(int[] t) { for (int i: t) add("" + i); }
	public Ls(byte[] t) { for (byte b: t) add("" + b); }
	public Ls(char[] t) { for (char c: t) add("" + c); }
	public Ls(boolean[] t) { for (boolean b: t) add("" + b); }
	public Ls(String[] t) { for (String s: t) add(s); }
	public Ls(Object[] t) { for (Object o: t) add(o.toString()); }
	public Ls(int n, IntFunction<String> o) { for (int i = 0; i < n; i++) add(o.apply(i)); }
	
	public static Ls of(String... ss) { return new Ls(ss); }
	
	public String g(int i) { return get(i); }

	public Ls mapped(Function<String, String> f) {
		Ls l = new Ls();
		for (String s: this) l.add(f.apply(s));
		return l;
	}

	public void foreach(Consumer<String> c) {
		for (String s: this) c.accept(s);
	}

	public void foreach(IntObjConsumer<String> c) {
		for (int i = 0; i < size(); i++) c.accept(i, get(i));
	}
	
	public Ls subbed(int s) { return subbed(s, size(), 1); }
	public Ls subbed(int s, int e) { return subbed(s, e, 1); }
	public Ls subbed(int s, int e, int k) {
		Ls l = new Ls();
		while (s < 0) s += size();
		while (e < 0) e += size();
		if (k > 0) for (int i = s; i < e; i += k) l.add(get(i));
		else for (int i = e - 1; i >= s; i += k) l.add(get(i));
		return l;
	}

	public String join() { return join(" "); };
	public String join(String sep) {
		StringBuilder sb = new StringBuilder();
		boolean w = false;
		for (String s: this) {
			if (w) sb.append(sep);
			sb.append(s);
			w = true;
		}
		return sb.toString();
	}

	public Ls filtered(Predicate<String> f) {
		Ls l = new Ls();
		for (String s: this) if (f.test(s)) l.add(s);
		return l;
	}
	
	public Ls filtered(IntObjPredicate<String> f) {
		Ls l = new Ls();
		for (int i = 0; i < size(); i++) {
			String s = get(i);
			if (f.test(i, s)) l.add(s);
		}
		return l;
	}

	public Ls sortedUp() {
		Ls l = new Ls(this);
		l.sort(Comparator.naturalOrder());
		return l;
	}

	public Ls sortedDown() {
		Ls l = new Ls(this);
		l.sort(Comparator.reverseOrder());
		return l;
	}

	public Ls reversed() {
		Ls l = new Ls();
		int max = size() - 1;
		for (int i = 0; i <= max; i++) l.add(get(max - i));
		return l;
	}

	public Ls shuffled() {
		Ls l = new Ls(this);
		Collections.shuffle(l);
		return l;
	}
	
	public String[] array() {
		String[] t = new String[size()];
		for (int i = 0; i < t.length; i++) t[i] = get(i);
		return t;
	}
	
	public int count(String s) {
		int n = 0;
		for (String v: this) if (s.equals(v)) n++;
		return n;
	}
	
	public int count(Predicate<String> p) {
		int n = 0;
		for (String v: this) if (p.test(v)) n++;
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
}
