package tools.bfs;

import java.util.ArrayList;
import java.util.List;

import tools.bfs.util.BFS2DBase;
import tools.tuple.Pos;

public final class BFS2DExt extends BFS2DBase<BFS2DExt> {

	public BFS2DExt(int[][] table) {
		super(table);
	}
	
	public BFS2DExt diffuse(int startLine, int startCol) {
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
		v2 = tab[l2][c2];
		if (firstEffect) sideEffect.run();
		if (endCondition.getAsBoolean()) return this;
		if (testStart && !moveCondition.getAsBoolean()) return this;
		scanned = 1;
		backtrack[startLine * colNb + startCol] = -1;
		turn = 1;
		
		while (true) {
			for (int i = 0; i < currentL.size(); i++) {
				l1 = currentL.get(i);
				c1 = currentC.get(i);
				v1 = tab[l1][c1];
				for (int r = 0; r < moves.length; r++) {
					l2 = l1;
					c2 = c1;
					moves[r].run();
					if (l2 < 0) if (vCycle) l2 = lineNb - 1; else continue;
					if (l2 >= lineNb) if (vCycle) l2 = 0; else continue;
					if (c2 < 0) if (hCycle) c2 = colNb - 1; else continue;
					if (c2 >= colNb) if (hCycle) c2 = 0; else continue;
					long back = backtrack[l2 * colNb + c2];
					if (back != 0) continue;
					v2 = tab[l2][c2];
					if (!moveCondition.getAsBoolean()) continue;
					backtrack[l2 * colNb + c2] = USED_BIT | l1 | (((long) c1) << 32); 
					scanned++;
					if (teleport != null) {
						Pos p = teleport.get();
						if (p != null) {
							sideEffect.run();
							if (endCondition.getAsBoolean()) return this;
							backtrack[p.l * colNb + p.c] = USED_BIT | l2 | (((long) c2) << 32); 
							l2 = p.l;
							c2 = p.c;
							v2 = tab[l2][c2];
							scanned++;
						}
					}
					sideEffect.run();
					if (endCondition.getAsBoolean()) return this;
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
