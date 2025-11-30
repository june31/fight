package aoc24;

import tools.bfs.BFS;
import tools.collections.pos.Lp;
import tools.scanner.Scan;
import tools.strings.S;
import tools.tables.Table;
import tools.tuple.Pos;

public class Day12b {
	public static void main(String[] args) {
		long z = 0;
		var map = Scan.readMapRaw();
		BFS bfs = new BFS(map);
		for (int l = 0; l < map.length; l++) {
			for (int c = 0; c < map[0].length; c++) {
				int v = map[l][c];
				if (v == '.') continue;
				bfs.setMoveCondition(b -> b.v2 == v);
				bfs.diffuse(l, c);
				Lp uperims = new Lp();
				Lp dperims = new Lp();
				Lp lperims = new Lp();
				Lp rperims = new Lp();
				for (Pos p: bfs.visited) {
					if (Table.get(map, p.up()) != v) uperims.add(p);
					if (Table.get(map, p.down()) != v) dperims.add(p);
					if (Table.get(map, p.left()) != v) lperims.add(p);
					if (Table.get(map, p.right()) != v) rperims.add(p);
				}
				z += bfs.visited.size() * (uperims.filtered(p -> !uperims.contains(p.right())).size()
						+ dperims.filtered(p -> !dperims.contains(p.right())).size()
						+ lperims.filtered(p -> !lperims.contains(p.down())).size()
						+ rperims.filtered(p -> !rperims.contains(p.down())).size());
				for (Pos p: bfs.visited) map[p.l][p.c] = '.';
			}
		}
		S.o(z);
	}
}
