package training;

import java.util.ArrayList;
import java.util.List;

import tools.bfs.BFSGraph;
import tools.collections.int32.L;
import tools.collections.pos.Lp;
import tools.scanner.Scan;
import tools.structures.graph.node.Node;
import tools.tuple.Pos;

public class Iso_1 {
	public static void main(String[] args) {
		int C = Scan.readInt();
		int L = Scan.readInt();
		int[][] map = new int[L][C];
		int nb = Scan.readInt();
		List<Lp> antas = new ArrayList<>();
		for (int i = 1; i <= nb; i++) {
			String s = Scan.readLine();
			boolean zone = s.charAt(0) == 'D';
			L a = new L(s);
			if (zone) {
				boolean found = false;
				for (int c = a.get(0); c <= a.get(1); c++) {
					for (int l = a.get(2); l <= a.get(3); l++) {
						if (!found && map[l][c] != 0) {
							Node.buildDual(i, map[l][c]);
							found = true;
						} else {
							map[l][c] = i;
						}
					}
				}
			} else {
				// z
				
				int p1 = map[a.get(2)][a.get(0)];
				if (p1 != 0) {
					Node.buildDual(i, p1);
				} else {
					map[a.get(2)][a.get(0)] = i;
				}
				int p2 = map[a.get(3)][a.get(1)];
				if (p2 != 0) {
					Node.buildDual(i, p2);
				} else {
					map[a.get(3)][a.get(1)] = i;
				}
				Lp anta = Lp.of(new Pos(a.get(2), a.get(0)), new Pos(a.get(3), a.get(1)));
				antas.add(anta);
			}
		}
		
		int[] vals = new int[nb + 1];
		vals[1] = 'A';
		BFSGraph bfs = new BFSGraph();
		bfs.diffuse(Node.get(1), () -> {
			vals[bfs.n2.id] = 'A';
			return false;
		});
		
		for (Lp anta : antas) {
			int p1 = map[anta.get(0).l][anta.get(0).c];
			int p2 = map[anta.get(1).l][anta.get(1).c];
			if (p1 == 0 || p2 == 0) {
				System.out.println("IMPOSSIBLE");
				return;
			}
			if (vals[p1] == vals[p2]) {
				System.out.println("IMPOSSIBLE");
				return;
			}
		}

	}
}
