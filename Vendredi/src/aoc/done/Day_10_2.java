package aoc.done;

import java.util.List;

import tools.bfs.BFS2D;
import tools.scanner.Scan;
import tools.tables.Table;
import tools.tuple.Pos;

public class Day_10_2 {

	private static int[][] map; 
	private static int[][] zoom; 

	public static void main(String[] args) {
		map = Scan.readMap0();
		int L = map.length;
		int C = map[0].length;
		zoom = new int[3 * L][3 * C];

		Pos p = Table.find(map, 'S');
		if (List.of('-', '7', 'J').contains((char) map[p.l][p.c+1])) follow(p, 0, 1);
		else if (List.of('-', 'F', 'L').contains((char) map[p.l][p.c-1])) follow(p, 0, -1);
		else follow(p, 1, 0);
		
		BFS2D bfs = new BFS2D(zoom);
		bfs.diffuse(new Pos(0, 0), '#', () -> { zoom[bfs.l2][bfs.c2] = '#'; return false; });
		Table.printMap(zoom);
		int s = 0;
		for (int l = 0; l < L; l++) for (int c = 0; c < C; c++) if (zoom[l*3+1][c*3+1] != '#') s++;
		System.out.println(s);
	}

	private static void follow(Pos p, int dl, int dc) {
		do {
			map[p.l][p.c] = 'S';
			zoom[p.l*3+1][p.c*3+1] = '#';
			zoom[p.l*3+1+dl][p.c*3+1+dc] = '#';
			p = new Pos(p.l+dl, p.c+dc);
			zoom[p.l*3+1-dl][p.c*3+1-dc] = '#';
			int x = map[p.l][p.c];
			if (x == 'J') if (dc == 1) { dc = 0; dl = -1; } else { dc = -1; dl = 0; }
			if (x == '7') if (dc == 1) { dc = 0; dl = 1; } else { dc = -1; dl = 0; }
			if (x == 'F') if (dc == -1) { dc = 0; dl = 1; } else { dc = 1; dl = 0; }
			if (x == 'L') if (dc == -1) { dc = 0; dl = -1; } else { dc = 1; dl = 0; }
		} while (map[p.l][p.c] != 'S');
	}
}
