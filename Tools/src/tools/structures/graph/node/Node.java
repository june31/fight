package tools.structures.graph.node;

import java.util.ArrayList;
import java.util.List;

public class Node {
	public static List<Node> all = new ArrayList<>();
	public static int count = 0;
	public final int id;
	public List<Node> links = new ArrayList<>();
	public Node() { id = count++; all.add(this); }
	public Node(int i) { id = i; all.add(this); }
}
