package tools.collections.int64;

import java.util.ArrayList;

import tools.function.LongToBooleanFunction;
import tools.function.LongToLongFunction;

@SuppressWarnings("serial")
public class Ll extends ArrayList<Long> {
	
	public Ll map(LongToLongFunction f) {
		Ll l = new Ll();
		for (long i: this) l.add(f.applyAsLong(i));
		return l;
	}
	
	public Ll sub(int s) { return sub(s, size()); }
	public Ll sub(int s, int e) {
		Ll l = new Ll();
		if (s < 0) s += size();
		if (e < 0) e += size();
		for (int i = s; i < e; i++) l.add(get(i));
		return l;
	}

	public String join() { return join(""); };
	public String join(String s) {
		StringBuilder sb = new StringBuilder();
		boolean w = false;
		for (long i: this) {
			if (w) sb.append(s);
			sb.append(i);
			w = true;
		}
		return sb.toString();
	}
	
	public Ll filter(LongToBooleanFunction f) {
		Ll l = new Ll();
		for (long i: this) if (f.applyAsBoolean(i)) l.add(i);
		return l;
	}
}
