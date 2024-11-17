package tools.bfs.util;

import java.util.Collections;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;

import tools.collections.pos.Lp;
import tools.function.BiIntConsumer;
import tools.function.BiIntToIntFunction;
import tools.tables.Table;
import tools.tuple.Pos;

@SuppressWarnings("unchecked")
public abstract class BFS2DBase<T> {
	
	protected final long USED_BIT = 1l<<31; // to differentiate unused backtrack and backtrack to (0, 0)

	public final int[][] map;
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
	
	public BooleanSupplier moveCondition = () -> true;
	public BooleanSupplier endCondition = () -> false;
	public Runnable sideEffect = () -> {};
	protected boolean firstEffect = false;
	protected boolean testStart = true;

	public final int lineNb;
	public final int colNb;
	
	protected boolean clean = true;
	
	// See BFS2DHelper class for additional move strategies
	public Function<BFS2DBase<T>, List<Runnable>> moves = bfs -> List.of(
			() -> { c2++; },
			() -> { c2--; },
			() -> { l2++; },
			() -> { l2--; });

	// bits 0 to 30 store L.
	// bit 31 is reserved for use purposes.
	// bits 32 to 62 store C.
	protected final long[] backtrack;
	
	public BFS2DBase(int[][] table) {
		map = table;
		lineNb = map.length;
		colNb = map[0].length;
		backtrack = new long[lineNb * colNb];
	}
	
	public T disableFirstEffect() {
		firstEffect = false;
		return (T) this;
	}		
	
	public T sideEffect(Runnable r) {
		sideEffect = r;
		return (T) this;
	}
	
	public T sideEffect(IntConsumer ic) {
		sideEffect = () -> ic.accept(v2);
		return (T) this;
	}
	
	public T sideEffect(BiIntConsumer i2c) {
		sideEffect = () -> i2c.accept(l2, c2);
		return (T) this;
	}

	public T setValue(int i) {
		sideEffect = () -> map[l2][c2] = i;
		return (T) this;
	}

	public T setValue(IntUnaryOperator iuo) {
        sideEffect = () -> map[l2][c2] = iuo.applyAsInt(v2);
   		return (T) this;
	}

	public T setValue(IntSupplier is) {
		sideEffect = () -> map[l2][c2] = is.getAsInt();
		return (T) this;
	}

	public T setValue(BiIntToIntFunction i2if) {
		sideEffect = () -> map[l2][c2] = i2if.apply(l2, c2);
		return (T) this;
	}
	
	public T setMoves(Function<BFS2DBase<T>, List<Runnable>> moves) {
		this.moves = moves;
		return (T) this;
	}
	
	public T setCyclic(boolean horizontal, boolean vertical) {
		hCycle = horizontal;
		vCycle = vertical;
		return (T) this;
	}
	
	public T wall(int c) {
		moveCondition = () -> v2 != c;
		return (T) this;
	}
	
	public T move(BooleanSupplier bs) {
		moveCondition = bs;
		return (T) this;
	}
	
	public T end(int c) {
		endCondition = () -> v2 == c;
		return (T) this;
	}

	public T end(Pos p) {
		endCondition = () -> l2 == p.l && c2 == p.c;
		return (T) this;
	}
	
	public T end(int l, int c) {
		endCondition = () -> l2 == l && c2 == c;
		return (T) this;
	}

	public T end(BooleanSupplier bs) {
		endCondition = bs;
		return (T) this;
	}

	public T testStart(boolean t) {
		testStart = t;
		return (T) this;
	}

	public T diffuse(int c) { return diffuse(Table.find(map, c)); }
	public T diffuse(Pos p) { return diffuse(p.l, p.c); }
	public abstract T diffuse(int startLine, int startCol);
	
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
}
