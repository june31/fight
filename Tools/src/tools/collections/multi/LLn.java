package tools.collections.multi;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import tools.collections.node.Ln;
import tools.structures.graph.node.Node;

@SuppressWarnings("serial")
public class LLn extends ArrayList<Ln> {
	public LLn() { }

	public LLn(Iterable<Ln> it) {
		for (Ln i : it) add(i);
	}

	public LLn(Ln... t) {
		for (Ln i : t) add(i);
	}

	public static LLn of(Ln... t) {
		return new LLn(t);
	}
	
	public Ln getAll() {
		Set<Node> all = new LinkedHashSet<>();
		for (Ln ls : this) all.addAll(ls);
		return new Ln(all);
	}
	
	public LLn deepCopy() {
		LLn copy = new LLn();
		for (Ln ls : this) copy.add(new Ln(ls));
		return copy;
	}

	public Ln first() {
		return get(0);
	}
	
	public Ln last() {
		return get(size() - 1);
	}
}
