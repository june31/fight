package training;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

import tools.math.Num;
import tools.scanner.Scan;

public class IsoContest_3 {

	public static void main(String[] args) {
		int n = Scan.readInt();
		int v = Scan.readInt();
		Cell[] ms = new Cell[v];
		for (int i = 0; i < v; i++) ms[i] = new Cell(i);
		Tree<Cell> tree = new Tree<>(ms);
		for (int i = 0; i < n; i++) tree.setChild(ms[Scan.readInt()], ms[Scan.readInt()]);
		for (Cell c : tree.getLeafs()) c.score = 1;
		tree.propagateLeafsToRoots((cell, father) -> { father.score += cell.score; });
		System.out.println(Num.max(tree.getRoots(), c -> c.score).id);
	}

	static class Cell {
		int score = 0;
		int id;
		public Cell(int i) { id = i; }
	}







	static class Tree<A> {
		public Tree() {}
		public Tree(A[] cells) {}
		public Tree(Collection<A> cells) {}
		public void add(A cell) {}
		public void remove(A cell) {}
		public boolean isLeaf(A m) { return false; }
		public boolean isRoot(A m) { return false; }
		public void setChild(A father, A child) {}
		public void propagateLeafsToRoots(BiConsumer<A, A> csm) {}
		public void propagateRootsToLeafs(BiConsumer<A, A> csm) {}
		public List<A> getCells() { return null; }
		public List<A> getLeafs() { return null; }
		public List<A> getRoots() { return null; }
	}
}

