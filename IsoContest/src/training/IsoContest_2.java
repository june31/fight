package training;

import java.util.List;
import java.util.function.BiConsumer;

import tools.scanner.Scan;

public class IsoContest_2 {

	static int max = -1;
	static Cell best;

	public static void main(String[] args) {
		int n = Scan.readInt();
		int v = Scan.readInt();
		Cell[] ms = new Cell[v];
		Tree<Cell> tree = new Tree<>();
		for (int i = 0; i < v; i++) {
			ms[i] = new Cell(i);
			tree.add(ms[i]);
		}
		for (int i = 0; i < n; i++) tree.addChild(ms[Scan.readInt()], ms[Scan.readInt()]);
		
		for (Cell c : tree.getLeafs()) c.score = 1;

		tree.propagateLeafsToRoots((cell, father) -> {
			father.score += cell.score;
			if (father.score > max) {
				max = father.score;
				best = father;
			}
		});
		System.out.println(best.id);
	}
	
	static class Cell {
		int score = 0;
		private int id;
		public Cell(int i) { id = i; }
	}
	
	
	
	
	
	
	
	static class Tree<A> {
		public void add(A cell) {}
		public boolean isLeaf(A m) { return false; }
		public void addChild(A cell1, A cell2) {}
		public void propagateLeafsToRoots(BiConsumer<A, A> csm) {}
		public List<A> getLeafs() { return null; }
	}
}
