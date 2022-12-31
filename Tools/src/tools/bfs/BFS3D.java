package tools.bfs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BooleanSupplier;

import tools.tuple.Pos3;

public class BFS3D {
	
	public final int RESERVED = 1<<28;
	
	public final int[][][] tab;
	public boolean found;
	public int scanned; // includes start
	public int x1;
	public int y1;
	public int z1;
	public int x2;
	public int y2;
	public int z2;
	public int v1;
	public int v2;
	public int startX;
	public int startY;
	public int startZ;
	
	public BooleanSupplier moveCondition;
	public BooleanSupplier endCondition;
	
	public final int xNb;
	public final int yNb;
	public final int zNb;
	
	private final int[] t;
	private final int yzNb;
	private final int[] workXs;
	private final int[] workYs;
	private final int[] workZs;
	private boolean clean = true; 
	private int newStart;
	private final int mid;
	private int newN = 0;

	// t is in int[x][y][z] format.
	// bits 28 to 30 are reserved for use & backtracking purposes.
	public BFS3D(int[][][] table) {
		tab = table;
		xNb = tab.length;
		yNb = tab[0].length;
		zNb = tab[0][0].length;
		yzNb = yNb * zNb;
		t = new int[xNb * yNb * zNb];
		for (int i = 0; i < xNb; i++) for (int j = 0; j < yNb; j++) for (int k = 0; k < zNb; k++) t[i * yzNb + j * zNb + k] = tab[i][j][k];
		mid = Integer.highestOneBit((2 * xNb * xNb + 2 * yNb * yNb + 2 * zNb * zNb) - 1) * 2; // Ceiling power of 2 
		workYs = new int[2 * mid];
		workXs = new int[2 * mid];
		workZs = new int[2 * mid];
	}

	public int diffuse(Pos3 s, int wall) { return diffuse(s.x, s.y, s.z, () -> v2 != wall, () -> false); }
	public int diffuse(Pos3 s, int wall, Pos3 e) { return diffuse(s.x, s.y, s.z, () -> v2 != wall, () -> e.x == x2 && e.y == y2 && e.z == z2); }
	public int diffuse(Pos3 s, int wall, BooleanSupplier end) { return diffuse(s.x, s.y, s.z, () -> v2 != wall, end); }
	public int diffuse(Pos3 s, BooleanSupplier move, Pos3 e) { return diffuse(s.x, s.y, s.z, move, () -> e.x == x2 && e.y == y2 && e.z == z2); }
	public int diffuse(Pos3 s, BooleanSupplier move, BooleanSupplier end) { return diffuse(s.x, s.y, s.z, move, end); }
	public int diffuse(int x1, int y1, int z1, int wall, int x2, int y2, int z2) { return diffuse(x1, y1, z1, () -> v2 != wall, () -> x1 == this.x1 && y1 == this.y1 && z1 == this.z1); }
	public int diffuse(int x1, int y1, int z1, BooleanSupplier move, int x2, int y2, int z2) { return diffuse(x1, y1, z1, move, () -> x1 == this.x1 && y1 == this.y1 && z1 == this.z1); }
	public int diffuse(int x1, int y1, int z1, int wall, BooleanSupplier end) { return diffuse(x1, y1, z1, () -> v2 != wall, end); }

	public int diffuse(int startX, int startY, int startZ, BooleanSupplier move, BooleanSupplier end) {
		moveCondition = move;
		endCondition = end;
		if (!clean) for (int i = 0; i < t.length; i++) t[i] &= ~(7<<28);
		clean = false;

		this.startX = startX;
		this.startY = startY;
		this.startZ = startZ;
		int turn = 1;
		int oldN = 1;
		workXs[0] = startX;
		workYs[0] = startY;
		workZs[0] = startZ;
		int oldStart = 0;
		newN = 0;
		newStart = mid;
		found = true;
		scanned = 1;
		x2 = startX;
		y2 = startY;
		z2 = startZ;
		v2 = t[startX * yzNb + startY * zNb + startZ];
		if (endCondition.getAsBoolean()) return 0;
		t[startX * yzNb + startY * zNb + startZ] = v2 | RESERVED;
		
		Loop: while (true) {
			for (int i = 0; i < oldN; i++) {
				x1 = workXs[oldStart | i];
				y1 = workYs[oldStart | i];
				z1 = workZs[oldStart | i];
				v1 = t[x1 * yzNb + y1 * zNb + z1];
				x2 = x1 + 1;
				y2 = y1;
				z2 = z1;
				if (check(RESERVED * 2)) break Loop;
				x2 = x1 - 1;
				if (check(RESERVED * 3)) break Loop;
				x2 = x1;
				y2 = y1 + 1;
				if (check(RESERVED * 4)) break Loop;
				y2 = y1 - 1;
				if (check(RESERVED * 5)) break Loop;
				y2 = y1;
				z2 = z1 + 1;
				if (check(RESERVED * 6)) break Loop;
				z2 = z1 - 1;
				if (check(RESERVED * 7)) break Loop;
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
		if (x2 < 0 || x2 >= xNb || y2 < 0 || y2 >= yNb || z2 < 0 || z2 >= xNb) return false;
		v2 = t[x2 * yzNb + y2 * zNb + z2];
		if ((v2 & (RESERVED * 7)) != 0 || !moveCondition.getAsBoolean()) return false;
		scanned++;
		t[x2 * yzNb + y2 * zNb + z2] = v2 | info;
		if (endCondition.getAsBoolean()) return true;
		workXs[newStart | newN] = x2; 
		workYs[newStart | newN] = y2;
		workZs[newStart | newN] = z2;
		newN++;
		return false;
	}
	
	public List<Pos3> backTrack(Pos3 p) { return backTrack(p.x, p.y, p.z); }
	public List<Pos3> backTrack(int x, int y, int z) {
		if (!found) return null;
		List<Pos3> track = new ArrayList<>();
		while (x != startX || y != startY || z != startZ) {
			track.add(new Pos3(x, y, z));
			int d = t[x * yzNb + y * zNb + z] & (RESERVED * 7);
			if (d == RESERVED * 2) x--;
			else if (d == RESERVED * 3) x++;
			else if (d == RESERVED * 4) y--;
			else if (d == RESERVED * 5) y++;
			else if (d == RESERVED * 6) z--;
			else if (d == RESERVED * 7) z++;
			else throw new IllegalArgumentException("Invalid backtrack");
		}
		Collections.reverse(track);
		return track;
	}

	public Pos3 next(Pos3 p) { return next(p.x, p.y, p.z); }
	public Pos3 next(int x, int y, int z) {
		if (!found) return null;
		int dx = -1;
		int dy = -1;
		int dz = -1;
		while (x != startX || y != startY || z != startZ) {
			dx = x;
			dy = y;
			dz = z;
			int d = t[x * yzNb + y * zNb + z] & (RESERVED * 7);
			if (d == RESERVED * 2) x--;
			else if (d == RESERVED * 3) x++;
			else if (d == RESERVED * 4) y--;
			else if (d == RESERVED * 5) y++;
			else if (d == RESERVED * 6) z--;
			else if (d == RESERVED * 7) z++;
			else throw new IllegalArgumentException("Invalid backtrack");
		}
		return dx == -1 ? null : new Pos3(dx, dy, dz);
	}
}
