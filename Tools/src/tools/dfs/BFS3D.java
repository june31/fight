package tools.dfs;

import tools.function.HexaIntPredicate;
import tools.function.TriIntPredicate;

public class BFS3D {
	
	public final int[][][] tab;
	public boolean found;
	public int scanned; // includes start
	private final int xNb;
	private final int yNb;
	private final int zNb;
	private final int yzNb;
	private final int[] workXs;
	private final int[] workYs;
	private final int[] workZs;
	private final int mid;
	private boolean clean = true; 
	public final boolean[] used;
	private int newStart;
	private int newN = 0;

	// t is in int[x][y][z] format.
	public BFS3D(int[][][] t) {
		tab = t;
		xNb = tab.length;
		yNb = tab[0].length;
		zNb = tab[0][0].length;
		yzNb = yNb * zNb;
		mid = Integer.highestOneBit((2 * xNb * xNb + 2 * yNb * yNb + 2 * zNb * zNb) - 1) * 2; // Ceiling power of 2 
		workYs = new int[2 * mid];
		workXs = new int[2 * mid];
		workZs = new int[2 * mid];
		used = new boolean[xNb * yNb * zNb];
	}

	public int diffuse(int startX, int startY, int startZ) {
		return diffuse(startX, startY, startZ, (a, b, c, d, e, f) -> true, (a, b, c) -> false);
	}

	public int diffuse(int startX, int startY, int startZ, HexaIntPredicate condition) {
		return diffuse(startX, startY, startZ, condition, (a, b, c) -> false);
	}

	public int diffuse(int startX, int startY, int startZ, TriIntPredicate end) {
		return diffuse(startX, startY, startZ, (a, b, c, d, e, f) -> true, end);
	}

	public int diffuse(int startX, int startY, int startZ, HexaIntPredicate condition, TriIntPredicate end) {
		
		if (!clean) for (int i = 0; i < used.length; i++) used[i] = false; 
		clean = false;
		
		int turn = 1;
		int oldN = 1;
		workXs[0] = startX;
		workYs[0] = startY;
		workZs[0] = startZ;
		used[startX * yzNb + startY * zNb + startZ] = true;
		int oldStart = 0;
		newN = 0;
		newStart = mid;
		found = true;
		scanned = 1;
		
		Loop: while (true) {
			for (int i = 0; i < oldN; i++) {
				int x = workXs[oldStart | i], y = workYs[oldStart | i], z = workZs[oldStart | i];
				if (check(x, y, z, x-1, y, z, condition, end)) break Loop;
				if (check(x, y, z, x+1, y, z, condition, end)) break Loop;
				if (check(x, y, z, x, y-1, z, condition, end)) break Loop;
				if (check(x, y, z, x, y+1, z, condition, end)) break Loop;
				if (check(x, y, z, x, y, z-1, condition, end)) break Loop;
				if (check(x, y, z, x, y, z+1, condition, end)) break Loop;
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

	private boolean check(int sourceX, int sourceY, int sourceZ, int destX, int destY, int destZ,
			HexaIntPredicate condition, TriIntPredicate end) {
		
		if (destX < 0 || destX >= xNb  || destY < 0 || destY >= yNb || destZ < 0 || destZ >= zNb
				|| used[destX * yzNb + destY * zNb + destZ]
				|| !condition.test(sourceX, sourceY, sourceZ, destX, destY, destZ)) return false;
		scanned++;
		if (end.test(destX, destY, destZ)) return true;
		used[destX * yzNb + destY * zNb + destZ] = true;
		workXs[newStart | newN] = destX; 
		workYs[newStart | newN] = destY;
		workZs[newStart | newN] = destZ;
		newN++;
		return false;
	}
}
