package tools.collections.node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import tools.collections.link.LLink;
import tools.function.IntObjConsumer;
import tools.function.IntObjPredicate;
import tools.structures.graph.link.Link;
import tools.structures.graph.node.Node;

@SuppressWarnings("serial")
public class Ln extends ArrayList<Node> {
	
	public Ln() { super(); }
	public Ln(int capacity) { super(capacity); }
	public Ln(Iterable<Node> it) { for (Node n: it) add(n); }
	public Ln(Node[] t) { for (Node n: t) add(n); }

	public LLink getLinks() {
		LLink l = new LLink();
		for (int i = 0; i < size() - 1; i++) {
			l.add(new Link(get(i), get(i+1)));
		}
		return l;
	}
	
	public void addNode() { add(new Node()); }
	
	public void addLinkSingle(Node from, Node to) { from.links.add(to); }
	public void addLinkSingle(Link l) { l.a.links.add(l.b); }
	public void addLinksSingle(LLink Ln) { for (Link l: Ln) l.a.links.add(l.b); }
	public void removeLinkSingle(Node from, Node to) { from.links.remove(to); }
	public void removeLinkSingle(Link l) { l.a.links.remove(l.b); }
	public void removeLinksSingle(LLink Ln) { for (Link l: Ln) l.a.links.remove(l.b); }

	public void addLinkDual(Node from, Node to) { from.links.add(to); to.links.add(from); }
	public void addLinkDual(Link l) { l.a.links.add(l.b); l.b.links.add(l.a); }
	public void addLinksDual(LLink Ln) { for (Link l: Ln) { l.a.links.add(l.b); l.b.links.add(l.a); } }
	public void removeLinkDual(Node from, Node to) { from.links.remove(to); to.links.remove(from); }
	public void removeLinkDual(Link l) { l.a.links.remove(l.b); l.b.links.remove(l.a); }
	public void removeLinksDual(LLink Ln) { for (Link l: Ln) { l.a.links.remove(l.b); l.b.links.remove(l.a); } }

	public static Ln of(Node... nodes) {
		Ln l = new Ln();
		for (Node n: nodes) l.add(n);
		return l;
	}
	
	public static Ln range0(int n) {
		Ln l = new Ln();
		for (int i = 0; i < n; i++) l.add(new Node(i));
		return l;
	}

	public static Ln range1(int n) {
		Ln l = new Ln();
		for (int i = 1; i <= n; i++) l.add(new Node(i));
		return l;
	}
	
	public Ln deepCopy() {
		Map<Node, Node> map = new LinkedHashMap<>();
		for (Node n: this) map.put(n, n.copyWithoutLinks());
		for (Node n: this) for (Node linked: n.links) map.get(n).links.add(map.get(linked));
		return new Ln(map.values());
	}
	
	public Node g(int i) { return get(i); }

	public Ln mapped(Function<Node, Node> f) {
		Ln l = new Ln();
		for (Node i: this) l.add(f.apply(i));
		return l;
	}

	public void foreach(Consumer<Node> c) {
		for (Node i: this) c.accept(i);
	}

	public void foreach(IntObjConsumer<Node> c) {
		for (int i = 0; i < size(); i++) c.accept(i, get(i));
	}
	
	public Ln subbed(int s) { return subbed(s, size(), 1); }
	public Ln subbed(int s, int e) { return subbed(s, e, 1); }
	public Ln subbed(int s, int e, int k) {
		Ln l = new Ln();
		while (s < 0) s += size();
		while (e < 0) e += size();
		if (k > 0) for (int i = s; i < e; i += k) l.add(get(i));
		else for (int i = e-1; i >= s; i += k) l.add(get(i));
		return l;
	}

	public String join() { return join(" "); };
	public String join(String s) {
		StringBuilder sb = new StringBuilder();
		boolean w = false;
		for (Node i: this) {
			if (w) sb.append(s);
			sb.append(i);
			w = true;
		}
		return sb.toString();
	}

	public Ln filtered(Predicate<Node> f) {
		Ln l = new Ln();
		for (Node i: this) if (f.test(i)) l.add(i);
		return l;
	}
	
	public Ln filtered(IntObjPredicate<Node> f) {
		Ln l = new Ln();
		for (int i = 0; i < size(); i++) {
			Node v = get(i);
			if (f.test(i, v)) l.add(v);
		}
		return l;
	}

	public Ln reversed() {
		Ln l = new Ln();
		int max = size() - 1;
		for (int i = 0; i <= max; i++) l.add(get(max - i));
		return l;
	}

	public Ln shuffled() {
		Ln l = new Ln(this);
		Collections.shuffle(l);
		return l;
	}

	public Node[] array() {
		Node[] t = new Node[size()];
		for (int i = 0; i < t.length; i++) t[i] = get(i);
		return t;
	}
	
	public static Ln createNodesAndLinks(String[] lines, boolean dual) {
		List<String[]> tokens = new ArrayList<>();
		Set<String> names = new LinkedHashSet<>();
		for (String line: lines) {
			String[] t = line.split("[,;:=\\-()\\[\\]{}\\s]+");
			tokens.add(t);
			for (String name: t) names.add(name);
		}
		Ln l = new Ln();
		for (String name: names) l.add(new Node(name));
		for (String[] t: tokens) {
			Node n1 = Node.fromName(t[0]);
			for (int i = 1; i < t.length; i++) {
				Node n2 = Node.fromName(t[i]);
				n1.links.add(n2);
				if (dual) n2.links.add(n1);
			}
		}
		return l;
	}
	
	public int count(Node n) {
		int i = 0;
		for (Node v: this) if (n.equals(v)) i++;
		return i;
	}
	
	public int count(Predicate<Node> p) {
		int n = 0;
		for (Node v: this) if (p.test(v)) n++;
		return n;
	}
}
