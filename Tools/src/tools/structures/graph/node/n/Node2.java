package tools.structures.graph.node.n;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import tools.collections.node.Ln2;

public class Node2 {
	private static Map<String, Node2> stringMap = new HashMap<>();
	public static Ln2 all = new Ln2();
	private static Ln2 leafs = new Ln2();
	private static Ln2 roots = new Ln2();
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
	public Ln2 links = new Ln2();
	public Ln2 parents = new Ln2();
	
	public static int count = 0;

	public Node2() { this(count); }
	public Node2(int id) { this("" + id); this.id = id; }
	public Node2(int x, int y) { this("(" + x + "," + y + ")"); this.x = x; this.y = y; }
	public Node2(int x, int y, int z) { this("(" + x + "," + y + "," + z + ")"); this.x = x; this.y = y; this.z = z; }
	public Node2(String name) { this.name = name; all.add(this); stringMap.put(name, this); id = count++; }
	public static Ln2 create(int n) {
		Ln2 ns = new Ln2();
		for (int i = 0; i < n; i++) ns.add(new Node2());
		return ns;
	}
	
	public String toString() {
		return name + '[' + links.stream().map(n->n.name).collect(Collectors.joining(",")) + ']';
	}

	public static void range0(int n) {
		for (int i = 0; i < n; i++) new Node2(i);
	}

	public static void range1(int n) {
		for (int i = 1; i <= n; i++) new Node2(i);
	}
	
	public Node2 copyWithoutLinks() {
		Node2 n = new Node2();
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

	public static boolean exists(String name) {
		return stringMap.containsKey(name);
	}

	public static boolean exists(int id) {
		return stringMap.containsKey("" + id);
	}

	public static Node2 get(String name) {
		Node2 n = stringMap.get(name);
		if (n == null) n = new Node2(name);
		return n;
	}
	
	public static Node2 get(int i) {
		return get("" + i);
	}
	
	public static void build(String name) {
		if (stringMap.containsKey(name)) return;
		Node2 n = new Node2(name);
		stringMap.put(name, n);
	}
	
	public static void build(int i) {
		get("" + i).id = i;
	}

	public static void buildSingle(String parent, String child) {
		Node2 p = get(parent);
		Node2 c = get(child);
		Ln2 links = p.links;
		Ln2 parents = c.parents;
		if (!links.contains(c)) links.add(c);
		if (parents.contains(p)) parents.add(p);
	}

	public static void buildSingle(int parent, int child) {
		Node2 p = get(parent);
		Node2 c = get(child);
		Ln2 links = p.links;
		Ln2 parents = c.parents;
		if (!links.contains(c)) links.add(c);
		if (parents.contains(p)) parents.add(p);
	}

	public static void buildDual(String node1, String node2) {
		Node2 n1 = get(node1);
		Node2 n2 = get(node2);
		Ln2 links1 = n1.links;
		Ln2 links2 = n2.links;
		if (!links1.contains(n2)) links1.add(n2);
		if (!links2.contains(n1)) links2.add(n1);
	}
	
	public static void buildDual(int node1, int node2) {
		Node2 n1 = get(node1);
		Node2 n2 = get(node2);
		Ln2 links1 = n1.links;
		Ln2 links2 = n2.links;
		if (!links1.contains(n2)) links1.add(n2);
		if (!links2.contains(n1)) links2.add(n1);
	}
	
	private static void init() {
		if (clean) return;
		clean = true;
		leafs.clear();
		roots.clear();
		for (Node2 n : all) {
			if (n.links.isEmpty()) leafs.add(n);
			if (n.parents.isEmpty()) roots.add(n);
		}
	}
	
	public void propagateLeafsToRoots(BiConsumer<Node2, Node2> action) {
		init();
		for (Node2 n : all) n.$ = 0;
		Ln2 current = new Ln2(leafs);
		Ln2 next = new Ln2();
		while (!current.isEmpty()) {
			for (Node2 n : current) for (Node2 p : n.parents) {
				action.accept(n, p);
				if (++p.$ == p.links.size()) next.add(p);
			}
			Ln2 tmp = next;
			next = current;
			next.clear();
			current = tmp;
		}
	}
	
	public void propagateRootsToLeafs(BiConsumer<Node2, Node2> action) {
		init();
		for (Node2 n : all) { n.$ = 0; }
		List<Node2> current = new ArrayList<>(roots);
		List<Node2> next = new ArrayList<>();
		while (!current.isEmpty()) {
			for (Node2 n : current) for (Node2 c : n.links) {
				action.accept(n, c);
				if (++c.$ == c.parents.size()) next.add(c);
			}
			List<Node2> tmp = next;
			next = current;
			next.clear();
			current = tmp;
		}
	}
	
	public static Ln2 getRoots() {
		init();
		return roots;
	}
	
	public static Ln2 getLeafs() {
		init();
		return leafs;
	}
}
