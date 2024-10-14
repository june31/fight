package tools.bfs;

import java.util.Collections;
import java.util.function.BooleanSupplier;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;

import tools.collections.pos.Lp;
import tools.function.BiIntConsumer;
import tools.function.BiIntPredicate;
import tools.function.BiIntToIntFunction;
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
	
	public BooleanSupplier moveCondition = () -> true;
	public BooleanSupplier endCondition = () -> false;
	public Runnable sideEffect = () -> {};
	private boolean firstEffect = true;

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

	public Lp reach(Pos s, Pos e) { moveCondition = () -> l2 == e.l && c2 == e.c; diffuse(s.l, s.c); return shortestPath(e); }
	public BFS2D diffuse(int s) { Pos p = Table.find(tab, s); return diffuse(p); }
	public BFS2D diffuse(Pos s) { return diffuse(s.l, s.c); }
	public BFS2D diffuse(int startLine, int startCol) {
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
		if (endCondition.getAsBoolean()) return this;
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

		return this;
	}

	public BFS2D disableFirstEffect() {
		firstEffect = false;
		return this;
	}		
	
	public BFS2D sideEffect(Runnable r) {
		sideEffect = r;
		return this;
	}
	
	public BFS2D sideEffect(int i) {
		sideEffect = () -> tab[l2][c2] = i;
		return this;
	}

	public BFS2D sideEffect(IntConsumer ic) {
		sideEffect = () -> ic.accept(v2);
		return this;
	}
	
	public BFS2D sideEffect(BiIntConsumer i2c) {
		sideEffect = () -> i2c.accept(l2, c2);
		return this;
	}

	public BFS2D setValue(IntUnaryOperator iuo) {
        sideEffect = () -> tab[l2][c2] = iuo.applyAsInt(v2);
   		return this;
	}

	public BFS2D setValue(IntSupplier is) {
		sideEffect = () -> tab[l2][c2] = is.getAsInt();
		return this;
	}

	public BFS2D setValue(BiIntToIntFunction i2if) {
		sideEffect = () -> tab[l2][c2] = i2if.apply(l2, c2);
		return this;
	}

	public BFS2D wall(int c) {
        moveCondition = () -> tab[l2][c2] != c;
		return this;
	}

	public BFS2D move(int c) {
        moveCondition = () -> tab[l2][c2] == c;
		return this;
	}

	public BFS2D move(BooleanSupplier bs) {
		moveCondition = bs;
		return this;
	}

	public BFS2D move(IntPredicate ip) {
		moveCondition = () -> ip.test(v2);
		return this;
	}

	public BFS2D move(BiIntPredicate ip) {
		moveCondition = () -> ip.test(l2, c2);
		return this;
	}

	public BFS2D end(BooleanSupplier bs) {
		endCondition = bs;
		return this;
	}

	public BFS2D end(int c) {
		endCondition = () -> v2 == c;
		return this;
	}

	public BFS2D end(Pos p) {
		endCondition = () -> l2 == p.l && c2 == p.c;
		return this;
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
