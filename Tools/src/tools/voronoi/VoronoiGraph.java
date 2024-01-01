package tools.voronoi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

import tools.collections.node.Ln;
import tools.structures.graph.node.Node;

public class VoronoiGraph {

	// nodeOwners possible values:
	// s >= 0: cell belongs to player s (includes starting position)
	// s = -1: cell is forbidden (conflict when no priority)
	// s = null: cell has not been reached
	
	public int[] areas; // includes start positions
	public Node n1;
	public Node n2;
	public int index;
	public int depth;
	public final Map<Node, Node> backTrack = new HashMap<>();
	public final Map<Node, Integer> nodeOwners = new HashMap<>();
	private final Map<Node, Integer> conflicts = new HashMap<>();
	public Node start;

	public int diffuse(List<Node> ps, boolean priority) { return diffuse(ps, () -> true, priority); }
	public int diffuse(List<Node> ps, BooleanSupplier move, boolean priority) {
		int playerNb = ps.size();
		backTrack.clear();
		nodeOwners.clear();
		areas = new int[playerNb];
		List<Ln> workNodes = new ArrayList<>();
		List<Ln> nextNodes = new ArrayList<>();
		for (int i = 0; i < playerNb; i++) {
			Ln work = new Ln();
			Node n = ps.get(i);
			work.add(n);
			workNodes.add(work);
			nextNodes.add(new Ln());
			nodeOwners.put(n, i);
			areas[i] = 1;
		}
		depth = 0;
		
		Loop: while (true) {
			conflicts.clear();
			for (index = 0; index < playerNb; index++) {
				Ln work = workNodes.get(index);
				Ln next = nextNodes.get(index);
				for (Node node : work) {
					n1 = node;
					for (Node n : n1.links) {
						n2 = n;
						Integer status = nodeOwners.get(n2);
						if (status != null && status >= -1 && (priority || !conflicts.containsKey(n2))) continue;
						if (!move.getAsBoolean()) continue;
						if (!priority && conflicts.containsKey(n2)) { 
							nodeOwners.put(n2, -1);
							backTrack.remove(n2);
							nextNodes.get(conflicts.get(n2)).remove(n2);
							continue;
						}
						nodeOwners.put(n2, index);
						backTrack.put(n2, n1);
						if (!priority) conflicts.put(n2, index);
						next.add(n2);
					}
				}
				work.clear();
			}
			boolean hasWork = false;
			for (int i = 0; i < playerNb; i++) {
				Ln next = nextNodes.get(i);
				hasWork |= !next.isEmpty();
				areas[i] += next.size();
			}
			if (!hasWork) break Loop;
			List<Ln> tmp = workNodes;
			workNodes = nextNodes;
			nextNodes = tmp;
			depth++;
		}
		return depth;
	}
	
	// This includes the start and the end. The order is start -> end.
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
