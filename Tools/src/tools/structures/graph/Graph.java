package tools.structures.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import tools.structures.graph.node.Node;

public class Graph {
	private List<Node> nodes = new ArrayList<>();
	
	public Graph() {}
	public Graph(Node[] nodes) { for (Node a : nodes) this.nodes.add(a); }
	public Graph(Collection<Node> nodes) { this.nodes.addAll(nodes); }
	
	public void add(Node node) { nodes.add(node); }
	public void singleLink(Node from, Node to) { from.links.add(to); }
	public void dualLink(Node a, Node b) { a.links.add(b); b.links.add(a); }
	public int size() { return nodes.size(); }
	public List<Node> getNodes() { return nodes; }
}