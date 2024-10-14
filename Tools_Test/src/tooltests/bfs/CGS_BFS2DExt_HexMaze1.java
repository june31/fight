package tooltests.bfs;

import tools.bfs.BFS2DExt;
import tools.bfs.util.BFS2DHelper;
import tools.scanner.Scan;
import tools.tables.Table;
import tools.tuple.Pos;

// https://www.codingame.com/ide/puzzle/hexagonal-maze
public class CGS_BFS2DExt_HexMaze1 {
	
	static int[][] map;
	
	public static void main(String[] args) {
		map = Scan.readMapCL();
		BFS2DExt bfs = new BFS2DExt(map);
		bfs.setMoves(BFS2DHelper.hexa(bfs));
		bfs.setCyclic(true, true);
		Pos s = Table.find(map, 'S');
		Pos e = Table.find(map, 'E');
		bfs.wall('#').end(e);
		bfs.diffuse(s);
		bfs.shortestPath().stream()
			.skip(1) // S
			.forEach(p -> map[p.l][p.c] = '.');
		map[e.l][e.c] = 'E';
		Table.printMap(map);
	}
}
