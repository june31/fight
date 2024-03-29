package tools.voronoi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;

import tools.tuple.Pos;
import tools.voronoi.model.PlayerTrack;
import tools.voronoi.model.VorMode;

public final class Voronoi2D {

	public final int USED_BIT = 1<<25;
	public int CURRENT_PLAYER_BIT = 1<<26;
	public int TENTATIVE_BIT = 1<<27;
	public final int REMOVE_BIT = 1<<28;
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
	public VorMode mode;
	
	public BooleanSupplier moveCondition;
	public BooleanSupplier endCondition;

	public final int lineNb;
	public final int colNb;

	private final int[] t;
	private boolean clean = true;
	public Runnable standardSideEffect = () -> {};
	private boolean firstEffect = false;
	public Runnable contactSideEffect = () -> {};
	
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

	public int diffuse(Pos[] ps, VorMode mode) { return diffuse(ps, () -> true, () -> false, mode); }
	public int diffuse(Pos[] ps, int wall, VorMode mode) { return diffuse(ps, () -> v2 != wall, () -> false, mode); }
	public int diffuse(Pos[] ps, BooleanSupplier move, VorMode mode) { return diffuse(ps, move, () -> false, mode); }
	public int diffuse(Pos[] ps, BooleanSupplier move, BooleanSupplier end, VorMode mode) {
		endCondition = end;
		if (!clean) for (int i = 0; i < t.length; i++) t[i] &= VAL_BITS;
		clean = false;
		for (int i = 0; i < lineNb; i++) for (int j = 0; j < colNb; j++) ownerTable[i][j] = -1;
		
		// Save all start positions for shortest path calculus
		startPositions = ps;
		
		this.mode = mode;
		int newBit = mode == VorMode.SEQUENTIAL ? USED_BIT : CURRENT_PLAYER_BIT;
		int n = ps.length;
		moveCondition = move;
		List<List<Integer>> workLines = new ArrayList<>();
		List<List<Integer>> workCols = new ArrayList<>();
		List<List<Integer>> newLines = new ArrayList<>();
		List<List<Integer>> newCols = new ArrayList<>();
		areas = new int[n];
		for (int i = 0; i < n ; i++) {
			List<Integer> wl = new ArrayList<>();
			wl.add(ps[i].l);
			workLines.add(wl);
			List<Integer> wc = new ArrayList<>();
			wc.add(ps[i].c);
			workCols.add(wc);
			newLines.add(new ArrayList<Integer>());
			newCols.add(new ArrayList<Integer>());
			l2 = ps[i].l;
			c2 = ps[i].c;
			v2 = t[l2 * colNb + c2];
			if (firstEffect) standardSideEffect.run();
			if (endCondition.getAsBoolean()) return 0;
			t[l2 * colNb + c2] |= USED_BIT;
			areas[i] = 1;
		}
		turn = 0;

		Loop: while (true) {
			turn++;
			for (index = 0; index < n; index++) {
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
					if (check(nl, nc, newBit | 0<<29)) break Loop;
					c2 = c1 - 1;
					if (check(nl, nc, newBit | 1<<29)) break Loop;
					c2 = c1;
					l2 = l1 + 1;
					if (check(nl, nc, newBit | 2<<29)) break Loop;
					l2 = l1 - 1;
					if (check(nl, nc, newBit | 3<<29)) break Loop;
				}
				
				// CURRENT_PLAYER_BIT -> TENTATIVE_BIT
				if (mode != VorMode.SEQUENTIAL) {
					for (int j = 0; j < nl.size(); j++) {
						t[nl.get(j) * colNb + nc.get(j)] &= ~CURRENT_PLAYER_BIT;
						t[nl.get(j) * colNb + nc.get(j)] |= TENTATIVE_BIT;
					}
				}
			}
			for (index = 0; index < n; index++) {
				List<Integer> wl = workLines.get(index);
				List<Integer> wc = workCols.get(index);
				List<Integer> nl = newLines.get(index);
				List<Integer> nc = newCols.get(index);
				if (mode != VorMode.SEQUENTIAL) {
					Iterator<Integer> itL = nl.iterator();
					Iterator<Integer> itC = nc.iterator();
					while (itL.hasNext()) {
						int line = itL.next();
						int col = itC.next();
						int pos = line * colNb + col;
						if ((t[pos] & REMOVE_BIT) != 0) {
							if (mode == VorMode.SYNC_BLOCK) {
								itL.remove();
								itC.remove();
							}
							ownerTable[line][col] = -2;
						} else {
							ownerTable[line][col] = index;
						}
						t[pos] |= USED_BIT;
					}
				}
				areas[index] += nl.size();
				
				for (int i = 0; i < nl.size(); i++) {
					l2 = nl.get(i);
					c2 = nc.get(i);
					v2 = t[l2 * colNb + c2] & VAL_BITS;
					if (ownerTable[l2][c2] >= 0) standardSideEffect.run();
				}
				
				// Swap work / new
				workLines.set(index, nl);
				workCols.set(index, nc);
				newLines.set(index, wl);
				newCols.set(index, wc);
			}
			for (List<Integer> work : workLines) if (!work.isEmpty()) continue Loop;
			break;
		}

		return turn;
	}

	private boolean check(List<Integer> nl, List<Integer> nc, int info) {
		if (l2 < 0 || l2 >= lineNb || c2 < 0 || c2 >= colNb) return false;
		v2 = t[l2 * colNb + c2];
		if ((v2 & (USED_BIT | CURRENT_PLAYER_BIT)) != 0 || !moveCondition.getAsBoolean()) return false;
		if ((v2 & TENTATIVE_BIT) != 0) {
			t[l2 * colNb + c2] |= REMOVE_BIT;
			contactSideEffect.run();
			if (mode == VorMode.SYNC_FLUID) {
				// Even if there is a collision, add element
				nl.add(l2);
				nc.add(c2);
			}
			return false;
		}
		if (endCondition.getAsBoolean()) return true;
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

	public void setStandardSideEffect(boolean onStart, Runnable effect) {
		firstEffect = onStart;
		standardSideEffect = effect;
	}
	
	public void setStandardSideEffect(boolean onStart, IntSupplier is) {
		firstEffect = onStart;
		standardSideEffect = () -> tab[l2][c2] = is.getAsInt();
	}

	public void setStandardSideEffect(boolean onStart, int i) {
		firstEffect = onStart;
		standardSideEffect = () -> tab[l2][c2] = i;
	}
	
	public void setContactSideEffect(Runnable effect) {
		contactSideEffect = effect;
	}

	public void setContactSideEffect(IntSupplier is) {
		contactSideEffect = () -> tab[l2][c2] = is.getAsInt();
	}
	
	public void setContactSideEffect(int i) {
		contactSideEffect = () -> tab[l2][c2] = i;
	}
}
