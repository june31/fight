package tools.board;

import java.util.List;

public abstract class Board {
	public static long count = 0;
	public long id;
	public Board parent;
	public int depth;
	public abstract List<Board> next();
	public abstract double eval();
	public Board() { this(null); }
	public Board(Board parent) {
		id = count++;
		this.parent = parent;
		depth = parent == null ? 0 : parent.depth + 1;
	}
}
