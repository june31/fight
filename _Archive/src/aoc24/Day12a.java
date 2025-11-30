package aoc24;

import tools.bfs.BFS;
import tools.scanner.Scan;
import tools.strings.S;
import tools.tables.Table;
import tools.tuple.Pos;

public class Day12a {
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
				int perims = 0;
				for (Pos p: bfs.visited) {
					if (Table.get(map, p.up()) != v) perims++;
					if (Table.get(map, p.down()) != v) perims++;
					if (Table.get(map, p.left()) != v) perims++;
					if (Table.get(map, p.right()) != v) perims++;
				}
				z += bfs.visited.size() * perims;
				for (Pos p: bfs.visited) map[p.l][p.c] = '.';
			}
		}
		S.o(z);
	}
}
