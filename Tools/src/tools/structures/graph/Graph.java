package tools.structures.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tools.structures.graph.node.Node;

public class Graph {
	private List<Node> nodes = new ArrayList<>();
	private Map<Integer, Node> nodeMap = new HashMap<>();
	
	public Graph() {}
	public Graph(Node[] nodes) { for (Node a : nodes) addNode(a); }
	public Graph(Collection<Node> nodes) { for (Node a : nodes) addNode(a); }
	
	public void addNode() { addNode(new Node()); }
	public void addNode(Node node) { nodes.add(node); nodeMap.put(node.id, node); }
	public void singleLink(Node from, Node to) { from.links.add(to); }
	public void dualLink(Node a, Node b) { a.links.add(b); b.links.add(a); }
	public int size() { return nodes.size(); }
	public List<Node> getNodes() { return nodes; }
	public Node getNode(int id) { return nodeMap.get(id); }
}