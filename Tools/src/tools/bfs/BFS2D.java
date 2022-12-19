package tools.bfs;

import tools.function.BiIntPredicate;
import tools.function.QuadIntPredicate;

public class BFS2D {
	
	public final int[][] tab;
	public boolean found;
	public int scanned; // includes start
	private final int lineNb;
	private final int colNb;
	private final int[] workLines;
	private final int[] workCols;
	private final int mid;
	private boolean clean = true; 
	private final boolean[] used;
	private int newStart;
	private int newN = 0;

	// t is in int[line][col] format.
	public BFS2D(int[][] t) {
		tab = t;
		lineNb = tab.length;
		colNb = tab[0].length;
		mid = Integer.highestOneBit((2 * lineNb + 2 * colNb) - 1) * 2; // Ceiling power of 2 
		workCols = new int[2 * mid];
		workLines = new int[2 * mid];
		used = new boolean[lineNb * colNb];
	}
	
	public int diffuse(int startLine, int startCol) {
		return diffuse(startLine, startCol, (a, b, c, d) -> true, (a, b) -> false);
	}

	public int diffuse(int startLine, int startCol, QuadIntPredicate condition) {
		return diffuse(startLine, startCol, condition, (a, b) -> false);
	}

	public int diffuse(int startLine, int startCol, BiIntPredicate end) {
		return diffuse(startLine, startCol, (a, b, c, d) -> true, end);
	}

	public int diffuse(int startLine, int startCol, QuadIntPredicate condition, BiIntPredicate end) {
		if (!clean) for (int i = 0; i < used.length; i++) used[i] = false; 
		clean = false;
		
		int turn = 1;
		int oldN = 1;
		workLines[0] = startLine;
		workCols[0] = startCol;
		used[startLine * colNb + startCol] = true;
		int oldStart = 0;
		newN = 0;
		newStart = mid;
		found = true;
		scanned = 1;
		
		Loop: while (true) {
			for (int i = 0; i < oldN; i++) {
				int line = workLines[oldStart | i], col = workCols[oldStart | i];
				if (check(line, col, line, col-1, condition, end)) break Loop;
				if (check(line, col, line, col+1, condition, end)) break Loop;
				if (check(line, col, line-1, col, condition, end)) break Loop;
				if (check(line, col, line+1, col, condition, end)) break Loop;
			}
			if (newN == 0) {
				found = false;
				break;
			}
			
			oldStart ^= mid;
			newStart ^= mid;
			oldN = newN;
			newN = 0;
			turn++;
		}
		
		return turn;
	}

	private boolean check(int sourceLine, int sourceCol, int destLine, int destCol, QuadIntPredicate condition, BiIntPredicate end) {
		if (destLine < 0 || destLine >= lineNb || destCol < 0 || destCol >= colNb
				|| used[destLine * colNb + destCol]
				|| !condition.test(sourceLine, sourceCol, destLine, destCol)) return false;
		scanned++;
		if (end.test(destLine, destCol)) return true;
		used[destLine * colNb + destCol] = true;
		workLines[newStart | newN] = destLine; 
		workCols[newStart | newN] = destCol;
		newN++;
		return false;
	}
}
