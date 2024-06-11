package tools.collections.int32;

import java.util.TreeSet;

@SuppressWarnings("serial")
public class Si extends TreeSet<Integer> {
	public Si() { super(); }
	public Si(Iterable<Integer> it) { for (int i: it) add(i); }
	public Si(int[] t) { for (int i: t) add(i); }
	
	public static Si of(int... t) {
		Si s = new Si();
		for (int i : t) s.add(i);
		return s;
	}
	public void add(char c) {
		add((int) c);
	}
}
