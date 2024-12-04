package tooltests.bfs;

import java.io.IOException;

import tools.bfs.BFS2D;
import tools.scanner.Scan;
import tools.tables.Table;
import tools.tuple.Pos;

// https://adventofcode.com/2022/day/12
public class AoC_BFS2DExt_HillClimbing {

	public static void main(String[] args) throws IOException {
		int[][] map = Scan.readRawMap();
		Pos S = Table.find(map, 'S');
		Pos E = Table.find(map, 'E');
		map[S.l][S.c] = 'a' - 1;
		map[E.l][E.c] = 'z' + 1;
		BFS2D bfs = new BFS2D(map);
		bfs.moveCondition = () -> bfs.v2 - bfs.v1 <= 1;
		bfs.reach(S, E);
	}
}