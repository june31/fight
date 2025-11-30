package aoc24;

import tools.bfs.BFS;
import tools.scanner.Scan;
import tools.strings.S;
import tools.tables.Table;
import tools.tuple.Pos;

public class Day10a {
	private static long z = 0;
	public static void main(String[] args) {
		var map = Scan.readMapRaw();
		BFS bfs = new BFS(map, false);
		bfs.setMoveCondition(b -> b.v2 == b.v1 + 1);
		bfs.setSideEffect(b -> { if (b.v2 == '9') z++; });
		for (Pos p: Table.findAll(map, '0')) bfs.diffuse(p);
		S.o(z);
	}
}