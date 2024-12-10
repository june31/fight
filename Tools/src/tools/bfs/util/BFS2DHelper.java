package tools.bfs.util;

import java.util.List;
import java.util.function.Function;

import tools.bfs.BFS;
import tools.collections.object.Lo;

public class BFS2DHelper {

    public static Function<BFS2DBase<BFS>, List<Runnable>> dir8() {
        return b -> List.of(
                () -> { b.c2++; },
                () -> { b.l2++; },
                () -> { b.c2--; },
                () -> { b.l2--; },
                () -> { b.c2++; b.l2++; },
                () -> { b.c2--; b.l2++; },
                () -> { b.c2--; b.l2--; },
                () -> { b.c2++; b.l2--; });
    }
    
    public static Function<BFS2DBase<BFS>, List<Runnable>> cross() {
        return b -> List.of(
                () -> { b.c2++; b.l2++; },
                () -> { b.c2--; b.l2++; },
                () -> { b.c2--; b.l2--; },
                () -> { b.c2++; b.l2--; });
    }
    
    public static Function<BFS2DBase<BFS>, List<Runnable>> knight() {
        return b -> List.of(
                () -> { b.c2+=2; b.l2++; },
                () -> { b.c2-=2; b.l2++; },
                () -> { b.c2-=2; b.l2--; },
                () -> { b.c2+=2; b.l2--; },
                () -> { b.c2++; b.l2+=2; },
                () -> { b.c2--; b.l2+=2; },
                () -> { b.c2--; b.l2-=2; },
                () -> { b.c2++; b.l2-=2; });
    }

    //  / \ / \ / \ / \
    // | A | B | C | D |   Line 0
    //  \ / \ / \ / \ / \
    //   | E | F | G | H | Line 1
    //  / \ / \ / \ / \ /
    // | I | J | K | L |   Line 2
    //  \ / \ / \ / \ / \
    //   | M | N | O | P | Line 3
    //    \ / \ / \ / \ /
    //
    public static Function<BFS2DBase<BFS>, List<Runnable>> hexa() {
        return b -> List.of(
                () -> { b.c2++; },
                () -> { b.l2++; },
                () -> { b.c2--; },
                () -> { b.l2--; },
                () -> { b.l2--; b.c2+=(b.l2 % 2 == 0 ? 1 : -1); },
                () -> { b.l2++; b.c2+=(b.l2 % 2 == 0 ? 1 : -1); });
    }
    
    public static Function<BFS2DBase<BFS>, List<Runnable>> anyToWall(int wall) {
    	return b -> {
			var r = new Lo<Runnable>();
			int c = b.c2;
			while (++c < b.map[0].length && b.map[b.l2][c] != wall) {
				int fc = c;
				r.add(() -> { b.c2 = fc; });
			}
			c = b.c2;
			while (--c >= 0 && b.map[b.l2][c] != wall) {
				int fc = c;
				r.add(() -> { b.c2 = fc; });
			}
			int l = b.l2;
			while (++l < b.map.length && b.map[l][b.c2] != wall) {
				int fl = l;
				r.add(() -> { b.l2 = fl; });
			}
			l = b.l2;
			while (--l >= 0 && b.map[l][b.c2] != wall) {
				int fl = l;
				r.add(() -> { b.l2 = fl; });
			}
			return r;
		};
    }
    
    public static Function<BFS2DBase<BFS>, List<Runnable>> slideToWall(int wall) {
    	return b -> {
			var r = new Lo<Runnable>();
			int c = b.c2;
			while (++c < b.map[0].length && b.map[b.l2][c] != wall);
			if (c != b.c2 + 1) {
				int fc = c - 1;
				r.add(() -> { b.c2 = fc; });
			}
			c = b.c2;
			while (--c >= 0 && b.map[b.l2][c] != wall);
			if (c != b.c2 - 1) {
				int fc = c + 1;
				r.add(() -> { b.c2 = fc; });
			}
			int l = b.l2;
			while (++l < b.map.length && b.map[l][b.c2] != wall);
			if (l != b.l2 + 1) {
				int fl = l - 1;
				r.add(() -> { b.l2 = fl; });
			}
			l = b.l2;
			while (--l >= 0 && b.map[l][b.c2] != wall);
			if (l != b.l2 - 1) {
				int fl = l + 1;
				r.add(() -> { b.l2 = fl; });
			}
			return r;
		};
    }
}
