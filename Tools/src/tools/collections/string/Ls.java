package tools.collections.string;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Ls extends ArrayList<String> {
	public Ls() { super(); }
	public Ls(int capacity) { super(capacity); }
	public Ls(Iterable<Object> it) { for (Object o: it) add(o.toString()); }
	public Ls(int[] t) { for (int i: t) add("" + i); }
	public Ls(byte[] t) { for (byte b: t) add("" + b); }
	public Ls(char[] t) { for (char c: t) add("" + c); }
	public Ls(boolean[] t) { for (boolean b: t) add("" + b); }
	public Ls(String[] t) { for (String s: t) add(s); }
	public Ls(Object[] t) { for (Object o: t) add(o.toString()); }
	
	public static Ls of(String... ss) { return new Ls(ss); } 
}
