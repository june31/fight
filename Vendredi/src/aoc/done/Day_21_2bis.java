package aoc.done;

import tools.bfs.BFS2D;
import tools.scanner.Scan;
import tools.solver.Solver;
import tools.tables.Table;
import tools.tuple.Pos;

public class Day_21_2bis {

	private static int[][] map = Scan.readMap0();
	private static int L = map.length;
	private static int C = map[0].length;
	private static Pos pS = Table.find(map, 'S');

	public static void main(String[] args) {
		// Extrapolate polynomial for 202300 = (26501365 - 65) / 131 
		System.out.println(Solver.getPolynomialValue(Solver.findPolynomial(x -> calc(x), 2), 202300));
	}

	private static long calc(int f) {
		int[] s = new int[1];
		int steps = f * L + L/2;
		int[][] m = new int[L + L*2*f][C + C*2*f];
		for (int l = 0; l < m.length; l++) for (int c = 0; c < m.length; c++) m[l][c] = map[l%L][c%C]; 
		BFS2D bfs = new BFS2D(m);
		bfs.diffuse(new Pos(pS.l + L*f, pS.c + C*f), '#', () -> {
			if (bfs.turn > steps) return true;
			if ((bfs.l2 + bfs.c2) % 2 == 1) s[0]++;
			return false;
		});
		return s[0];
	}
}
