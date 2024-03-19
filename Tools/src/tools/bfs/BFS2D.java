package tools.bfs;

import java.util.Collections;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;

import tools.collections.pos.Lp;
import tools.tables.Table;
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
	public int turn;
	
	public BooleanSupplier moveCondition;
	public BooleanSupplier endCondition;
	public Runnable sideEffect = () -> {};
	private boolean firstEffect = false;

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

	public Lp reach(Pos s, int wall, Pos e) { diffuse(s.l, s.c, () -> v2 != wall, () -> e.l == l2 && e.c == c2, true); return shortestPath(e); }
	public Lp reach(Pos s, BooleanSupplier move, Pos e) { diffuse(s.l, s.c, move, () -> e.l == l2 && e.c == c2, false); return shortestPath(e); }
	public int diffuse(int s, int wall) { return diffuse(Table.find(tab, s), wall); }
	public int diffuse(int s, int wall, Pos e) { return diffuse(Table.find(tab, s), wall, e); }
	public int diffuse(int s, int wall, BooleanSupplier end) { return diffuse(Table.find(tab, s), wall, end); }
	public int diffuse(int s, BooleanSupplier move, Pos e) { return diffuse(Table.find(tab, s), move, e); }
	public int diffuse(int s, BooleanSupplier move, BooleanSupplier end) { return diffuse(Table.find(tab, s), move, end); }
	public int diffuse(Pos s, int wall) { return diffuse(s.l, s.c, () -> v2 != wall, () -> false, true); }
	public int diffuse(Pos s, int wall, Pos e) { return diffuse(s.l, s.c, () -> v2 != wall, () -> e.l == l2 && e.c == c2, true); }
	public int diffuse(Pos s, int wall, BooleanSupplier end) { return diffuse(s.l, s.c, () -> v2 != wall, end, true); }
	public int diffuse(Pos s, BooleanSupplier move, Pos e) { return diffuse(s.l, s.c, move, () -> e.l == l2 && e.c == c2, false); }
	public int diffuse(Pos s, BooleanSupplier move, BooleanSupplier end) { return diffuse(s.l, s.c, move, end, false); }
	public int diffuse(int startLine, int startCol, int wall) { return diffuse(startLine, startCol, () -> v2 != wall, () -> false, true); }
	public int diffuse(int startLine, int startCol, int wall, int endLine, int endCol) { return diffuse(startLine, startCol, () -> v2 != wall, () -> endLine == l2 && endCol == c2, true); }
	public int diffuse(int startLine, int startCol, BooleanSupplier move, int endLine, int endCol) { return diffuse(startLine, startCol, move, () -> endLine == l2 && endCol == c2, false); }
	public int diffuse(int startLine, int startCol, int wall, BooleanSupplier end) { return diffuse(startLine, startCol, () -> v2 != wall, end, true); }
	public int diffuse(int startLine, int startCol, BooleanSupplier move, BooleanSupplier end) { return diffuse(startLine, startCol, move, end, false); }
	public int diffuse(int startLine, int startCol, BooleanSupplier move, BooleanSupplier end, boolean testStart) {
		moveCondition = move;
		endCondition = end;
		if (!clean) for (int i = 0; i < t.length; i++) t[i] &= ~(7<<28);
		clean = false;

		startL = startLine;
		startC = startCol;
		turn = 0;
		int oldN = 1;
		workLines[0] = startLine;
		workCols[0] = startCol;
		int oldStart = 0;
		newN = 0;
		newStart = mid;
		found = true;
		scanned = 0;
		l2 = startLine;
		c2 = startCol;
		v2 = t[startLine * colNb + startCol];
		if (firstEffect) sideEffect.run();
		if (endCondition.getAsBoolean()) return 0;
		if (testStart && !move.getAsBoolean()) return 0;
		scanned = 1;
		t[startLine * colNb + startCol] = v2 | USED_BIT;
		turn = 1;
		
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

	public void setSideEffect(boolean onStart, Runnable r) {
		firstEffect = onStart;
		sideEffect = r;
	}
	
	public void setSideEffect(boolean onStart, IntSupplier is) {
		firstEffect = onStart;
		sideEffect = () -> tab[l2][c2] = is.getAsInt();
	}

	public void setSideEffect(boolean onStart, int i) {
		firstEffect = onStart;
		sideEffect = () -> tab[l2][c2] = i;
	}

	private boolean check(int info) {
		if (l2 < 0 || l2 >= lineNb || c2 < 0 || c2 >= colNb) return false;
		v2 = t[l2 * colNb + c2];
		if ((v2 & USED_BIT) != 0 || !moveCondition.getAsBoolean()) return false;
		scanned++;
		t[l2 * colNb + c2] = v2 | info;
		sideEffect.run();
		if (endCondition.getAsBoolean()) return true;
		workLines[newStart | newN] = l2;
		workCols[newStart | newN] = c2;
		newN++;
		return false;
	}

	// This includes the start and the end. The order is start -> end.
	public Lp shortestPath() { return shortestPath(l2, c2); }
	public Lp shortestPath(Pos p) { return shortestPath(p.l, p.c); }
	public Lp shortestPath(int l, int c) {
		if ((t[l * colNb + c] & USED_BIT) == 0) return null;
		Lp track = new Lp();
		while (l != startL || c != startC) {
			track.add(new Pos(l, c));
			int d = t[l * colNb + c] & (3<<29);
			if (d == 0) c--;
			else if (d == 1<<29) c++;
			else if (d == 1<<30) l--;
			else l++;
		}
		track.add(new Pos(l, c));
		Collections.reverse(track);
		return track;
	}
}
