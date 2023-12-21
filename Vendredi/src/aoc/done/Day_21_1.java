package aoc.done;

import tools.bfs.BFS2D;
import tools.scanner.Scan;
import tools.tables.Table;
import tools.tuple.Pos;

public class Day_21_1 {
	
	private static int[][] map = Scan.readMap0();
	private static int L = map.length;
	private static int C = map[0].length;
	private static BFS2D bfs = new BFS2D(map);
	private static long s = 0;
	
	public static void main(String[] args) {
		Pos p = Table.find(map, 'S');
		bfs.diffuse('S', '#', () -> { map[bfs.l2][bfs.c2] = 'X'; return bfs.turn == 65; });
		for (int l = 0; l < L; l++) for (int c = 0; c < C; c++) if (map[l][c] == 'X' && (l + c) % 2 == (p.l + p.c) % 2) s++;
		System.out.println(s);
	}
}
