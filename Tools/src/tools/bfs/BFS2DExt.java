package tools.bfs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BooleanSupplier;

import tools.tuple.Pos;

public final class BFS2DExt {

	private final long USED_BIT = 1l<<31;
	
	public final int[][] tab;
	public boolean found;
	public int scanned; // includes start
	public int l1;
	public int c1;
	public int l2;
	public int c2;
	public int v1;
	public int v2;
	public int turn;
	
	public BooleanSupplier moveCondition;
	public BooleanSupplier endCondition;

	public final int lineNb;
	public final int colNb;

	// bits 0 to 30 store L.
	// bit 31 is reserved for use purposes.
	// bits 32 to 62 store C.
	private final long[] backtrack;

	private boolean clean = true;
	
	// See BFS2DHelper class for additional move strategies
	private Runnable[] moves = {
			() -> { c2++; },
			() -> { c2--; },
			() -> { l2++; },
			() -> { l2--; }
	};
	
	public BFS2DExt(int[][] table) {
		tab = table;
		lineNb = tab.length;
		colNb = tab[0].length;
		backtrack = new long[lineNb * colNb];
	}
	
	public void setMoves(Runnable... moves) { this.moves = moves; }

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
		List<Integer> currentL = new ArrayList<>();
		List<Integer> nextL = new ArrayList<>();
		List<Integer> currentC = new ArrayList<>();
		List<Integer> nextC = new ArrayList<>();
		currentL.add(startLine);
		currentC.add(startCol);
		moveCondition = move;
		endCondition = end;
		if (!clean) for (int i = 0; i < backtrack.length; i++) backtrack[i] = 0;
		clean = false;
		turn = 0;
		found = true;
		scanned = 0;
		l2 = startLine;
		c2 = startCol;
		v2 = tab[l2][c2];
		if (endCondition.getAsBoolean()) return 0;
		if (testStart && !move.getAsBoolean()) return 0;
		scanned = 1;
		backtrack[startLine * colNb + startCol] = -1;
		turn = 1;
		
		while (true) {
			for (int i = 0; i < currentL.size(); i++) {
				l1 = currentL.get(i);
				c1 = currentC.get(i);
				v1 = tab[l1][c1];
				for (int r = 0; r < moves.length; r++) {
					l2 = l1;
					c2 = c1;
					moves[r].run();
					if (l2 < 0 || l2 >= lineNb || c2 < 0 || c2 >= colNb) continue;
					long back = backtrack[l2 * colNb + c2];
					if (back != 0) continue;
					v2 = tab[l2][c2];
					if (!moveCondition.getAsBoolean()) continue;
					backtrack[l2 * colNb + c2] = USED_BIT | l1 | (((long) c1) << 32); 
					scanned++;
					if (endCondition.getAsBoolean()) return turn;
					nextL.add(l2);
					nextC.add(c2);
				}
			}
			if (nextL.size() == 0) {
				found = false;
				break;
			}

			List<Integer> tmp = currentL;
			currentL = nextL;
			nextL = tmp;
			nextL.clear();
			tmp = currentC;
			currentC = nextC;
			nextC = tmp;
			nextC.clear();
			turn++;
		}

		return turn;
	}

	// This includes the start and the end. The order is start -> end.
	public List<Pos> shortestPath() { return shortestPath(l2, c2); }
	public List<Pos> shortestPath(Pos p) { return shortestPath(p.l, p.c); }
	public List<Pos> shortestPath(int l, int c) {
		long bt = backtrack[l * colNb + c];
		if (bt == 0) return null;
		List<Pos> track = new ArrayList<>();
		do {
			track.add(new Pos(l, c));
			bt = backtrack[l * colNb + c];
			l = (int) (bt & Integer.MAX_VALUE);
			c = (int) (bt >> 32);
		} while (bt != -1);
		Collections.reverse(track);
		return track;
	}
}
