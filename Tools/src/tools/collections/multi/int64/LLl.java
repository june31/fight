package tools.collections.multi.int64;

import java.util.ArrayList;
import java.util.Collection;

import tools.collections.int64.Ll;
import tools.collections.string.Ls;

@SuppressWarnings("serial")
public class LLl extends ArrayList<Ll> {

	public LLl() {}
	public LLl(Collection<Ll> c) { super(c); }
	
	public static LLl fromLs(Ls ls) {
		LLl lll = new LLl();
		for (String s: ls) lll.add(new Ll(s));
		return lll;
	}
	
	public static LLl fromLsChars(Ls ls) {
		LLl lll = new LLl();
		for (String s: ls) {
			Ll l = new Ll();
			for (char c: s.toCharArray()) l.add(c);
			lll.add(l);
		}
		return lll;
	}

	public static LLl fromLsDigits(Ls ls) {
		LLl lll = new LLl();
		for (String s: ls) {
			Ll l = new Ll();
			for (char c: s.toCharArray()) l.add(c - '0');
			lll.add(l);
		}
		return lll;
	}
	
	public Ll first() {
		return get(0);
	}
	
	public Ll last() {
		return get(size() - 1);
	}
	
	public LLl copy() {
		LLl lll = new LLl();
		for (Ll l: this) lll.add(l.copy());
		return lll;
	}
}
