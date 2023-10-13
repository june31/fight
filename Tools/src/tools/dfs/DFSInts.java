package tools.dfs;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import tools.board.DepthBoard;

public class DFSInts<B extends DepthBoard<B>> {
	private Supplier<B> boardSupplier;
	public List<B> boards = new ArrayList<>();
	public int best = 0;
	public B bestBoard = null;

	public DFSInts(Supplier<B> boardSupplier) {
		this.boardSupplier = boardSupplier;
		boards.add(boardSupplier.get());
	}

	public B process() {
		return process(1);
	}

	private B process(int depth) {
		B prevBoard = boards.get(depth - 1);
		if (prevBoard.endCondition()) return prevBoard;
		
		if (depth == boards.size()) boards.add(boardSupplier.get());
		B board = boards.get(depth);
		boards.get(depth - 1).copyTo(board);
		board.depth = depth - 1;
		
		int[] commands = board.getCommands();
		if (commands == null) return null;
		for (int i: commands) {
			boards.get(depth - 1).copyTo(board);
			board.depth = depth - 1;
			int result = board.process(i);
			if (result >= 0) {
				if (best < result) { best = result; bestBoard = board; }
				B res = process(depth + 1);
				if (res != null)
					return res;
			} else if (result == DepthBoard.END) return board;
		}
		return null;
	}
}
