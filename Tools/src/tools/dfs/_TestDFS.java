package tools.dfs;

import java.util.List;

import tools.misc.Copyable;

public class _TestDFS {
	static int nn = 0;

	public static void main(String[] args) {
		Zz z = new Zz();
		var d = new DFSPermutation<String, Zz>(new String[] { "A", "B", "C", "D", "E", "F", "G", "H" }, z);
		List<String> l;
		do {
			l = d.findNext((zz, s) -> {
				zz.s += s;
				zz.score += (int) s.charAt(0) * (10 - d.depth);
				if (s.equals("A") || zz.score > 2500) zz.score = Integer.MIN_VALUE;
				//System.out.println(++nn + " " + zz.s + " " + zz.score);
				return zz.score;
			});
			System.out.println("Result: " + l);
			System.out.println("Best: " + d.best);
			System.out.println("Max: " + d.max);
			System.out.println("Visited: " + d.visited);
			System.out.println("Valid: " + d.validNodes);
		} while (l != null);
	}


	public static class Zz implements Copyable<Zz> {
		String s = "";
		int score = 0;
		@Override
		public void copyTo(Zz a) {
			a.s = s;
			a.score = score;
		}
	}
}
