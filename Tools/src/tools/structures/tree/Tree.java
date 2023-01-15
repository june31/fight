package tools.structures.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

import tools.structures.tree.node.TreeNode;

public class Tree<N extends TreeNode<N>> {
	private boolean clean = true;
	private List<N> nodes = new ArrayList<>();
	private List<N> leafs = new ArrayList<>();
	private List<N> roots = new ArrayList<>();
	
	public Tree() {}
	public Tree(N[] nodes) { for (N a : nodes) this.nodes.add(a); clean = nodes.length == 0; }
	public Tree(Collection<N> nodes) { this.nodes.addAll(nodes); clean = nodes.size() == 0; }
	
	public void add(N node) { nodes.add(node); clean = false; }
	public void remove(N node) { nodes.remove(node); clean = false; }
	public void link(N parent, N child) { parent.children.add(child); child.parents.add(parent); clean = false;}
	public List<N> getNodes() { return nodes; }
	public List<N> getLeafs() { if (!clean) init(); return leafs; }
	public List<N> getRoots() { if (!clean) init(); return roots; }

	public void propagateLeafsToRoots(BiConsumer<N, N> action) {
		if (!clean) init();
		for (N n : nodes) { n.$ = 0; }
		List<N> current = new ArrayList<>(leafs);
		List<N> next = new ArrayList<>();
		while (!current.isEmpty()) {
			for (N n : current) for (N p : n.parents) {
				action.accept(n, p);
				if (++p.$ == p.children.size()) next.add(p);
			}
			List<N> tmp = next;
			next = current;
			next.clear();
			current = tmp;
		}
	}
	
	public void propagateRootsToLeafs(BiConsumer<N, N> action) {
		if (!clean) init();
		for (N n : nodes) { n.$ = 0; }
		List<N> current = new ArrayList<>(roots);
		List<N> next = new ArrayList<>();
		while (!current.isEmpty()) {
			for (N n : current) for (N c : n.children) {
				action.accept(n, c);
				if (++c.$ == c.parents.size()) next.add(c);
			}
			List<N> tmp = next;
			next = current;
			next.clear();
			current = tmp;
		}
	}
	
	private void init() {
		leafs.clear();
		roots.clear();
		for (N n : nodes) {
			if (n.children.isEmpty()) leafs.add(n);
			if (n.parents.isEmpty()) roots.add(n);
		}
		clean = true;
	}
}