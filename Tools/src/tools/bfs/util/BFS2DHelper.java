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
}
