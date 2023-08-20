package currentCG;

import tools.bfs.BFS2DExt;
import tools.scanner.Scan;
import tools.tables.Table;

// https://www.codingame.com/ide/puzzle/moves-in-maze
public class CGS_MovesInMaze {
	static int[][] finalMap;
	static BFS2DExt bfs;
	
	public static void main(String[] args) {
		int[][] map = Scan.readMapCL();
		finalMap = Table.copy(map);
		bfs = new BFS2DExt(map);
		bfs.setCyclic(true, true);
		bfs.diffuse('S', '#', () -> {
			finalMap[bfs.l2][bfs.c2] = bfs.turn + (bfs.turn < 10 ? 48 : 55);
			return false;
		});
		Table.printMap(finalMap);
	}
}
