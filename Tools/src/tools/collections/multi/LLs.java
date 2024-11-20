package tools.collections.multi;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import tools.collections.string.Ls;

@SuppressWarnings("serial")
public class LLs extends ArrayList<Ls> {
	public LLs() { }

	public LLs(Iterable<Ls> it) {
		for (Ls i : it) add(i);
	}

	public LLs(Ls... t) {
		for (Ls i : t) add(i);
	}

	public static LLs of(Ls... t) {
		return new LLs(t);
	}
	
	public Ls getAll() {
		Set<String> all = new LinkedHashSet<>();
		for (Ls ls : this) all.addAll(ls);
		return new Ls(all);
	}
	
	public LLs deepCopy() {
		LLs copy = new LLs();
		for (Ls ls : this) copy.add(new Ls(ls));
		return copy;
	}

	public Ls first() {
		return get(0);
	}
	
	public Ls last() {
		return get(size() - 1);
	}
}
