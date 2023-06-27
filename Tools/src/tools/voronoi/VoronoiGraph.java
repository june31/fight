package tools.voronoi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.BooleanSupplier;

import tools.structures.graph.Graph;
import tools.structures.graph.node.Node;

public final class VoronoiGraph {

	// nodeOwners possible values:
	// s >= 0: cell belongs to player s (includes starting position)
	// s = -1: cell is forbidden (conflict when no priority)
	// s = -2: cell has not been reached
	// s = -3 - p: cell tentatively belongs to player p (transient state when no priority) 
	
	public final Graph g;
	public int[] areas; // includes start positions
	public Node n1;
	public Node n2;
	public int id1;
	public int id2;
	public int index;
	public int depth;
	public int[] nodeOwners;
	private int graphSize;
	private Node[] backTrack;

	public VoronoiGraph(Graph graph) {
		g = graph;
		graphSize = g.size();
		nodeOwners = new int[graphSize];
		backTrack = new Node[graphSize];
	}

	public int diffuse(int[] is, boolean priority) { return diffuse(is, () -> true, priority); }
	public int diffuse(int[] is, BooleanSupplier move, boolean priority) {
		Node[] ps = new Node[is.length];
		for (int i = 0; i < ps.length; i++) ps[i] = g.getNode(is[i]);
		return diffuse(ps, () -> true, priority);
	}
	public int diffuse(Node[] ps, boolean priority) { return diffuse(ps, () -> true, priority); }
	public int diffuse(Node[] ps, BooleanSupplier move, boolean priority) {
		int l = ps.length;
		areas = new int[l];
		for (int i = 0; i < graphSize; i++) { nodeOwners[i] = -2; backTrack[i] = null; }
		List<List<Node>> workNodes = new ArrayList<>();
		List<List<Node>> nextNodes = new ArrayList<>();
		for (int i = 0; i < l; i++) {
			List<Node> work = new ArrayList<>();
			Node n = ps[i];
			work.add(n);
			workNodes.add(work);
			nextNodes.add(new ArrayList<>());
			nodeOwners[n.id] = i;
			areas[i] = 1;
		}
		depth = 0;
		
		Loop: while (true) {
			for (index = 0; index < l; index++) {
				List<Node> work = workNodes.get(index);
				List<Node> next = nextNodes.get(index);
				for (Node node : work) {
					n1 = node;
					id1 = n1.id;
					for (Node n : n1.links) {
						n2 = n;
						id2 = n.id;
						int status = nodeOwners[id2];
						if (status >= -1 || status == -3 - index) continue;
						if (!move.getAsBoolean()) continue;
						if (status != -2) { // May only happen if no priority 
							nodeOwners[id2] = -1;
							backTrack[id2] = null;
							continue;
						}
						nodeOwners[id2] = priority ? index : -3 - index;
						backTrack[id2] = n1;
						next.add(n2);
					}
				}
				work.clear();
			}
			int newN = 0;
			for (index = 0; index < l; index++) {
				List<Node> next = nextNodes.get(index);
				if (!priority) {
					Iterator<Node> it = next.iterator();
					while (it.hasNext()) {
						Node n = it.next();
						if (nodeOwners[n.id] == -1) it.remove();
						else nodeOwners[n.id] = index;
					}
				}
				newN += next.size();
				areas[index] += next.size();
			}
			if (newN == 0) break Loop;
			List<List<Node>> tmp = workNodes;
			workNodes = nextNodes;
			nextNodes = tmp;
			depth++;
		}
		return depth;
	}
	
	// This includes the start and the end. The order is start -> end.
	public List<Node> shortestPath(Node n) {
		if (backTrack[n.id] == null) return null;
		List<Node> track = new ArrayList<>();
		while (backTrack[n.id] != null) {
			track.add(n);
			n = backTrack[n.id];
		}
		track.add(n);
		Collections.reverse(track);
		return track;
	}
}
