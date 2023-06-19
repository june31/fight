package current;

import tools.scanner.Scan;
import tools.tables.Table;
import tools.tuple.Pos;
import tools.voronoi.Voronoi2D;

public class SavaneVoronoi {
	static { Scan.open("src/current/SavaneBFS.txt"); }
	private static final int LIONS = 8;

	public static void main(String[] args) {
		int[][] map = Scan.readMap0();
		Pos[] start = new Pos[LIONS];
		Table.forEach(map, (l, c, v) -> {
			if (v >= '1' && v <= '0' + LIONS) start[v - '1'] = new Pos(l, c);
		});

		Voronoi2D vor = new Voronoi2D(map);
		
		vor.diffuse(start, () -> vor.v2 == '.', false);
		for (int i = 0; i < LIONS; i++) System.out.println("Lion " + (i+1) + ": " + vor.areas[i]);
		System.out.println();
		vor.diffuse(start, () -> vor.v2 == '.', true);
		for (int i = 0; i < LIONS; i++) System.out.println("Lion " + (i+1) + ": " + vor.areas[i]);
	}
}
