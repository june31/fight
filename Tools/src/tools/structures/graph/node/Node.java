package tools.structures.graph.node;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import tools.collections.node.Ln;

public class Node {
	private static Map<String, Node> stringMap = new HashMap<>();
	public static Ln all = new Ln();
	public String name;
	
	// optional
	public double weight;
	public int x, y, z;
	public long l;
	public double d;
	public boolean b;
	public String s;
	public Ln links = new Ln();

	public static int count = 0;

	public Node() { this(count); }
	public Node(int x) { this("" + x); this.x = x; }
	public Node(int x, int y) { this("(" + x + "," + y + ")"); this.x = x; this.y = y; }
	public Node(int x, int y, int z) { this("(" + x + "," + y + "," + z + ")"); this.x = x; this.y = y; this.z = z; }
	public Node(String name) { this.name = name; all.add(this); stringMap.put(name, this); count++; }
	public static Ln create(int n) {
		Ln ns = new Ln();
		for (int i = 0; i < n; i++) ns.add(new Node());
		return ns;
	}
	public String toString() {
		return name + '[' + links.stream().map(n->n.name).collect(Collectors.joining(",")) + ']';
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
	
	public static Node fromName(String name) {
		return stringMap.get(name);
	}
	
	public static Node fromName(int i) {
		return stringMap.get("" + i);
	}
}
