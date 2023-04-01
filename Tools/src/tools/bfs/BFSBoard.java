package tools.bfs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

import tools.board.Board;

public final class BFSBoard {

	public boolean found;
	public int scanned; // includes start
	public Board b1;
	public Board b2;
	
	public BFSBoard(Board board) { b2 = board; }

	public int diffuse() { return diffuse(Long.MAX_VALUE, null, () -> b2.eval() == Double.POSITIVE_INFINITY); }
	public int diffuse(long maxDepth, Consumer<List<Board>> filter, BooleanSupplier end) {
		List<Board> current = new ArrayList<>();
		List<Board> next = new ArrayList<>();
		current.add(b2);
		found = true;
		while (true) {
			for (Board b : current) {
				b1 = b.parent;
				b2 = b;
				scanned++;
				if (end.getAsBoolean()) return b2.depth;
				next.addAll(b2.next());
			}
			if (next.isEmpty()) {
				found = false;
				break;
			}
			if (b2.depth == maxDepth) break;
			if (filter != null) filter.accept(next);
			List<Board> tmp = current;
			current = next;
			next = tmp;
			next.clear();
		}

		return b2.depth;
	}

	// This includes the start and the end. The order is start -> end.
	public List<Board> shortestPath() {
		if (!found) return null;
		List<Board> track = new ArrayList<>();
		Board b = b2;
		while (b != null) {
			track.add(b);
			b = b.parent;
		}
		Collections.reverse(track);
		return track;
	}
}
