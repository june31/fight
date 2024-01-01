package tools.bfs;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;

import tools.collections.node.Ln;
import tools.structures.graph.node.Node;

public final class BFSGraph {

	public boolean found;
	public int scanned; // includes start
	public Node n1;
	public Node n2;
	public Node start;
	public int depth;
	
	public final Map<Node, Node> backTrack = new HashMap<>();
	private final Ln[] workNodes = new Ln[2];

	public int diffuse(Node s) { return diffuse(s, () -> false, () -> true); }
	public int diffuse(Node s, Node e) { return diffuse(s, () -> n2 == e, () -> true); }
	public int diffuse(Node s, Node e, BooleanSupplier moveCondition) { return diffuse(s, () -> n2 == e, moveCondition); }
	public int diffuse(Node s, BooleanSupplier end)  { return diffuse(s, end, () -> true); }
	public int diffuse(Node s, BooleanSupplier end, BooleanSupplier moveCondition) {
		start = s;
		backTrack.clear();
		depth = 0;
		n1 = n2 = s;
		if (end.getAsBoolean()) return 0; 
		
		workNodes[0].add(s);
		int current = 0;
		int next = 1;
		found = true;
		scanned = 1;
		
		while (true) {
			for (Node node1: workNodes[current]) {
				n1 = node1;
				for (Node node2 : node1.links) {
					if (node2 == s || backTrack.containsKey(node2)) continue;
					n2 = node2;
					if (!moveCondition.getAsBoolean()) continue;
					backTrack.put(node2, node1);
					workNodes[next].add(node2);
					scanned++;
					if (end.getAsBoolean()) return depth; 
				}
			}
			if (workNodes[next].isEmpty()) {
				found = false;
				break;
			}
			current ^= 1;
			next ^= 1;
			depth++;
		}

		return depth;
	}

	// This includes the start and the end. The order is start -> end.
	public Ln shortestPath() { return shortestPath(n2); }
	public Ln shortestPath(Node n) {
		if (n == start) return Ln.of(start);
		Node p = backTrack.get(n);
		if (p == null) return null;
		Ln track = new Ln();
		do {
			track.add(p);
			p = backTrack.get(p);
		} while (p != null); 
		track.add(n);
		Collections.reverse(track);
		return track;
	}
}
