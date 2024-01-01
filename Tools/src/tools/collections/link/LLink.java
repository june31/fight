package tools.collections.link;

import java.util.ArrayList;

import tools.structures.graph.link.Link;

@SuppressWarnings("serial")
public class LLink extends ArrayList<Link> {
	public LLink() { super(); }
	public LLink(int capacity) { super(capacity); }
	public LLink(Iterable<Link> it) { for (Link l: it) add(l); }
	public LLink(Link[] t) { for (Link l: t) add(l); }

	public Link g(int i) { return get(i); }
}
