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
	private BooleanSupplier end = () -> false;
	private BooleanSupplier moveCondition = () -> true;
	
	public final Map<Node, Node> backTrack = new HashMap<>();
	private final Ln[] workNodes = new Ln[2];

	public BFSGraph end(Node e) { end = () -> n2 == e; return this; }
	public BFSGraph end(BooleanSupplier bs) { end = bs; return this; }
	public BFSGraph move(BooleanSupplier bs) { end = bs; return this; }
	
	public BFSGraph reach(Node s, Node e) { end(e); return diffuse(s); }
	public BFSGraph diffuse(Node s) {
		start = s;
		backTrack.clear();
		depth = 0;
		n1 = n2 = s;
		if (end.getAsBoolean()) return this; 
		workNodes[0] = new Ln();
		workNodes[0].add(s);
		workNodes[1] = new Ln();
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
					if (end.getAsBoolean()) return this; 
				}
			}
			if (workNodes[next].isEmpty()) {
				found = false;
				break;
			}
			current ^= 1;
			next ^= 1;
			workNodes[next].clear();
			depth++;
		}

		return this;
	}

	// This includes the start and the end. The order is start -> end.
	public Ln shortestPath() { return shortestPath(n2); }
	public Ln shortestPath(Node n) {
		Node p = backTrack.get(n);
		if (p == null) return null;
		Ln track = Ln.of(n);
		do {
			track.add(p);
			p = backTrack.get(p);
		} while (p != null); 
		Collections.reverse(track);
		return track;
	}
}
