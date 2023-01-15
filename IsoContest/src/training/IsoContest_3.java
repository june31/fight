package training;

import tools.math.Num;
import tools.scanner.Scan;
import tools.structures.tree.Tree;
import tools.structures.tree.node.TreeNode;

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
}

