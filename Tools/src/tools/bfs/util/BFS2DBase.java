package tools.bfs.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

import tools.collections.pos.Lp;
import tools.tables.Table;
import tools.tuple.Pos;

public abstract class BFS2DBase {
	
	protected final long USED_BIT = 1l<<31; // to differentiate unused backtrack and backtrack to (0, 0)

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
	public boolean hCycle;
	public boolean vCycle;
	
	public BooleanSupplier moveCondition;
	public BooleanSupplier endCondition;
	public Runnable sideEffect = () -> {};
	protected boolean firstEffect = false;

	public Supplier<Pos> teleport;
	
	public final int lineNb;
	public final int colNb;
	
	protected boolean clean = true;
	
	// See BFS2DHelper class for additional move strategies
	public Runnable[] moves = {
			() -> { c2++; },
			() -> { c2--; },
			() -> { l2++; },
			() -> { l2--; }
	};

	// bits 0 to 30 store L.
	// bit 31 is reserved for use purposes.
	// bits 32 to 62 store C.
	protected final long[] backtrack;
	
	public BFS2DBase(int[][] table) {
		tab = table;
		lineNb = tab.length;
		colNb = tab[0].length;
		backtrack = new long[lineNb * colNb];
	}
	
	// To be set after standard moves
	public void setTeleportMap(Map<Pos, Pos> teleMap) {
		List<Runnable> l = new ArrayList<>();
		for (Runnable r : moves) {
			Runnable r2 = () -> {
				r.run();
				Pos p = teleMap.get(new Pos(l2, c2));
				if (p != null) {
					l2 = p.l;
					c2 = p.c;
				}
			};
			l.add(r2);
		}
		moves = l.toArray(new Runnable[0]);
	}
	
	public void setMoves(Runnable... moves) { this.moves = moves; }
	public void setCyclic(boolean horizontal, boolean vertical) {
		hCycle = horizontal;
		vCycle = vertical;
	}
	public void setTeleport(Supplier<Pos> tp) { teleport = tp; }
	public void setTeleport(Pos[][] tpMap) { teleport = () -> tpMap[l2][c2]; }
	
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
	
	public abstract int diffuse(int startLine, int startCol, BooleanSupplier move, BooleanSupplier end, boolean testStart);
	
	// This includes the start and the end. The order is start -> end.
	public Lp shortestPath() { return shortestPath(l2, c2); }
	public Lp shortestPath(Pos p) { return shortestPath(p.l, p.c); }
	public Lp shortestPath(int l, int c) {
		long bt = backtrack[l * colNb + c];
		if (bt == 0) return null;
		Lp track = new Lp();
		do {
			track.add(new Pos(l, c));
			bt = backtrack[l * colNb + c];
			l = (int) (bt & Integer.MAX_VALUE);
			c = (int) (bt >> 32);
		} while (bt != -1);
		Collections.reverse(track);
		return track;
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
}
