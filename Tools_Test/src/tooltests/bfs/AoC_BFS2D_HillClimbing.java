package tooltests.bfs;

import java.io.IOException;

import tools.bfs.BFS2D;
import tools.scanner.Scan;
import tools.tuple.Pos;

// https://adventofcode.com/2022/day/12
public class AoC_BFS2D_HillClimbing {

	public static void main(String[] args) throws IOException {
		Pos e = null;
		Pos s = null;
		int[][] map = Scan.readRawMap();
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] == 'S') {
					map[i][j] = 'a' - 1;
					s = new Pos(i, j);
				} else if (map[i][j] == 'E') {
					map[i][j] = 'z' + 1;
					e = new Pos(i, j);
				}
			}
		}
		BFS2D bfs = new BFS2D(map);
		bfs.move(() -> bfs.v2 - bfs.v1 <= 1).end('z' + 1);
		System.out.println(bfs.diffuse(s));
		System.out.println("Scanned:" + bfs.scanned);
		System.out.println(bfs.shortestPath());
		bfs.move(() -> bfs.v1 - bfs.v2 <= 1).end('a');
		System.out.println(bfs.diffuse(e));
		System.out.println("Scanned:" + bfs.scanned);
		System.out.println(bfs.shortestPath());
	}
}