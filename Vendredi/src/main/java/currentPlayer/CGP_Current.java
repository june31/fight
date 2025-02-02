package currentPlayer;

import java.util.Map;

import tools.collections.int32.Si;
import tools.collections.map.Mpi;
import tools.collections.pos.Lp;
import tools.enumeration.combinations.Combinations;
import tools.scanner.Scan;
import tools.strings.S;
import tools.tables.Table;
import tools.tuple.Pos;
public class CGP_Current {
	private static final Pos W = new Pos(-1, -1);
	static int nl, nc;
	static Lp mainKept;
	public static void main(String[] args) {
		Scan.setDebugMode();
		nc = Scan.readInt();
		nl = Scan.readInt();
		int[][] map = Scan.readMap(nl);
		int turns = Scan.readInt();
		int bombs = Scan.readInt();
		
		Lp sol = calc(map, Math.min(bombs, turns));

		if (sol.isEmpty()) {
			// Blow 1, wait 3, then refresh
			for (Pos p: mainKept) {
				int[][] map2 = Table.copy(map);
				int l = p.l;
				int c = p.c;
				for (int i = 1; i <= 3; i++) {
					if (Table.get(map2, l + i, c) == '#') break;
					Table.set(map2, l + i, c, '.');
				}
				for (int i = 1; i <= 3; i++) {
					if (Table.get(map2, l - i, c) == '#') break;
					Table.set(map2, l - i, c, '.');
				}
				for (int i = 1; i <= 3; i++) {
					if (Table.get(map2, l, c + i) == '#') break;
					Table.set(map2, l, c + i, '.');
				}
				for (int i = 1; i <= 3; i++) {
					if (Table.get(map2, l, c - i) == '#') break;
					Table.set(map2, l, c - i, '.');
				}
				Lp sol2 = calc(map2, Math.min(bombs-1, turns-4));
				if (!sol2.isEmpty()) {
					sol.add(p);
					sol.add(W);
					sol.add(W);
					sol.add(W);
					sol.addAll(sol2);
					break;
				}
			}
		}
		while (sol.size() < turns) sol.add(W);
		
		int i = 0;
		while (turns > 0) {
			Pos p = sol.get(i);
			if (p == W) S.o("WAIT");
			else S.o(p.c, p.l);
			i++;
			if (turns > 1) {
				turns = Scan.readInt();
				Scan.readInt();
			}
		}
	}
	
	private static Lp calc(int[][] map, int max) {
		Pos[] survs = Table.findAll(map, '@');
		Mpi mpi = new Mpi();
		int id = 0;
		for (Pos surv: survs) mpi.put(surv, 1 << id++);
		Mpi interesting = new Mpi();
		for (int l = 0; l < nl; l++) {
			for (int c = 0; c < nc; c++) {
				if (map[l][c] == '.') {
					int m = 0;
					for (int i = 1; i <= 3; i++) {
						if (Table.get(map, l + i, c) == '#') break;
						if (Table.get(map, l + i, c) == '@') m |= mpi.get(l + i, c);
					}
					for (int i = 1; i <= 3; i++) {
						if (Table.get(map, l - i, c) == '#') break;
						if (Table.get(map, l - i, c) == '@') m |= mpi.get(l - i, c);
					}
					for (int i = 1; i <= 3; i++) {
						if (Table.get(map, l, c + i) == '#') break;
						if (Table.get(map, l, c + i) == '@') m |= mpi.get(l, c + i);
					}
					for (int i = 1; i <= 3; i++) {
						if (Table.get(map, l, c - i) == '#') break;
						if (Table.get(map, l, c - i) == '@') m |= mpi.get(l, c - i);
					}
					if (m > 0) interesting.put(l, c, m);
				}
			}
		}
		
		Mpi kept = new Mpi();
		Si masks = new Si();
		Loop: for (var e: interesting.entrySet()) {
			Pos p = e.getKey();
			int mask = e.getValue();
			for (int m: interesting.values()) {
				if ((m | mask) == m) {
					if (m != mask || masks.contains(mask)) continue Loop;
				}
			}
			masks.add(mask);
			kept.put(p, mask);
		}
		
		S.e(kept.size());
		
		int target = (1 << id) - 1;
		
		Lp sol = new Lp();
		if (max < kept.size()) {
			for (var l: new Combinations<Map.Entry<Pos, Integer>>(kept.entrySet(), max)) {
				int m = 0;
				for (var e: l) m |= e.getValue();
				if (m == target) {
					for (var e: l) sol.add(e.getKey());
					break;
				}
			}
		} else {
			sol.addAll(kept.keySet());
		}
		if (mainKept == null) mainKept  = new Lp(kept.keySet());
		return sol;
	}
}
