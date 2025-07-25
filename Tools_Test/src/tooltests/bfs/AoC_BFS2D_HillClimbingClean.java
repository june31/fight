package tooltests.bfs;

import java.io.IOException;

import tools.bfs.BFS2D;
import tools.scanner.Scan;
import tools.tables.Table;
import tools.tuple.Pos;

// https://adventofcode.com/2022/day/12
public class AoC_BFS2D_HillClimbingClean {

	public static void main(String[] args) throws IOException {
		int[][] map = Scan.readMapRaw();
		Pos s = Table.find(map, 'S');
		Pos e = Table.find(map, 'E');
		map[s.l][s.c] = 'a' - 1;
		map[e.l][e.c] = 'z' + 1;

		BFS2D bfs = new BFS2D(map);
		bfs.move(() -> bfs.v2 - bfs.v1 <= 1).end('z' + 1);
		System.out.println(bfs.diffuse(s));
		bfs.move(() -> bfs.v1 - bfs.v2 <= 1).end('a');
		System.out.println(bfs.diffuse(e));
	}
}