package tools.bfs;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

import tools.bfs.util.BFSExt;
import tools.function.BiIntToIntFunction;
import tools.math.Num;
import tools.tuple.Pos;

public final class BFS2DWExt extends BFSExt {

	public int maxW;
	public BiIntToIntFunction wRule;

	public BFS2DWExt(int[][] table, int[][] weights) {
		this(table, (l, c) -> weights[l][c], Num.max(weights).value);
	}
    public BFS2DWExt(int[][] table, int maxWeight) {
    	// Bifunction needs to be set later.
    	this(table, null, maxWeight);
    }
    public BFS2DWExt(int[][] table, BiIntToIntFunction weightRule, int maxWeight) {
    	super(table);
		wRule = weightRule;
		maxW = maxWeight;
	}

	public int diffuse(int startLine, int startCol, BooleanSupplier move, BooleanSupplier end, boolean testStart) {
		List<List<Integer>> allLs = new ArrayList<List<Integer>>(maxW + 1);
		List<List<Integer>> allCs = new ArrayList<List<Integer>>(maxW + 1);
		for (int i = 0; i <= maxW; i++) {
			allLs.add(new ArrayList<>());
			allCs.add(new ArrayList<>());
		}
		int index = 0;

		allLs.get(0).add(startLine);
		allCs.get(0).add(startCol);
		moveCondition = move;
		endCondition = end;
		if (!clean) for (int i = 0; i < backtrack.length; i++) backtrack[i] = 0;
		clean = false;
		turn = 0;
		found = true;
		scanned = 0;
		l2 = startLine;
		c2 = startCol;
		v2 = tab[l2][c2];
		if (endCondition.getAsBoolean()) return 0;
		if (testStart && !move.getAsBoolean()) return 0;
		scanned = 1;
		backtrack[startLine * colNb + startCol] = -1;
		turn = 1;
		int drought = 0;

		while (true) {
			List<Integer> currentL = allLs.get(index);
			if (currentL.size() > 0) {
				List<Integer> currentC = allCs.get(index);
				for (int i = 0; i < currentL.size(); i++) {
					drought = index;
					l1 = currentL.get(i);
					c1 = currentC.get(i);
					v1 = tab[l1][c1];
					Runnable[] posMoves = getMoves();
					for (int r = 0; r < posMoves.length; r++) {
						l2 = l1;
						c2 = c1;
						posMoves[r].run();
						if (l2 < 0 || l2 >= lineNb || c2 < 0 || c2 >= colNb) continue;
						long back = backtrack[l2 * colNb + c2];
						if (back != 0) continue;
						v2 = tab[l2][c2];
						if (!moveCondition.getAsBoolean()) continue;
						backtrack[l2 * colNb + c2] = USED_BIT | l1 | (((long) c1) << 32); 
						scanned++;
						if (teleport != null) {
							Pos p = teleport.get();
							if (p != null) {
								if (endCondition.getAsBoolean()) return turn;
								backtrack[p.l * colNb + p.c] = USED_BIT | l2 | (((long) c2) << 32); 
								l2 = p.l;
								c2 = p.c;
								v2 = tab[l2][c2];
								scanned++;
							}
						}
						if (endCondition.getAsBoolean()) return turn;
						int w = wRule.applyAsInt(l2, c2);
						allLs.get((index + w) % (maxW + 1)).add(l2);
						allCs.get((index + w) % (maxW + 1)).add(c2);
					}
				}
				currentL.clear();
				currentC.clear();
			}
			turn++;
			index = (index + 1) % (maxW + 1);
			if (index == drought) {
				return turn - maxW - 1;
			}
		}
	}
}
