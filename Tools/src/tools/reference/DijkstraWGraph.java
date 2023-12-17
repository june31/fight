package tools.reference;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import tools.structures.graph.WGraph;
import tools.structures.graph.node.WNode;

public class DijkstraWGraph {

	public static Map<WNode, Integer> process(WGraph graph, WNode startNode) {
		Map<WNode, Integer> distances = new HashMap<>();
		PriorityQueue<WNode> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));
		Set<WNode> settledNodes = new HashSet<>();

		// Initialize distances
		for (WNode node : graph.getNodes()) {
			distances.put(node, Integer.MAX_VALUE);
		}
		distances.put(startNode, 0);

		queue.add(startNode);

		while (!queue.isEmpty()) {
			WNode currentNode = queue.poll();
			settledNodes.add(currentNode);

			for (WNode neighbor : currentNode.links) {
				if (!settledNodes.contains(neighbor)) {
					int edgeDistance = currentNode.weight;
					int newDistance = distances.get(currentNode) + edgeDistance;
					if (newDistance < distances.get(neighbor)) {
						distances.put(neighbor, newDistance);
						queue.add(neighbor);
					}
				}
			}
		}

		return distances;
	}
}
