package tools.collections.multi;

import java.util.ArrayList;
import java.util.Collection;

import tools.collections.int32.L;
import tools.collections.string.Ls;

@SuppressWarnings("serial")
public class LLi extends ArrayList<L> {

	public LLi() {}
	public LLi(Collection<L> c) { super(c); }
	
	public static LLi fromLs(Ls ls) {
		LLi lli = new LLi();
		for (String s: ls) lli.add(new L(s));
		return lli;
	}
	
	public static LLi fromLsChars(Ls ls) {
		LLi lli = new LLi();
		for (String s: ls) {
			L l = new L();
			for (char c: s.toCharArray()) l.add(c);
			lli.add(l);
		}
		return lli;
	}

	public static LLi fromLsDigits(Ls ls) {
		LLi lli = new LLi();
		for (String s: ls) {
			L l = new L();
			for (char c: s.toCharArray()) l.add(c - '0');
			lli.add(l);
		}
		return lli;
	}
	
	public L first() {
		return get(0);
	}
	
	public L last() {
		return get(size() - 1);
	}
	
	public LLi copy() {
		LLi lli = new LLi();
		for (L l: this) lli.add(l.copy());
		return lli;
	}
}
