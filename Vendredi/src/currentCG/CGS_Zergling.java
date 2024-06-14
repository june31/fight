package currentCG;

import tools.bfs.BFS2D;
import tools.scanner.Scan;
import tools.tables.Table;
import tools.tuple.Pos;

public class CGS_Zergling {
	public static void main(String[] args) {
		int c = Scan.readInt();
		int l = Scan.readInt();
		int[][] map = Scan.readMap(l);
		Pos[] Bs = Table.findAll(map, 'B');
		int[][] map2 = Table.copy(map);
		BFS2D bfs = new BFS2D(map2);
		bfs.setSideEffect(true, 'x');
		for (int i = 0; i < c; i++) {
			if (map2[0][i] == '.') bfs.diffuse(new Pos(0, i), () -> bfs.v2 == '.', () -> false);
			if (map2[l-1][i] == '.') bfs.diffuse(new Pos(l-1, i), () -> bfs.v2 == '.', () -> false);
		}
		for (int i = 0; i < l; i++) {
			if (map2[i][0] == '.') bfs.diffuse(new Pos(i, 0), () -> bfs.v2 == '.', () -> false);
			if (map2[i][c - 1] == '.') bfs.diffuse(new Pos(i, c - 1), () -> bfs.v2 == '.', () -> false);
		}
		for (Pos p : Bs) {
			if (map2[p.l-1][p.c] == 'x') map[p.l-1][p.c] = 'z';
			if (map2[p.l+1][p.c] == 'x') map[p.l+1][p.c] = 'z';
			if (map2[p.l][p.c-1] == 'x') map[p.l][p.c-1] = 'z';
			if (map2[p.l][p.c+1] == 'x') map[p.l][p.c+1] = 'z';
			if (map2[p.l-1][p.c-1] == 'x') map[p.l-1][p.c-1] = 'z';
			if (map2[p.l-1][p.c+1] == 'x') map[p.l-1][p.c+1] = 'z';
			if (map2[p.l+1][p.c-1] == 'x') map[p.l+1][p.c-1] = 'z';
			if (map2[p.l+1][p.c+1] == 'x') map[p.l+1][p.c+1] = 'z';
		}
		Table.printMap(map);
	}
}
