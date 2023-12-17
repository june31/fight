package tools.structures.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tools.structures.graph.node.WNode;

public class WGraph {
	private List<WNode> nodes = new ArrayList<>();
	private Map<Integer, WNode> nodeMap = new HashMap<>();
	
	public WGraph() {}
	public WGraph(WNode[] nodes) { for (WNode a : nodes) addNode(a); }
	public WGraph(Collection<WNode> nodes) { for (WNode a : nodes) addNode(a); }
	
	public void addNode() { addNode(new WNode()); }
	public void addNode(WNode node) { nodes.add(node); nodeMap.put(node.id, node); }
	public void singleLink(WNode from, WNode to) { from.links.add(to); }
	public void singleLink(int from, int to) { nodeMap.get(from).links.add(nodeMap.get(to)); }
	public void singleCut(WNode from, WNode to) { from.links.remove(to); }
	public void dualLink(WNode a, WNode b) { a.links.add(b); b.links.add(a); }
	public void dualLink(int a, int b) {
		WNode m = nodeMap.get(a);
		WNode n = nodeMap.get(b);
		m.links.add(n);
		n.links.add(m);
	}
	public void dualCut(WNode a, WNode b) { a.links.remove(b); b.links.remove(a); }
	public int size() { return nodes.size(); }
	public List<WNode> getNodes() { return nodes; }
	public WNode getNode(int id) { return nodeMap.get(id); }
}