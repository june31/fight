package tools.structures.graph.node;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WNode {
	public static List<WNode> all = new ArrayList<>();
	public static int count = 0;
	public final int id;
	public int weight = 0;
	public List<WNode> links = new ArrayList<>();
	public WNode() { id = count++; all.add(this); }
	public WNode(int w) { this(); weight = w; }
	public String toString() {
		return "N" + id + ":" + weight + links.stream().mapToInt(n->n.id).boxed().collect(Collectors.toList());
	}
}
