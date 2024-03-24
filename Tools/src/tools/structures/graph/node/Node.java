package tools.structures.graph.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import tools.collections.node.Ln;

public class Node {
	private static Map<String, Node> stringMap = new HashMap<>();
	public static Ln all = new Ln();
	private static Ln leafs = new Ln();
	private static Ln roots = new Ln();
	public String name;
	private static boolean clean = false;
	
	// optional
	public double weight;
	public int id, x, y, z;
	private int $;
	public long l;
	public double d;
	public boolean b;
	public String s;
	public Ln links = new Ln();
	public Ln parents = new Ln();
	
	public static int count = 0;

	public Node() { this(count); }
	public Node(int id) { this("" + id); this.id = id; }
	public Node(int x, int y) { this("(" + x + "," + y + ")"); this.x = x; this.y = y; }
	public Node(int x, int y, int z) { this("(" + x + "," + y + "," + z + ")"); this.x = x; this.y = y; this.z = z; }
	public Node(String name) { this.name = name; all.add(this); stringMap.put(name, this); id = count++; }
	public static Ln create(int n) {
		Ln ns = new Ln();
		for (int i = 0; i < n; i++) ns.add(new Node());
		return ns;
	}
	
	public String toString() {
		return name + '[' + links.stream().map(n->n.name).collect(Collectors.joining(",")) + ']';
	}

	public static void range0(int n) {
		for (int i = 0; i < n; i++) new Node(i);
	}

	public static void range1(int n) {
		for (int i = 1; i <= n; i++) new Node(i);
	}
	
	public Node copyWithoutLinks() {
		Node n = new Node();
		n.name = name;
		n.weight = weight;
		n.x = x;
		n.y = y;
		n.z = z;
		n.l = l;
		n.d = d;
		n.b = b;
		n.s = s;
		return n;
	}
	
	public static Node get(String name) {
		Node n = stringMap.get(name);
		if (n == null) n = new Node(name);
		return n;
	}
	
	public static Node get(int i) {
		return get("" + i);
	}
	
	public static void build(String name) {
		if (stringMap.containsKey(name)) return;
		Node n = new Node(name);
		stringMap.put(name, n);
	}
	
	public static void build(int i) {
		get("" + i).id = i;
	}

	public static void buildSingle(String parent, String child) {
		Node p = get(parent);
		Node c = get(child);
		Ln links = p.links;
		Ln parents = c.parents;
		if (!links.contains(c)) links.add(c);
		if (parents.contains(p)) parents.add(p);
	}

	public static void buildSingle(int parent, int child) {
		Node p = get(parent);
		Node c = get(child);
		Ln links = p.links;
		Ln parents = c.parents;
		if (!links.contains(c)) links.add(c);
		if (parents.contains(p)) parents.add(p);
	}

	public static void buildDual(String node1, String node2) {
		Node n1 = get(node1);
		Node n2 = get(node2);
		Ln links1 = n1.links;
		Ln links2 = n2.links;
		if (!links1.contains(n2)) links1.add(n2);
		if (!links2.contains(n1)) links2.add(n1);
	}
	
	public static void buildDual(int node1, int node2) {
		Node n1 = get(node1);
		Node n2 = get(node2);
		Ln links1 = n1.links;
		Ln links2 = n2.links;
		if (!links1.contains(n2)) links1.add(n2);
		if (!links2.contains(n1)) links2.add(n1);
	}
	
	private static void init() {
		if (clean) return;
		clean = true;
		leafs.clear();
		roots.clear();
		for (Node n : all) {
			if (n.links.isEmpty()) leafs.add(n);
			if (n.parents.isEmpty()) roots.add(n);
		}
	}
	
	public void propagateLeafsToRoots(BiConsumer<Node, Node> action) {
		init();
		for (Node n : all) n.$ = 0;
		Ln current = new Ln(leafs);
		Ln next = new Ln();
		while (!current.isEmpty()) {
			for (Node n : current) for (Node p : n.parents) {
				action.accept(n, p);
				if (++p.$ == p.links.size()) next.add(p);
			}
			Ln tmp = next;
			next = current;
			next.clear();
			current = tmp;
		}
	}
	
	public void propagateRootsToLeafs(BiConsumer<Node, Node> action) {
		init();
		for (Node n : all) { n.$ = 0; }
		List<Node> current = new ArrayList<>(roots);
		List<Node> next = new ArrayList<>();
		while (!current.isEmpty()) {
			for (Node n : current) for (Node c : n.links) {
				action.accept(n, c);
				if (++c.$ == c.parents.size()) next.add(c);
			}
			List<Node> tmp = next;
			next = current;
			next.clear();
			current = tmp;
		}
	}
	
	public static Ln getRoots() {
		init();
		return roots;
	}
	
	public static Ln getLeafs() {
		init();
		return leafs;
	}
}
