package training;

import java.util.List;
import java.util.function.BiConsumer;

import tools.math.Num;
import tools.scanner.Scan;

public class IsoContest_3 {

	public static void main(String[] args) {
		int n = Scan.readInt();
		int v = Scan.readInt();
		Cell[] ms = new Cell[v];
		for (int i = 0; i < v; i++) ms[i] = new Cell();
		Tree<Cell> tree = new Tree<>(ms);
		for (int i = 0; i < n; i++) tree.link(ms[Scan.readInt()], ms[Scan.readInt()]);
		for (Cell c : tree.getLeafs()) c.score = 1;
		tree.propagateLeafsToRoots((cell, father) -> { father.score += cell.score; });
		System.out.println(Num.max(tree.getRoots(), c -> c.score).id);
	}

	static class Cell extends TreeNode<Cell> { int score = 0; }






	static class TreeNode<A> {
		public static int count = 0;
		final Object id;
		public TreeNode(Object o) { id = o; count++; }
		public TreeNode() { this(count); }
		public List<A> children;
		public List<A> parents;
	}
	

	static class Tree<A extends TreeNode<A>> {
		public Tree() {}
		public Tree(A[] cells) {}
		public Tree(Iterable<A> cells) {}
		public void add(A cell) {}
		public void remove(A cell) {}
		public boolean isLeaf(A m) { return false; }
		public boolean isRoot(A m) { return false; }
		public void link(A father, A child) {}
		public void propagateLeafsToRoots(BiConsumer<A, A> csm) {}
		public void propagateRootsToLeafs(BiConsumer<A, A> csm) {}
		public List<A> getNodes() { return null; }
		public List<A> getLeafs() { return null; }
		public List<A> getRoots() { return null; }
	}
}

