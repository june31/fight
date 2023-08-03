package tools.bfs.util;

import tools.bfs.BFS2DExt;

public class BFS2DHelper {

	public static Runnable[] dir8(BFS2DExt b) {
		return new Runnable[] {
				() -> { b.c2++; },
				() -> { b.c2++; b.l2++; },
				() -> { b.l2++; },
				() -> { b.c2--; b.l2++; },
				() -> { b.c2--; },
				() -> { b.c2--; b.l2--; },
				() -> { b.l2--; },
				() -> { b.c2++; b.l2--; }
		};
	}
	
	public static Runnable[] cross(BFS2DExt b) {
		return new Runnable[] {
				() -> { b.c2++; b.l2++; },
				() -> { b.c2--; b.l2++; },
				() -> { b.c2--; b.l2--; },
				() -> { b.c2++; b.l2--; }
		};
	}
	
	public static Runnable[] knight(BFS2DExt b) {
		return new Runnable[] {
				() -> { b.c2+=2; b.l2++; },
				() -> { b.c2-=2; b.l2++; },
				() -> { b.c2-=2; b.l2--; },
				() -> { b.c2+=2; b.l2--; },
				() -> { b.c2++; b.l2+=2; },
				() -> { b.c2--; b.l2+=2; },
				() -> { b.c2--; b.l2-=2; },
				() -> { b.c2++; b.l2-=2; }
		};
	}
	
	public static Runnable[] cyclic(BFS2DExt b) {
		return new Runnable[] {
				() -> { b.c2 = b.c2 == b.colNb - 1 ? 0 : b.c2 + 1; },
				() -> { b.l2 = b.l2 == b.lineNb - 1 ? 0 : b.l2 + 1; },
				() -> { b.c2 = b.c2 == 0 ? b.colNb - 1 : b.c2 - 1; },
				() -> { b.l2 = b.l2 == 0 ? b.lineNb - 1 : b.l2 - 1; }
		};
	}
}
