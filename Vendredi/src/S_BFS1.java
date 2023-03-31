import tools.bfs.BFS2D;
import tools.scanner.Scan;
import tools.tables.Table;
import tools.tuple.Pos;

public class S_BFS1 {
	public static void main(String[] args) {
		int[][] map = Scan.readMap1();
		int ln = map.length;
		int cn = map[0].length;
		
		BFS2D bfs = new BFS2D(map);
		loop: for (int l = 0; l < ln; l++) {
			for (int c = 0; c < cn; c++) {
				if (map[l][c] == 'a') {
					bfs.diffuse(l, c, () -> bfs.v2 == bfs.v1 + 1, () -> bfs.v2 == 'z');
					if (bfs.found) break loop;
				}
			}
		}
		
		int[][] sol = new int[ln][cn];
		Table.fill(sol, '-');
		for (Pos p: bfs.shortestPath()) sol[p.l][p.c] = map[p.l][p.c];
		Table.showMap(sol);
	}
}
