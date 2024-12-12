package tools.bfs;

import java.util.ArrayList;
import java.util.List;

import tools.bfs.util.BFS2DBase;

public final class BFS extends BFS2DBase<BFS> {

	public BFS(int[][] table) {
		super(table);
	}

	public BFS(int[][] table, boolean testStart) {
		super(table);
		this.testStart = testStart; 
	}

	public BFS diffuse(int startLine, int startCol) {
		List<Integer> currentL = new ArrayList<>();
		List<Integer> nextL = new ArrayList<>();
		List<Integer> currentC = new ArrayList<>();
		List<Integer> nextC = new ArrayList<>();
		currentL.add(startLine);
		currentC.add(startCol);
		if (!clean) for (int i = 0; i < backtrack.length; i++) backtrack[i] = 0;
		clean = false;
		turn = 0;
		found = true;
		scanned = 0;
		l2 = startLine;
		c2 = startCol;
		v2 = map[l2][c2];
		if (firstEffect) sideEffect.accept(this);
		if (endCondition.test(this)) return this;
		if (testStart && !moveCondition.test(this)) return this;
		if (testStart) sideEffect.accept(this);
		scanned = 1;
		backtrack[startLine * colNb + startCol] = -1;
		turn = 1;
		
		while (true) {
			for (int i = 0; i < currentL.size(); i++) {
				l1 = currentL.get(i);
				c1 = currentC.get(i);
				v1 = map[l1][c1];
				l2 = l1; // Set once for moves.apply
				c2 = c1;
				for (Runnable move: moves.apply(this)) {
					l2 = l1; // Set a second time for move.run
					c2 = c1;
					move.run();
					if (l2 < 0) if (vCycle) l2 = lineNb - 1; else continue;
					if (l2 >= lineNb) if (vCycle) l2 = 0; else continue;
					if (c2 < 0) if (hCycle) c2 = colNb - 1; else continue;
					if (c2 >= colNb) if (hCycle) c2 = 0; else continue;
					long back = backtrack[l2 * colNb + c2];
					if (back != 0) continue;
					v2 = map[l2][c2];
					if (!moveCondition.test(this)) continue;
					backtrack[l2 * colNb + c2] = USED_BIT | l1 | (((long) c1) << 32); 
					scanned++;
					sideEffect.accept(this);
					if (endCondition.test(this)) return this;
					nextL.add(l2);
					nextC.add(c2);
				}
			}
			if (nextL.size() == 0) {
				found = false;
				break;
			}

			List<Integer> tmp = currentL;
			currentL = nextL;
			nextL = tmp;
			nextL.clear();
			tmp = currentC;
			currentC = nextC;
			nextC = tmp;
			nextC.clear();
			turn++;
		}

		return this;
	}
}
