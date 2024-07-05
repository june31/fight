package tools.collections.node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;

import tools.collections.link.LLink;
import tools.function.IntObjConsumer;
import tools.function.IntObjPredicate;
import tools.structures.graph.link.Link;
import tools.structures.graph.node.n.Node2;

@SuppressWarnings("serial")
public class Ln2 extends ArrayList<Node2> {
	
	public Ln2() { super(); }
	public Ln2(int capacity) { super(capacity); }
	public Ln2(Iterable<Node2> it) { for (Node2 n: it) add(n); }
	public Ln2(Node2[] t) { for (Node2 n: t) add(n); }
	public Ln2(int n, IntFunction<Node2> o) { for (int i = 0; i < n; i++) add(o.apply(i)); }


	public void addNode() { add(new Node2()); }
	
	public void addLinkSingle(Node2 from, Node2 to) { from.links.add(to); to.parents.add(from); }
	public void addLinkSingle(Link l) { l.a.links.add(l.b); l.b.parents.add(l.a); }
	public void addLinksSingle(LLink lk) { for (Link l: lk) { l.a.links.add(l.b); l.b.parents.add(l.a); } }
	public void removeLinkSingle(Node2 from, Node2 to) { from.links.remove(to); to.parents.remove(from); }
	public void removeLinkSingle(Link l) { l.a.links.remove(l.b); l.b.parents.remove(l.a); }
	public void removeLinksSingle(LLink lk) { for (Link l: lk) { l.a.links.remove(l.b); l.b.links.remove(l.a); } }

	public void addLinkDual(Node2 from, Node2 to) { from.links.add(to); to.links.add(from); }
	public void addLinkDual(Link l) { l.a.links.add(l.b); l.b.links.add(l.a); }
	public void addLinksDual(LLink Ln) { for (Link l: Ln) { l.a.links.add(l.b); l.b.links.add(l.a); } }
	public void removeLinkDual(Node2 from, Node2 to) { from.links.remove(to); to.links.remove(from); }
	public void removeLinkDual(Link l) { l.a.links.remove(l.b); l.b.links.remove(l.a); }
	public void removeLinksDual(LLink Ln) { for (Link l: Ln) { l.a.links.remove(l.b); l.b.links.remove(l.a); } }

	public static Ln2 of(Node2... nodes) {
		Ln2 l = new Ln2();
		for (Node2 n: nodes) l.add(n);
		return l;
	}
	
	public static Ln2 range0(int n) {
		Ln2 l = new Ln2();
		for (int i = 0; i < n; i++) l.add(new Node2(i));
		return l;
	}

	public static Ln2 range1(int n) {
		Ln2 l = new Ln2();
		for (int i = 1; i <= n; i++) l.add(new Node2(i));
		return l;
	}
	
	public Ln2 deepCopy() {
		Map<Node2, Node2> map = new LinkedHashMap<>();
		for (Node2 n: this) map.put(n, n.copyWithoutLinks());
		for (Node2 n: this) for (Node2 linked: n.links) map.get(n).links.add(map.get(linked));
		for (Node2 n: this) for (Node2 linked: n.parents) map.get(n).parents.add(map.get(linked));
		return new Ln2(map.values());
	}
	
	public Node2 g(int i) { return get(Math.floorMod(i, size())); }

	public Ln2 mapped(Function<Node2, Node2> f) {
		Ln2 l = new Ln2();
		for (Node2 i: this) l.add(f.apply(i));
		return l;
	}

	public void foreach(Consumer<Node2> c) {
		for (Node2 i: this) c.accept(i);
	}

	public void foreach(IntObjConsumer<Node2> c) {
		for (int i = 0; i < size(); i++) c.accept(i, get(i));
	}
	
	public Ln2 subbed(int s) { return subbed(s, size(), 1); }
	public Ln2 subbed(int s, int e) { return subbed(s, e, 1); }
	public Ln2 subbed(int s, int e, int k) {
		Ln2 l = new Ln2();
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
		for (Node2 i: this) {
			if (w) sb.append(s);
			sb.append(i);
			w = true;
		}
		return sb.toString();
	}

	public Ln2 filtered(Predicate<Node2> f) {
		Ln2 l = new Ln2();
		for (Node2 i: this) if (f.test(i)) l.add(i);
		return l;
	}
	
	public Ln2 filtered(IntObjPredicate<Node2> f) {
		Ln2 l = new Ln2();
		for (int i = 0; i < size(); i++) {
			Node2 v = get(i);
			if (f.test(i, v)) l.add(v);
		}
		return l;
	}

	public Ln2 reversed() {
		Ln2 l = new Ln2();
		int max = size() - 1;
		for (int i = 0; i <= max; i++) l.add(get(max - i));
		return l;
	}

	public Ln2 shuffled() {
		Ln2 l = new Ln2(this);
		Collections.shuffle(l);
		return l;
	}

	public Node2[] array() {
		Node2[] t = new Node2[size()];
		for (int i = 0; i < t.length; i++) t[i] = get(i);
		return t;
	}
	
	public static Ln2 createNodesAndLinks(String[] lines, boolean dual) {
		List<String[]> tokens = new ArrayList<>();
		Set<String> names = new LinkedHashSet<>();
		for (String line: lines) {
			String[] t = line.split("[,;:=\\-()\\[\\]{}\\s]+");
			tokens.add(t);
			for (String name: t) names.add(name);
		}
		Ln2 l = new Ln2();
		for (String name: names) l.add(new Node2(name));
		for (String[] t: tokens) {
			Node2 n1 = Node2.get(t[0]);
			for (int i = 1; i < t.length; i++) {
				Node2 n2 = Node2.get(t[i]);
				n1.links.add(n2);
				if (dual) n2.links.add(n1);
				else n2.parents.add(n1);
			}
		}
		return l;
	}
	
	public int count(Node2 n) {
		int i = 0;
		for (Node2 v: this) if (n.equals(v)) i++;
		return i;
	}
	
	public Map<Node2, Integer> countAll() {
		Map<Node2, Integer> m = new TreeMap<>();
		for (Node2 v: this) {
			Integer i = m.get(v);
			if (i == null) m.put(v, 1);
			else m.put(v, i + 1);
		}
		return m;
	}
	
	public int count(Predicate<Node2> p) {
		int n = 0;
		for (Node2 v: this) if (p.test(v)) n++;
		return n;
	}
	
	public void debug() {
		System.err.println(this);
	}
	
	public Ln2 distinct() {
		return new Ln2(new LinkedHashSet<>(this)); 
	}
}
