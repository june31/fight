package tools.directions;

import java.util.function.Consumer;

import tools.tuple.Pos;

public enum Dir {
	L(p -> p.c--),
	R(p -> p.c++),
	U(p -> p.l--),
	D(p -> p.l++),
	UL(p -> { p.l--; p.c--; }),
	UR(p -> { p.l--; p.c++; }),
	DL(p -> { p.l++; p.c--; }),
	DR(p -> { p.l++; p.c++; });

	public final Consumer<Pos> move;
	
	private Dir(Consumer<Pos> c) {
		move = c;
	}
	
	public void apply(Pos p) {
		move.accept(p);
	}
	
	public int peek(int[][] map, Pos p) {
		Pos p2 = new Pos(p);
		move.accept(p2);
		if (p2.l < 0 || p2.l >= map.length || p2.c < 0 || p2.c >= map[0].length) return -1;
		return map[p2.l][p2.c];
	}
}
