package tools.collections.bool;

import java.util.ArrayList;

import tools.collections.character.Lc;

@SuppressWarnings("serial")
public class Lb extends ArrayList<Boolean> {
	public static Lc TRUE_CHARS = Lc.of(1, '1', 'Y', '#', '+', '*'); 
	
	public Lb() { super(); }
	public Lb(int capacity) { super(capacity); }
	public Lb(Iterable<Boolean> it) { for (boolean b: it) add(b); }
	public Lb(int[] t) { for (int i: t) add(i != 0); }
	public Lb(byte[] t) { for (byte b: t) add(b != 0); }
	public Lb(char[] t) { for (char c: t) add(TRUE_CHARS.contains(c)); }
	public Lb(boolean[] t) { for (boolean b: t) add(b); }
	public Lb(String s) { for (String e: s.split("[^-\\d]+")) if (!e.isEmpty()) add(Boolean.parseBoolean(e)); }
	
	public static Lb of(boolean... bs) { return new Lb(bs); } 
}
