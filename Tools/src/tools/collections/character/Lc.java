package tools.collections.character;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Lc extends ArrayList<Character> {
	public Lc() { super(); }
	public Lc(int capacity) { super(capacity); }
	public Lc(Iterable<Character> it) { for (char c: it) add(c); }
	public Lc(int[] t) { for (int i: t) add((char) i); }
	public Lc(byte[] t) { for (byte b: t) add((char) b); }
	public Lc(char[] t) { for (char c: t) add(c); }
	public Lc(String s) { this(s.toCharArray()); }
	
	public static Lc of(int... ints) { return new Lc(ints); }
}
