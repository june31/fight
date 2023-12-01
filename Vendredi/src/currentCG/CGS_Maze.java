package currentCG;

import tools.bfs.BFS2D;
import tools.bfs.BFS2DExt;
import tools.collections.pos.Lp;
import tools.scanner.Scan;
import tools.tables.Table;
import tools.tuple.Pos;

public class CGS_Maze {
	static Pos s;
	static Pos e;
	static BFS2D bfs;
	static BFS2DExt bfsElf;
	static int wpl1 = 0, wpl2 = 0, wpc1 = 0, wpc2 = 0;

	public static void main(String[] args) {
		int nc = Scan.readInt();
		int nl = Scan.readInt();
		int[][] map = Scan.readMap(nl);
		Table.forEach(map, (l, c, v) -> {
			if (c == 0 && v == '>') s = new Pos(l, c);
			if (c == nc - 1 && v == '<') s = new Pos(l, c);
			if (l == 0 && v == 'v') s = new Pos(l, c);
			if (l == nl - 1 && v == '^') s = new Pos(l, c);
			if (c == 0 && v == '<') e = new Pos(l, c);
			if (c == nc - 1 && v == '>') e = new Pos(l, c);
			if (l == 0 && v == '^') e = new Pos(l, c);
			if (l == nl - 1 && v == 'v') e = new Pos(l, c);
		});
		
		// Warrior
		bfs = new BFS2D(map);
		int bestScore = bfs.diffuse(s, '#', e) * 2;
		String bestHero = "WARRIOR";
		Lp heroPath = bfs.shortestPath();
		Lp bestPath = heroPath;
		
		// DWARF
		int score = bfs.diffuse(s, () -> dwarf(), e) * 3;
		if (score < bestScore) {
			bestScore = score;
			bestHero = "DWARF";
			bestPath = bfs.shortestPath();
		}
		
		// ELF
		bfsElf = new BFS2DExt(map);
		bfsElf.setMoves(new Runnable[] {
                () -> { bfsElf.c2++; },
                () -> { bfsElf.l2++; },
                () -> { bfsElf.c2--; },
                () -> { bfsElf.l2--; },
                () -> { bfsElf.c2++; bfsElf.l2++; },
                () -> { bfsElf.c2--; bfsElf.l2++; },
                () -> { bfsElf.c2--; bfsElf.l2--; },
                () -> { bfsElf.c2++; bfsElf.l2--; }
        });
		score = bfsElf.diffuse(s, '#', e) * 4;
		if (score < bestScore) {
			bestScore = score;
			bestHero = "ELF";
			bestPath = bfs.shortestPath();
		}
		
		// WIZARD
		Lp wizPath = heroPath.filter((i, p) -> {
			boolean ret = p.l - wpl1 != wpl1 - wpl2 || p.c - wpc1 != wpc1 - wpc2;
			wpl2 = wpl1;
			wpl1 = p.l;
			wpc2 = wpc1;
			wpc1 = p.c;
			return ret;
		});
		score = wizPath.size() * 5;
		if (score < bestScore) {
			bestScore = score;
			bestHero = "WIZARD";
			bestPath = wizPath;
		}
		
		// Result
		for (Pos p: bestPath) {
			
		}
		System.out.println(bestHero + " " + bestScore);
	}
	
	private static boolean dwarf() {
		return false;
	}
}
