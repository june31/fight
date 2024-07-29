package tools.collections.node;

import java.util.LinkedHashSet;

import tools.structures.graph.node.Node;

@SuppressWarnings("serial")
public class Sn extends LinkedHashSet<Node> {
	public Sn() {
		super();
	}

	public Sn(Iterable<Node> it) {
		for (Node i : it)
			add(i);
	}
}
