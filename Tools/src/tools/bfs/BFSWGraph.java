package tools.bfs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BooleanSupplier;

import tools.structures.graph.Graph;
import tools.structures.graph.node.Node;

public final class BFSWGraph {

	public final Graph g;
	public boolean found;
	public int scanned; // includes start
	public Node n1;
	public Node n2;
	public int depth;
	private int graphSize;
	
	private final int[] backTrack;
	private final Node[] workNodes;

	public BFSWGraph(Graph graph) {
		g = graph;
		graphSize = g.size();
		backTrack = new int[graphSize];
		workNodes = new Node[2 * graphSize];
	}

	public int diffuse(Node s) { return diffuse(s, () -> false, () -> true); }
	public int diffuse(Node s, Node e) { return diffuse(s, () -> n2 == e, () -> true); }
	public int diffuse(Node s, Node e, BooleanSupplier moveCondition) { return diffuse(s, () -> n2 == e, moveCondition); }
	public int diffuse(Node s, BooleanSupplier end)  { return diffuse(s, end, () -> true); }
	public int diffuse(Node s, BooleanSupplier end, BooleanSupplier moveCondition) {
		for (int i = 0; i < backTrack.length; i++) backTrack[i] = -2;
		depth = 0;
		n1 = n2 = s;
		backTrack[s.id] = -1;
		if (end.getAsBoolean()) return 0; 
		
		workNodes[0] = s;
		int oldN = 1;
		int oldStart = 0;
		int newN = 0;
		int newStart = graphSize;
		found = true;
		scanned = 1;
		
		while (true) {
			for (int i = 0; i < oldN; i++) {
				n1 = workNodes[i + oldStart];
				for (Node n : n1.links) {
					int back = backTrack[n.id];
					if (back > -2) continue;
					n2 = n;
					if (!moveCondition.getAsBoolean()) continue;
					backTrack[n.id] = n1.id;
					workNodes[newStart + newN++] = n;
					scanned++;
					if (end.getAsBoolean()) return depth; 
				}
			}
			if (newN == 0) {
				found = false;
				break;
			}

			int tmp = oldStart;
			oldStart = newStart;
			newStart = tmp;
			oldN = newN;
			newN = 0;
			depth++;
		}

		return depth;
	}

	// This includes the start and the end. The order is start -> end.
	public List<Node> shortestPath() { return shortestPath(n2); }
	public List<Node> shortestPath(Node n) {
		if (!found || backTrack[n.id] == -2) return null;
		List<Node> track = new ArrayList<>();
		while (backTrack[n.id] != -1) {
			track.add(n);
			n = Node.all.get(backTrack[n.id]);
		}
		track.add(n);
		Collections.reverse(track);
		return track;
	}
}
