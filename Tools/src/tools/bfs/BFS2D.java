package tools.bfs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BooleanSupplier;

import tools.tuple.Pos;

public final class BFS2D {

	public final int USED_BIT = 1<<28;

	public final int[][] tab;
	public boolean found;
	public int scanned; // includes start
	public int l1;
	public int c1;
	public int l2;
	public int c2;
	public int v1;
	public int v2;
	public int startL;
	public int startC;
	
	public BooleanSupplier moveCondition;
	public BooleanSupplier endCondition;

	public final int lineNb;
	public final int colNb;

	private final int[] t;
	private final int[] workLines;
	private final int[] workCols;
	private boolean clean = true; 
	private int newStart;
	private int mid;
	private int newN = 0;

	// t is in int[line][col] format.
	// bits 28 to 30 are reserved for use & backtracking purposes.
	public BFS2D(int[][] table) {
		tab = table;
		lineNb = tab.length;
		colNb = tab[0].length;
		t = new int[lineNb * colNb];
		for (int i = 0; i < lineNb; i++) for (int j = 0; j < colNb; j++) t[i * colNb + j] = tab[i][j];
		mid = Integer.highestOneBit((2 * lineNb + 2 * colNb) - 1) * 2; // Ceiling power of 2 
		workCols = new int[2 * mid];
		workLines = new int[2 * mid];
	}

	public int diffuse(Pos s, int wall, Pos e) { return diffuse(s.l, s.c, () -> v2 != wall, () -> e.l == l2 && e.c == c2); }
	public int diffuse(Pos s, int wall, BooleanSupplier end) { return diffuse(s.l, s.c, () -> v2 != wall, end); }
	public int diffuse(Pos s, BooleanSupplier move, Pos e) { return diffuse(s.l, s.c, move, () -> e.l == l2 && e.c == c2); }
	public int diffuse(Pos s, BooleanSupplier move, BooleanSupplier end) { return diffuse(s.l, s.c, move, end); }
	public int diffuse(int startLine, int startCol, int wall, int endLine, int endCol) { return diffuse(startLine, startCol, () -> v2 != wall, () -> endLine == l2 && endCol == c2); }
	public int diffuse(int startLine, int startCol, BooleanSupplier move, int endLine, int endCol) { return diffuse(startLine, startCol, move, () -> endLine == l2 && endCol == c2); }
	public int diffuse(int startLine, int startCol, int wall, BooleanSupplier end) { return diffuse(startLine, startCol, () -> v2 != wall, end); }

	public int diffuse(int startLine, int startCol, BooleanSupplier move, BooleanSupplier end) {
		moveCondition = move;
		endCondition = end;
		if (!clean) for (int i = 0; i < lineNb; i++) for (int j = 0; j < colNb; j++) t[i * colNb + j] &= ~(7<<28);
		clean = false;

		startL = startLine;
		startC = startCol;
		int turn = 1;
		int oldN = 1;
		workLines[0] = startLine;
		workCols[0] = startCol;
		t[startLine * colNb + startCol] |= USED_BIT;
		int oldStart = 0;
		newN = 0;
		newStart = mid;
		found = true;
		scanned = 1;

		Loop: while (true) {
			for (int i = 0; i < oldN; i++) {
				l1 = workLines[oldStart | i];
				c1 = workCols[oldStart | i];
				v1 = t[l1 * colNb + c1] & ~(7<<28);
				l2 = l1;
				c2 = c1 + 1;
				if (check(USED_BIT | 0<<29)) break Loop;
				c2 = c1 - 1;
				if (check(USED_BIT | 1<<29)) break Loop;
				c2 = c1;
				l2 = l1 + 1;
				if (check(USED_BIT | 2<<29)) break Loop;
				l2 = l1 - 1;
				if (check(USED_BIT | 3<<29)) break Loop;
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

	private boolean check(int info) {
		if (l2 < 0 || l2 >= lineNb || c2 < 0 || c2 >= colNb) return false;
		v2 = t[l2 * colNb + c2];
		if ((v2 & USED_BIT) != 0 || !moveCondition.getAsBoolean()) return false;
		scanned++;
		t[l2 * colNb + c2] = v2 | info;
		if (endCondition.getAsBoolean()) return true;
		workLines[newStart | newN] = l2;
		workCols[newStart | newN] = c2;
		newN++;
		return false;
	}

	public List<Pos> backTrack(Pos p) { return backTrack(p.l, p.c); }
	public List<Pos> backTrack(int l, int c) {
		if (!found) return null;
		List<Pos> track = new ArrayList<>();
		while (l != startL || c != startC) {
			track.add(new Pos(l, c));
			int d = t[l * colNb + c] & (3<<29);
			if (d == 0) c--;
			else if (d == 1<<29) c++;
			else if (d == 1<<30) l--;
			else l++;
		}
		Collections.reverse(track);
		return track;
	}

	public Pos next(Pos p) { return next(p.l, p.c); }
	public Pos next(int l, int c) {
		if (!found) return null;
		int dl = -1;
		int dc = -1;
		while (l != startL || c != startC) {
			dl = l;
			dc = c;
			int d = t[l * colNb + c] & (3<<29);
			if (d == 0) c--;
			else if (d == 1<<29) c++;
			else if (d == 1<<30) l--;
			else l++;
		}
		return dl == -1 ? null : new Pos(dl, dc);
	}
}
