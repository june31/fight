package tools.structures.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

import tools.structures.tree.node.TreeNode;

public class Tree {
	private boolean clean = true;
	private List<TreeNode> nodes = new ArrayList<>();
	private List<TreeNode> leafs = new ArrayList<>();
	private List<TreeNode> roots = new ArrayList<>();
	
	public Tree() {}
	public Tree(TreeNode[] nodes) { for (TreeNode a : nodes) this.nodes.add(a); clean = nodes.length == 0; }
	public Tree(Collection<TreeNode> nodes) { this.nodes.addAll(nodes); clean = nodes.size() == 0; }
	
	public void add(TreeNode node) { nodes.add(node); clean = false; }
	public void link(TreeNode parent, TreeNode child) { parent.children.add(child); child.parents.add(parent); clean = false;}
	public List<TreeNode> getNodes() { return nodes; }
	public List<TreeNode> getLeafs() { if (!clean) init(); return leafs; }
	public List<TreeNode> getRoots() { if (!clean) init(); return roots; }
	public int size() { return nodes.size(); }

	public void propagateLeafsToRoots(BiConsumer<TreeNode, TreeNode> action) {
		if (!clean) init();
		for (TreeNode n : nodes) { n.$ = 0; }
		List<TreeNode> current = new ArrayList<>(leafs);
		List<TreeNode> next = new ArrayList<>();
		while (!current.isEmpty()) {
			for (TreeNode n : current) for (TreeNode p : n.parents) {
				action.accept(n, p);
				if (++p.$ == p.children.size()) next.add(p);
			}
			List<TreeNode> tmp = next;
			next = current;
			next.clear();
			current = tmp;
		}
	}
	
	public void propagateRootsToLeafs(BiConsumer<TreeNode, TreeNode> action) {
		if (!clean) init();
		for (TreeNode n : nodes) { n.$ = 0; }
		List<TreeNode> current = new ArrayList<>(roots);
		List<TreeNode> next = new ArrayList<>();
		while (!current.isEmpty()) {
			for (TreeNode n : current) for (TreeNode c : n.children) {
				action.accept(n, c);
				if (++c.$ == c.parents.size()) next.add(c);
			}
			List<TreeNode> tmp = next;
			next = current;
			next.clear();
			current = tmp;
		}
	}
	
	private void init() {
		leafs.clear();
		roots.clear();
		for (TreeNode n : nodes) {
			if (n.children.isEmpty()) leafs.add(n);
			if (n.parents.isEmpty()) roots.add(n);
		}
		clean = true;
	}
}