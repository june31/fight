package tooltests.bfs;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import tools.bfs.BFSBoard;
import tools.board.Board;
import tools.output.Out;
import tools.scanner.Scan;
import tools.tables.Table;
import tools.tuple.Pos;

// https://www.codingame.com/training/medium/lunar-lockout
public class S_BFSBoard_LunarLookout {

	static int n;

	public static void main(String[] args) {
		int[][] map = Scan.readMap0();
		Map<Integer, Pos> pMap = new TreeMap<>();
		for (int l = 0; l < 5; l++)
			for (int c = 0; c < 5; c++)
				if (map[l][c] != '.') pMap.put(map[l][c], new Pos(l, c));

		n = pMap.size() - 1;
		BFSBoard bfs = new BFSBoard(new B(new ArrayList<>(pMap.values())));
		bfs.diffuse();

		for (Board b : bfs.shortestPath()) if (((B) b).move != null) Out.space(((B) b).move);
		Out.println("\n");

		int[][] sol = new int[5][5];
		Table.fill(sol, '.');
		List<Pos> pl = ((B) bfs.b2).players;
		for (int i = 0; i < n; i++) sol[pl.get(i).l][pl.get(i).c] = 'A' + i;
		sol[pl.get(n).l][pl.get(n).c] = 'X';
		Table.showMap(sol);
	}

	static class B extends Board {
		static final Set<List<Pos>> history = new HashSet<>();
		List<Pos> players = new ArrayList<>();
		List<Board> next = new ArrayList<>();
		String move;

		B(List<Pos> ps) {
			players = ps;
			history.add(players);
		}

		@Override
		public List<Board> next() {
			for (int i = 0; i <= n; i++) {
				checkMove(i, +1, 0, "D");
				checkMove(i, 0, -1, "L");
				checkMove(i, 0, +1, "R");
				checkMove(i, -1, 0, "U");
			}
			return next;
		}

		@Override
		public double eval() { return players.get(n).l == 2 && players.get(n).c == 2 ? Double.POSITIVE_INFINITY : 0.0; }

		private void checkMove(int i, int v, int h, String dir) {
			Pos old = new Pos(players.get(i));
			Pos p = new Pos(old);
			while (true) {
				p.l += v;
				p.c += h;
				if (p.l < 0 || p.l > 4 || p.c < 0 || p.c > 4) return;
				for (Pos o : players) {
					if (o.equals(p)) {
						if (old.equals(players.get(i))) return;
						List<Pos> npl = new ArrayList<>(players);
						npl.set(i, old);
						if (history.contains(npl)) return;
						B nb = new B(npl);
						nb.parent = this;
						nb.move = (char) ((i < n) ? 'A' + i : 'X') + dir;
						next.add(nb);
						return;
					}
				}
				old.l = p.l;
				old.c = p.c;
			}
		}
	}
}
