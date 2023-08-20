package tools.bfs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BooleanSupplier;

import tools.math.Num;
import tools.tables.Table;
import tools.tuple.Pos;

public final class BFS2DWExt {

	private final long USED_BIT = 1l<<31; // to differentiate unused backtrack and backtrack to (0, 0)

	public final int[][] tab;
	public final int[][] ws;
	public boolean found;
	public int scanned; // includes start
	public int l1;
	public int c1;
	public int l2;
	public int c2;
	public int v1;
	public int v2;
	public int w1;
	public int w2;
	public int turn;
	public int maxW;

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

	public BFS2DWExt(int[][] table, int[][] weights) {
		tab = table;
		ws = weights;
		lineNb = tab.length;
		colNb = tab[0].length;
		backtrack = new long[lineNb * colNb];
		maxW = Num.max(weights).value;
	}

	public void setMoves(Runnable... moves) { this.moves = moves; }

	// To be overridden if move list depend on square.
	public Runnable[] getMoves() { return moves; }

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
		List<List<Integer>> allLs = new ArrayList<List<Integer>>(maxW + 1);
		List<List<Integer>> allCs = new ArrayList<List<Integer>>(maxW + 1);
		for (int i = 0; i <= maxW; i++) {
			allLs.add(new ArrayList<>());
			allCs.add(new ArrayList<>());
		}
		int index = 0;

		allLs.get(0).add(startLine);
		allCs.get(0).add(startCol);
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
		w2 = ws[l2][c2];
		if (endCondition.getAsBoolean()) return 0;
		if (testStart && !move.getAsBoolean()) return 0;
		scanned = 1;
		backtrack[startLine * colNb + startCol] = -1;
		turn = 1;
		int drought = 0;

		while (true) {
			List<Integer> currentL = allLs.get(index);
			if (currentL.size() > 0) {
				List<Integer> currentC = allCs.get(index);
				for (int i = 0; i < currentL.size(); i++) {
					drought = index;
					l1 = currentL.get(i);
					c1 = currentC.get(i);
					v1 = tab[l1][c1];
					w1 = ws[l1][c1];
					Runnable[] posMoves = getMoves();
					for (int r = 0; r < posMoves.length; r++) {
						l2 = l1;
						c2 = c1;
						w2 = w1;
						posMoves[r].run();
						if (l2 < 0 || l2 >= lineNb || c2 < 0 || c2 >= colNb) continue;
						long back = backtrack[l2 * colNb + c2];
						if (back != 0) continue;
						v2 = tab[l2][c2];
						w2 = ws[l2][c2];
						if (!moveCondition.getAsBoolean()) continue;
						backtrack[l2 * colNb + c2] = USED_BIT | l1 | (((long) c1) << 32); 
						scanned++;
						if (endCondition.getAsBoolean()) return turn;
						allLs.get((index + w2) % (maxW + 1)).add(l2);
						allCs.get((index + w2) % (maxW + 1)).add(c2);
					}
				}
				currentL.clear();
				currentC.clear();
			}
			turn++;
			index = (index + 1) % (maxW + 1);
			if (index == drought) {
				return turn - maxW - 1;
			}
		}
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
