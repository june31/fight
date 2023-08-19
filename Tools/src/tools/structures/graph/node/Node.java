package tools.structures.graph.node;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Node {
	public static List<Node> all = new ArrayList<>();
	public static int count = 0;
	public static Node[] create(int n) {
		Node[] ns = new Node[n];
		for (int i=0; i<n; i++) ns[i] = new Node();
		return ns;
	}
	public final int id;
	public List<Node> links = new ArrayList<>();
	public Node() { id = count++; all.add(this); }
	public Node(int i) { id = i; all.add(this); }
	public String toString() {
		return "N" + id + links.stream().mapToInt(n->n.id).boxed().collect(Collectors.toList());
	}
}
