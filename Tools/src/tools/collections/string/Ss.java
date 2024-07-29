package tools.collections.string;

import java.util.TreeSet;

@SuppressWarnings("serial")
public class Ss extends TreeSet<String> {

	public Ss() {
		super();
	}

	public Ss(Iterable<String> it) {
		for (String i : it)
			add(i);
	}

	public Ss(String[] t) {
		for (String i : t)
			add(i);
	}

	public static Ss of(String... t) {
		return new Ss(t);
	}
}
