package aoc2022.day12;

import java.io.IOException;

import tools.bfs.BFS2D;
import tools.scanner.Scan;
import tools.tuple.Pos;

public class HillClimbing_1_2 {

	static { Scan.open("input2.txt"); }

	public static void main(String[] args) throws IOException {
		Pos e = null;
		Pos s = null;
		int[][] map = Scan.readMap0();
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
		System.out.println(bfs.diffuse(s, () -> bfs.v2 - bfs.v1 <= 1, () -> bfs.v2 == 'z' + 1));
		System.out.println(bfs.shortestPath(bfs.l2, bfs.c2));
		System.out.println(bfs.diffuse(e, () -> bfs.v1 - bfs.v2 <= 1, () -> bfs.v2 == 'a'));
		System.out.println(bfs.shortestPath(bfs.l2, bfs.c2));
	}
}