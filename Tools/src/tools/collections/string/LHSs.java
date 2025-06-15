package tools.collections.string;

import java.util.LinkedHashSet;

@SuppressWarnings("serial")
public class LHSs extends LinkedHashSet<String> {

	public LHSs() {
		super();
	}

	public LHSs(Iterable<String> it) {
		for (String i : it)
			add(i);
	}

	public LHSs(String[] t) {
		for (String i : t)
			add(i);
	}

	public static LHSs of(String... t) {
		return new LHSs(t);
	}
}
