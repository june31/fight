package aoc.done;

import tools.bfs.BFS2D;
import tools.scanner.Scan;
import tools.tables.Table;
import tools.tuple.Pos;

public class Day_21_2 {

	private static int[][] map = Scan.readMap0();
	private static int L = map.length;
	private static int C = map[0].length;
	private static Pos pS = Table.find(map, 'S');

	public static void main(String[] args) {
		int[] calc246 = new int[3];
		for (int f = 0; f < 3; f++) calc246[f] = calc(f*2 + 2);
		var abc = solveForABC(calc246);

		// Interpolate "Ax² + Bx + C" for 202300 = (26501365 - 65) / 131 
		System.out.println((long) (abc[0] * 202300 * 202300 + abc[1] * 202300 + abc[2]));
	}

	private static int calc(int f) {
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
	
	// Retrieve A, B, C for Ax² + Bx + C = z, x = 2, 4, 6 and z=calc(2), calc(4), calc(6) 
    public static double[] solveForABC(int[] calc246) {
        double[][] inverseMatrix = { { 0.125, -0.25, 0.125 }, { -1.25, 2.0, -0.75 }, { 3.0, -3.0, 1.0 } };
        double[] result = new double[3];
        for (int i = 0; i < 3; i++) for (int j = 0; j < 3; j++) result[i] += inverseMatrix[i][j] * calc246[j];
        return result;
    }
}
