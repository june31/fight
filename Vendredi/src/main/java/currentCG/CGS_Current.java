package currentCG;

import tools.bfs.BFS2D;
import tools.math.Num;
import tools.scanner.Scan;
import tools.tables.Table;
import tools.tuple.Pos;

public class CGS_Current {

	public static void main(String[] args) {
		int W = Scan.readInt();
		int H = Scan.readInt();
		int P = Scan.readInt();
		int Q = Scan.readInt();
		int R = Scan.readInt();
		long lcm = Num.lcm(P-1, Q-1);
		
		int[][] map = Table.wall(Table.fill(new int[2 * H - 1][2 * W - 1], '.'), '#');
		for (int l = 0; l < H-1; l++) {
			for (int c = 0; c < W-1; c++) {
				map[2 * l + 2][2 * c + 2] = '#';
				long exp = Num.powMod(2, c + l*W + 1, lcm);
				if (Num.powMod(R, exp, P*Q) % 2 == 1) map[2 * l + 1][2 * c + 2] = '#';
				else map[2 * l + 2][2 * c + 1] = '#';
			}
		}

		BFS2D bfsT = new BFS2D(map).wall('#').diffuse(1, 1);
		int[][] dists = Table.copy(map);
		BFS2D bfsX = new BFS2D(dists).wall('#');
		bfsX.setValue(() -> bfsX.turn).diffuse(2*H-1, 2*W-1);
		Pos pMin = null;
		int dMin = Integer.MAX_VALUE;
		for (Pos p : bfsT.shortestPath(bfsT.l1, bfsT.c1)) {
			if (dists[p.l][p.c] < dMin) {
				dMin = dists[p.l][p.c];
				pMin = p;
			}
		}
		map[pMin.l][pMin.c] = 'X';
		map[bfsT.l1][bfsT.c1] = 'T';
		map[0][1] = map[2*H][2*W-1] = '.';
		Table.printMap(map);
	}
}
