package aoc24;

import java.util.HashMap;

import tools.bfs.BFS;
import tools.collections.pos.Lp;
import tools.math.Dist;
import tools.scanner.Scan;
import tools.strings.S;
import tools.tuple.Pos;

public class Day20b {
	public static void main(String[] args) {
		long z = 0;
		var map = Scan.readRawMap();
		var dists = new HashMap<Pos, Integer>();
		var bfs = new BFS(map).setEnd('E');
		bfs.setSideEffect(b -> dists.put(new Pos(b.l2, b.c2), b.turn));
		int ref = bfs.diffuse('S').turn;
		Lp lp = bfs.shortestPath();
		for (Pos p1: lp) {
			for (Pos p2: lp) {
				int m = Dist.manh(p1, p2);
				if (m > 20) continue;
				if (ref - (dists.get(p1) + m + (ref - dists.get(p2))) >= 100) z++;
			}
		}
		S.o(z);
	}
}
