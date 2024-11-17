package tools.bfs;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntBinaryOperator;

import tools.bfs.util.BFS2DBase;
import tools.math.Num;

public final class BFS2DWExt extends BFS2DBase<BFS2DWExt> {

	public int maxWeight;
	public IntBinaryOperator wRule;

	public BFS2DWExt(int[][] table, int[][] weights) {
		this(table, (l, c) -> weights[l][c], Num.max(weights).value);
	}
    public BFS2DWExt(int[][] table, int maxWeight) {
    	// Bifunction needs to be set later.
    	this(table, null, maxWeight);
    }
    public BFS2DWExt(int[][] table, IntBinaryOperator weightRule, int maxWeight) {
    	super(table);
		wRule = weightRule;
		this.maxWeight = maxWeight;
	}

	public BFS2DWExt diffuse(int startLine, int startCol) {
		List<List<Integer>> allLs = new ArrayList<>(maxWeight + 1);
		List<List<Integer>> allCs = new ArrayList<>(maxWeight + 1);
		for (int i = 0; i <= maxWeight; i++) {
			allLs.add(new ArrayList<>());
			allCs.add(new ArrayList<>());
		}
		int index = 0;

		allLs.get(0).add(startLine);
		allCs.get(0).add(startCol);
		if (!clean) for (int i = 0; i < backtrack.length; i++) backtrack[i] = 0;
		clean = false;
		turn = 0;
		found = true;
		scanned = 0;
		l2 = startLine;
		c2 = startCol;
		v2 = map[l2][c2];
		if (firstEffect) sideEffect.run();
		if (endCondition.getAsBoolean()) return this;
		if (testStart && !moveCondition.getAsBoolean()) return this;
		scanned = 1;
		backtrack[startLine * colNb + startCol] = -1;
		turn = 1;
		int drought = 0;

		while (true) {
			List<Integer> currentL = allLs.get(index);
			if (currentL.size() > 0) {
				drought = index;
				List<Integer> currentC = allCs.get(index);
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
						if (l2 < 0 || l2 >= lineNb || c2 < 0 || c2 >= colNb) continue;
						long back = backtrack[l2 * colNb + c2];
						if (back != 0) continue;
						v2 = map[l2][c2];
						if (!moveCondition.getAsBoolean()) continue;
						backtrack[l2 * colNb + c2] = USED_BIT | l1 | (((long) c1) << 32); 
						scanned++;
						sideEffect.run();
						if (endCondition.getAsBoolean()) return this;
						int w = wRule.applyAsInt(l2, c2);
						allLs.get((index + w) % (maxWeight + 1)).add(l2);
						allCs.get((index + w) % (maxWeight + 1)).add(c2);
					}
				}
				currentL.clear();
				currentC.clear();
			}
			turn++;
			index = (index + 1) % (maxWeight + 1);
			if (index == drought) return this;
		}
	}
}
