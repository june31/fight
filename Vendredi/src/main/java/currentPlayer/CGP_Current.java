package currentPlayer;

import java.util.function.Function;
import java.util.function.Supplier;

import tools.scanner.Scan;
import tools.tables.Table;
import tools.tuple.Pos;

public class CGP_Current {

	//  0
	// 123
	//  4
	//  5

	static int n;
	static final int[][] FACES = { // U, D, L, R
			{ 5, 2, 1, 3 }, // 0's neigbbors
			{ 0, 4, 5, 2 }, // 1's neigbbors
			{ 0, 4, 1, 3 }, // 2's neigbbors
			{ 0, 4, 2, 5 }, // 3's neigbbors
			{ 2, 5, 1, 3 }, // 4's neigbbors
			{ 4, 0, 1, 3 }  // 5's neigbbors
	};
	static interface FPos extends Function<Pos, Pos> {}
	static final FPos[][] TRANSPOS = { // U, D, L, R
			{ p -> new Pos(n-1, p.c), p -> new Pos(0, p.c), p -> new Pos(0, p.l), p -> new Pos(0, n-1 - p.l) }, // 0's neigbbors
			{ p -> new Pos(p.c, 0), p -> new Pos(n-1 - p.c, 0), p -> new Pos(n-1 - p.l, 0), p -> new Pos(p.l, 0) }, // 1's neigbbors
			{ p -> new Pos(n-1, p.c), p -> new Pos(0, p.c), p -> new Pos(p.l, n-1), p -> new Pos(p.l, 0) }, // 2's neigbbors
			{ p -> new Pos(n-1 - p.c, n-1), p -> new Pos(p.c, n-1), p -> new Pos(p.l, n-1), p -> new Pos(n-1 - p.l, n-1) }, // 3's neigbbors
			{ p -> new Pos(n-1, p.c), p -> new Pos(0, p.c), p -> new Pos(n-1, n-1 - p.l), p -> new Pos(n-1, p.l) }, // 4's neigbbors
			{ p -> new Pos(n-1, p.c), p -> new Pos(0, p.c), p -> new Pos(n-1 - p.l, 0), p -> new Pos(n-1 - p.l, n-1) } // 5's neigbbors
	};
	static interface SPos extends Supplier<Pos> {}
	static final SPos[][] TRANSDIR = { // U, D, L, R
			{ () -> new Pos(-1, 0), () -> new Pos(1, 0), () -> new Pos(1, 0), () -> new Pos(1, 0) }, // 0's neigbbors
			{ () -> new Pos(0, 1), () -> new Pos(0, 1), () -> new Pos(0, 1), () -> new Pos(0, 1) }, // 1's neigbbors
			{ () -> new Pos(-1, 0), () -> new Pos(1, 0), () -> new Pos(0, -1), () -> new Pos(0, 1) }, // 2's neigbbors
			{ () -> new Pos(0, -1), () -> new Pos(0, -1), () -> new Pos(0, -1), () -> new Pos(0, -1) }, // 3's neigbbors
			{ () -> new Pos(-1, 0), () -> new Pos(1, 0), () -> new Pos(-1, 0), () -> new Pos(-1, 0) }, // 4's neigbbors
			{ () -> new Pos(-1, 0), () -> new Pos(1, 0), () -> new Pos(0, 1), () -> new Pos(0, -1) } // 5's neigbbors
	};

	static int face;
	static int startFace;
	static int[][][] map = new int[6][][];
	static int dl = 0;
	static int dc = 0;
	public static void main(String[] args) {
		Scan.setDebugMode(true);
		n = Scan.readInt();
		Pos start = null;
		for (int i = 0; i < 6; i++) {
			map[i] = Scan.readMap(n);
			Pos q = Table.find(map[i], new int[] { '<', '>', 'v', '^' });
			if (q != null) {
				startFace = face = i;
				start = q;
				int c = map[i][q.l][q.c];
				if (c == '<') dc = -1;
				if (c == '>') dc = +1;
				if (c == 'v') dl = +1;
				if (c == '^') dl = -1;
			}
		}
		
		boolean dir = Scan.readChar() == 'L';
		map[face][start.l][start.c] = '0';

		Pos o = null;
		int r = 0;
		while (o == null) {
			if (r++ == 5) {
				for (int i = 0; i < 6; i++) Table.printMap(map[i]);
				return;
			}
			o = nextPos(start);
			if (o == null) if (dir) right(); else left();
		}
		map[face][o.l][o.c] = '1';
		
		do {
			if (dir) left(); else right();
			Pos p = null;
			while (p == null) {
				p = nextPos(o);
				if (p == null) if (dir) right(); else left();
			}
			o = p;
			map[face][o.l][o.c]++;
		} while (!o.equals(start) || face != startFace);
		
		for (int i = 0; i < 6; i++) Table.printMap(map[i]);
	}
	
	static Pos nextPos(Pos p) {
		int nFace = face;
		Pos next = new Pos(p.l + dl, p.c + dc);
		Pos nextDir = new Pos(dl, dc);
		if (next.l == -1) {
			nFace = FACES[face][0];
			next = TRANSPOS[face][0].apply(next);
			nextDir = TRANSDIR[face][0].get();
		} else if (next.l == n) {
			nFace = FACES[face][1];
			next = TRANSPOS[face][1].apply(next);
			nextDir = TRANSDIR[face][1].get();
		} else if (next.c == -1) {
			nFace = FACES[face][2];
			next = TRANSPOS[face][2].apply(next);
			nextDir = TRANSDIR[face][2].get();
		} else if (next.c == n) {
			nFace = FACES[face][3];
			next = TRANSPOS[face][3].apply(next);
			nextDir = TRANSDIR[face][3].get();
		}
		if (map[nFace][next.l][next.c] == '#') return null;
		face = nFace;
		dl = nextDir.l;
		dc = nextDir.c;
		return next;
	}

	private static void left() {
		int tmp = dl; 
		dl = -dc;
		dc = tmp;
	}
	
	private static void right() {
		int tmp = dl; 
		dl = dc;
		dc = -tmp;
	}
}
