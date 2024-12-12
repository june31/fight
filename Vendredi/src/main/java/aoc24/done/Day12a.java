package aoc24.done;

import tools.bfs.BFS;
import tools.collections.pos.Lp;
import tools.scanner.Scan;
import tools.strings.S;
import tools.tables.Table;
import tools.tuple.Pos;

public class Day12a {
	private static Lp lp = new Lp();
	public static void main(String[] args) {
		long z = 0;
		var map = Scan.readRawMap();
		BFS bfs = new BFS(map);
		int nl = map.length;
		int nc = map[0].length;
		for (int l = 0; l < nl; l++) {
			for (int c = 0; c < nc; c++) {
				int v = map[l][c];
				if (v == '.') continue;
				lp.clear();
				lp.add(new Pos(l, c));
				bfs.setMoveCondition(b -> b.v2 == v);
				bfs.setSideEffect(b -> { lp.add(new Pos(b.l2, b.c2)); });
				bfs.diffuse(l, c);
				int perims = 0;
				for (Pos p: lp) {
					if (Table.get(map, p.up()) != v) perims++;
					if (Table.get(map, p.down()) != v) perims++;
					if (Table.get(map, p.left()) != v) perims++;
					if (Table.get(map, p.right()) != v) perims++;
				}
				z += lp.size() * perims;
				for (Pos p: lp) map[p.l][p.c] = '.';
			}
		}
		S.o(z);
	}
}
