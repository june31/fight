package tools.bfs.util;

public class BFS2DHelper {

    public static Runnable[] dir8(BFS2DBase b) {
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
    
    public static Runnable[] cross(BFS2DBase b) {
        return new Runnable[] {
                () -> { b.c2++; b.l2++; },
                () -> { b.c2--; b.l2++; },
                () -> { b.c2--; b.l2--; },
                () -> { b.c2++; b.l2--; }
        };
    }
    
    public static Runnable[] knight(BFS2DBase b) {
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

    /**
     *  / \ / \ / \ / \
     * | A | B | C | D |   Line 0
     *  \ / \ / \ / \ / \
     *   | E | F | G | H | Line 1
     *  / \ / \ / \ / \ /
     * | I | J | K | L |   Line 2
     *  \ / \ / \ / \ / \
     *   | M | N | O | P | Line 3
     *    \ / \ / \ / \ /
     */
    public static Runnable[] hexa(BFS2DBase b) {
        return new Runnable[] {
                () -> { b.c2++; },
                () -> { b.l2++; },
                () -> { b.c2--; },
                () -> { b.l2--; },
                () -> { b.l2--; b.c2+=(b.l2 % 2 == 0 ? 1 : -1); },
                () -> { b.l2++; b.c2+=(b.l2 % 2 == 0 ? 1 : -1); }
        };
    }
}
