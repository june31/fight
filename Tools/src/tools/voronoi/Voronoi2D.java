package tools.voronoi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.BooleanSupplier;

import tools.tuple.Pos;
import tools.voronoi.model.PlayerTrack;

public final class Voronoi2D {

	public final int USED_BIT = 1<<28;
	public int NEW_BIT = 1<<27;
	public int ALREADY_BIT = 1<<26;
	public final int REMOVE_BIT = 1<<25;
	public final int VAL_BITS = 0x00FFFFFF;

	public final int[][] tab;
	public final int[][] ownerTable; // -1 if no owner
	public int[] areas; // includes start positions
	public int l1;
	public int c1;
	public int l2;
	public int c2;
	public int v1;
	public int v2;
	public int index;
	public Pos[] startPositions;
	public int turn;
	public boolean priority;
	
	public BooleanSupplier moveCondition;

	public final int lineNb;
	public final int colNb;

	private final int[] t;
	private boolean clean = true; 
	
	// t is in int[line][col] format.
	// Only first 24 bits are usable for values
	public Voronoi2D(int[][] table) {
		tab = table;
		lineNb = tab.length;
		colNb = tab[0].length;
		t = new int[lineNb * colNb];
		ownerTable = new int[lineNb][colNb];
		for (int i = 0; i < lineNb; i++) for (int j = 0; j < colNb; j++) t[i * colNb + j] = tab[i][j];
	}

	public int diffuse(Pos[] ps, boolean priority) { return diffuse(ps, () -> false, priority); }
	public int diffuse(Pos[] ps, int wall, boolean priority) { return diffuse(ps, () -> v2 != wall, priority); }
	public int diffuse(Pos[] ps, BooleanSupplier move, boolean priority) {
		if (!clean) for (int i = 0; i < t.length; i++) t[i] &= 0x00FFFFFF;
		clean = false;
		for (int i = 0; i < lineNb; i++) for (int j = 0; j < colNb; j++) ownerTable[i][j] = -1;
		
		// Save all start positions for shortest path calculus
		startPositions = ps;
		
		this.priority = priority;
		if (priority) NEW_BIT = USED_BIT;
		int l = ps.length;
		moveCondition = move;
		List<List<Integer>> workLines = new ArrayList<>();
		List<List<Integer>> workCols = new ArrayList<>();
		List<List<Integer>> newLines = new ArrayList<>();
		List<List<Integer>> newCols = new ArrayList<>();
		areas = new int[l];
		for (int i = 0; i < l ; i++) {
			List<Integer> wl = new ArrayList<>();
			wl.add(ps[i].l);
			workLines.add(wl);
			List<Integer> wc = new ArrayList<>();
			wc.add(ps[i].c);
			workCols.add(wc);
			List<Integer> nl = new ArrayList<>();
			newLines.add(nl);
			List<Integer> nc = new ArrayList<>();
			newCols.add(nc);
			t[ps[i].l * colNb + ps[i].c] |= USED_BIT;
			areas[i] = 1;
		}
		turn = 0;

		Loop: while (true) {
			turn++;
			for (index = 0; index < l; index++) {
				List<Integer> wl = workLines.get(index);
				List<Integer> wc = workCols.get(index);
				List<Integer> nl = newLines.get(index);
				List<Integer> nc = newCols.get(index);
				nl.clear();
				nc.clear();
				for (int j = 0; j < wl.size(); j++) {
					l1 = wl.get(j);
					c1 = wc.get(j);
					v1 = t[l1 * colNb + c1] & VAL_BITS;
					l2 = l1;
					c2 = c1 + 1;
					if (check(nl, nc, NEW_BIT | 0<<29)) break Loop;
					c2 = c1 - 1;
					if (check(nl, nc, NEW_BIT | 1<<29)) break Loop;
					c2 = c1;
					l2 = l1 + 1;
					if (check(nl, nc, NEW_BIT | 2<<29)) break Loop;
					l2 = l1 - 1;
					if (check(nl, nc, NEW_BIT | 3<<29)) break Loop;
				}
				
				// NEW -> ALREADY
				if (!priority) {
					for (int j = 0; j < nl.size(); j++)
						t[nl.get(j) * colNb + nc.get(j)] |= ALREADY_BIT;
				}
			}
			for (int i = 0; i < l; i++) {
				List<Integer> wl = workLines.get(i);
				List<Integer> wc = workCols.get(i);
				List<Integer> nl = newLines.get(i);
				List<Integer> nc = newCols.get(i);
				if (!priority) {
					Iterator<Integer> itL = nl.iterator();
					Iterator<Integer> itC = nc.iterator();
					while (itL.hasNext()) {
						int line = itL.next();
						int col = itC.next();
						int pos = line * colNb + col;
						if ((t[pos] & REMOVE_BIT) != 0) {
							itL.remove();
							itC.remove();
						} else ownerTable[line][col] = i;
						t[pos] |= USED_BIT;
					}
				}
				areas[i] += nl.size();
				
				// Swap work / new
				workLines.set(i, nl);
				workCols.set(i, nc);
				newLines.set(i, wl);
				newCols.set(i, wc);
			}
			for (List<Integer> work : workLines) if (!work.isEmpty()) continue Loop;
			break;
		}

		return turn;
	}

	private boolean check(List<Integer> nl, List<Integer> nc, int info) {
		if (l2 < 0 || l2 >= lineNb || c2 < 0 || c2 >= colNb) return false;
		v2 = t[l2 * colNb + c2];
		if ((v2 & USED_BIT) != 0 || !moveCondition.getAsBoolean()) {
			if (!priority && (v2 & ALREADY_BIT) != 0) t[l2 * colNb + c2] |= REMOVE_BIT;
			return false;
		}
		t[l2 * colNb + c2] = v2 | info;
		nl.add(l2);
		nc.add(c2);
		return false;
	}

	// This includes the start and the end. The order is start -> end.
	public PlayerTrack shortestPath(Pos p) { return shortestPath(p.l, p.c); }
	public PlayerTrack shortestPath(int l, int c) {
		if ((t[l * colNb + c] & USED_BIT) == 0) return null;
		if ((t[l * colNb + c] & REMOVE_BIT) != 0) return null;
		List<Pos> track = new ArrayList<>();
		int player;
		while ((player = getPlayer(l, c)) == -1) {
			track.add(new Pos(l, c));
			int d = t[l * colNb + c] & (3<<29);
			if (d == 0) c--;
			else if (d == 1<<29) c++;
			else if (d == 1<<30) l--;
			else l++;
		}
		track.add(new Pos(l, c));
		Collections.reverse(track);
		return new PlayerTrack(player, track);
	}

	private int getPlayer(int l, int c) {
		for (int i = 0; i < startPositions.length; i++) {
			if (l == startPositions[i].l && c == startPositions[i].c) return i;
		}
		return -1;
	}
}
