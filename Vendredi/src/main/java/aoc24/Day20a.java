package aoc24;

import tools.bfs.BFS;
import tools.scanner.Scan;
import tools.strings.S;

public class Day20a {
	public static void main(String[] args) {
		int z = 0;
		var map = Scan.readRawMap();
		var bfs = new BFS(map).setEnd('E');
		int ref = bfs.diffuse('S').turn;
		for (int i = 0; i < ref; i++) {
			int f = i;
			bfs.setMoveCondition(b -> b.turn == f && (b.l2 != b.l1) || b.v2 != '#');
			if (ref - bfs.diffuse('S').turn >= 100) z++;
			bfs.setMoveCondition(b -> b.turn == f && (b.c2 != b.c1) || b.v2 != '#');
			if (ref - bfs.diffuse('S').turn >= 100) z++;
		}
		S.o(z);
	}
}
