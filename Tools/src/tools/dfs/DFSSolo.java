package tools.dfs;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import tools.board.SoloBoard;

public class DFSSolo<B extends SoloBoard> {
	
	private Supplier<B> boardSupplier;
	public List<B> boards = new ArrayList<>();
	public int depth;
	
	public DFSSolo(Supplier<B> boardSupplier) {
		this.boardSupplier = boardSupplier;
		boards.add(boardSupplier.get());
	}
	
	public B process() {
		depth = 0;
		int result = SoloBoard.INIT;
		while (depth >= 0) {
			B board = boards.get(depth);
			result = board.process(result);
			if (result >= 0) {
				if (++depth == boards.size()) boards.add(boardSupplier.get());
				board.copyTo(boards.get(depth));
			} else if (result == SoloBoard.FAIL) depth--;
			else return board;
		}
		return null;
	}
}
