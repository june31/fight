package tools.bfs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BooleanSupplier;

import tools.structures.graph.WGraph;
import tools.structures.graph.node.WNode;

public final class BFSWGraph {

	public final WGraph g;
	public boolean found;
	public int scanned; // includes start
	public WNode n1;
	public WNode n2;
	public int turn;
	private int graphSize;
	private int maxWeight = 0;

	private final int[] backTrack;

	public BFSWGraph(WGraph graph) {
		g = graph;
		graphSize = g.size();
		backTrack = new int[graphSize];
		for (WNode n: graph.getNodes()) if (n.weight > maxWeight) maxWeight = n.weight;
	}

	public int diffuse(WNode s) { return diffuse(s, () -> false, () -> true); }
	public int diffuse(WNode s, WNode e) { return diffuse(s, () -> n2 == e, () -> true); }
	public int diffuse(WNode s, WNode e, BooleanSupplier moveCondition) { return diffuse(s, () -> n2 == e, moveCondition); }
	public int diffuse(WNode s, BooleanSupplier end)  { return diffuse(s, end, () -> true); }
	public int diffuse(WNode s, BooleanSupplier end, BooleanSupplier moveCondition) {
		List<List<WNode>> allNodes = new ArrayList<>(maxWeight + 1);
		for (int i = 0; i <= maxWeight; i++) allNodes.add(new ArrayList<>());
		for (int i = 0; i < backTrack.length; i++) backTrack[i] = -2;
		int index = 0;
		turn = 0;
		n1 = n2 = s;
		backTrack[s.id] = -1;
		if (end.getAsBoolean()) return 0; 

		allNodes.get(0).add(s);
		found = true;
		scanned = 1;
		int drought = 0;

		while (true) {
			List<WNode> current = allNodes.get(index);
			if (current.size() > 0) {
				drought = index;
				for (int i = 0; i < current.size(); i++) {
					n1 = current.get(i);
					for (WNode n : n1.links) {
						int back = backTrack[n.id];
						if (back > -2) continue;
						n2 = n;
						if (!moveCondition.getAsBoolean()) continue;
						backTrack[n.id] = n1.id;
						allNodes.get((index + n.weight) % (maxWeight + 1)).add(n);
						scanned++;
						if (end.getAsBoolean()) return turn; 
					}
				}
				current.clear();
			}
			turn++;
			index = (index + 1) % (maxWeight + 1);
			if (index == drought) return turn - maxWeight - 1;
		}
	}

	// This includes the start and the end. The order is start -> end.
	public List<WNode> shortestPath() { return shortestPath(n2); }
	public List<WNode> shortestPath(WNode n) {
		if (!found || backTrack[n.id] == -2) return null;
		List<WNode> track = new ArrayList<>();
		while (backTrack[n.id] != -1) {
			track.add(n);
			n = WNode.all.get(backTrack[n.id]);
		}
		track.add(n);
		Collections.reverse(track);
		return track;
	}
}
