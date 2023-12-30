package aoc.done;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;

import tools.scanner.Scan;

public class Day_25_2 {

	private static String[] input = Scan.readRawStrings();
	static List<Node> l;

	public static void main(String[] args) {

		Map<String, Node> map = new LinkedHashMap<>();
		for (var in: input) {
			String[] t = in.split(": ");
			Node n = new Node();
			n.name = t[0];
			if (!map.containsKey(t[0])) map.put(n.name, n);
			for (String u: t[1].split(" ")) {
				n = new Node();
				n.name = u;
				if (!map.containsKey(u)) map.put(n.name, n);
			}
		}
		for (var in: input) {
			String[] t = in.split(": ");
			Node n = map.get(t[0]);
			for (String u: t[1].split(" ")) {
				n.links.add(map.get(u));
				map.get(u).links.add(n);
			}
		}

		l = new ArrayList<>(map.values());
		for (int i = 0; i < l.size(); i++) {
			l.get(i).id = i;
		}

		Graph g = new Graph(l);
		BFSGraph bfs = new BFSGraph(g);
		Node n0 = l.get(0);
		long s = 0;
		for (int i = 1; i < l.size(); i++) {
			for (Node n: l) {
				n.links.addAll(n.deac);
				n.deac.clear();
			}
			Node ni = l.get(i);
			for (int j = 0; j < 4; j++) {
				bfs.diffuse(n0, ni);
				var sp = bfs.shortestPath();
				if (sp == null) { s++; break; } 
				for (int k = 0; k < sp.size() - 1; k++) {
					sp.get(k).links.remove(sp.get(k+1));
					sp.get(k+1).links.remove(sp.get(k));
					sp.get(k).deac.add(sp.get(k+1));
					sp.get(k+1).deac.add(sp.get(k));
				}
			}
		}
		System.out.println(s * (l.size() - s));
	}
}

class Node {
	public String name;
	public int id;
	public List<Node> links = new ArrayList<>();
	public List<Node> deac = new ArrayList<>();
	public Node() {}
	public String toString() {
		return name + links.stream().map(n->n.name).collect(Collectors.toList());
	}
}

class Graph {
	private List<Node> nodes = new ArrayList<>();
	private Map<Integer, Node> nodeMap = new HashMap<>();

	public Graph() {}
	public Graph(Node[] nodes) { for (Node a : nodes) addNode(a); }
	public Graph(Collection<Node> nodes) { for (Node a : nodes) addNode(a); }

	public void addNode() { addNode(new Node()); }
	public void addNode(Node node) { nodes.add(node); nodeMap.put(node.id, node); }
	public void singleLink(Node from, Node to) { from.links.add(to); }
	public void singleLink(int from, int to) { nodeMap.get(from).links.add(nodeMap.get(to)); }
	public void singleCut(Node from, Node to) { from.links.remove(to); }
	public void dualLink(Node a, Node b) { a.links.add(b); b.links.add(a); }
	public void dualLink(int a, int b) {
		Node m = nodeMap.get(a);
		Node n = nodeMap.get(b);
		m.links.add(n);
		n.links.add(m);
	}
	public void dualCut(Node a, Node b) { a.links.remove(b); b.links.remove(a); }
	public int size() { return nodes.size(); }
	public List<Node> getNodes() { return nodes; }
	public Node getNode(int id) { return nodeMap.get(id); }
}
final class BFSGraph {

	public final Graph g;
	public boolean found;
	public int scanned; // includes start
	public Node n1;
	public Node n2;
	public int depth;
	private int graphSize;

	private final int[] backTrack;
	private final Node[] workNodes;

	public BFSGraph(Graph graph) {
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
			n = Day_25_2.l.get(backTrack[n.id]);
		}
		track.add(n);
		Collections.reverse(track);
		return track;
	}
}
