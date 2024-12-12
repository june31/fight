package aoc24.done;

import tools.bfs.BFS;
import tools.collections.pos.Lp;
import tools.scanner.Scan;
import tools.strings.S;
import tools.tables.Table;
import tools.tuple.Pos;

public class Day12b {
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
				Lp uperims = new Lp();
				Lp dperims = new Lp();
				Lp lperims = new Lp();
				Lp rperims = new Lp();
				for (Pos p: lp) {
					if (Table.get(map, p.up()) != v) uperims.add(p);
					if (Table.get(map, p.down()) != v) dperims.add(p);
					if (Table.get(map, p.left()) != v) lperims.add(p);
					if (Table.get(map, p.right()) != v) rperims.add(p);
				}
				Lp u = new Lp(uperims);
				for (Pos p: uperims) u.remove(p.right());
				Lp d = new Lp(dperims);
				for (Pos p: dperims) d.remove(p.right());
				Lp le = new Lp(lperims);
				for (Pos p: lperims) le.remove(p.down());
				Lp r = new Lp(rperims);
				for (Pos p: rperims) r.remove(p.down());
				
				z += lp.size() * (u.size() + d.size() + le.size() + r.size());
				for (Pos p: lp) map[p.l][p.c] = '.';
			}
		}
		S.o(z);
	}
}
