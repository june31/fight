package tooltests.dfs;

import java.util.List;

import tools.dfs.DFSFastSubSet;
import tools.misc.Copyable;

public class TST_DFSFastSubSet_Binary {
	static int n1 = 31;
	static int n2 = 48;
	static int n3 = 193;

	public static void main(String[] args) {
		Zz z = new Zz();
		var d = new DFSFastSubSet<String, Zz>(new String[] { "A", "B", "C", "D", "E", "F", "G", "H" }, z, false);
		List<String> l;
		do {
			l = d.findNext((zz, s) -> {
				zz.score += 1<<('H' - s.charAt(0));
				if (zz.score > n3) return Double.NEGATIVE_INFINITY;
				if (zz.score == n1) return Double.POSITIVE_INFINITY;
				if (zz.score == n2) return Double.POSITIVE_INFINITY;
				if (zz.score == n3) return Double.POSITIVE_INFINITY;
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
		int score = 0;
		@Override
		public void copyTo(Zz a) {
			a.score = score;
		}
	}
}
