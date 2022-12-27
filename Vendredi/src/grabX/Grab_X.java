package grabX;

import java.util.ArrayList;
import java.util.List;

import tools.bfs.BFS2D;
import tools.scanner.Scan;
import tools.select.MixAll;
import tools.tables.Table;
import tools.tuple.Pos;

public class Grab_X {

	static { Scan.open("src/grabX/House.txt"); }

	private static Pos o;
	public static void main(String[] args) {
		List<Pos> xs = new ArrayList<>();
		int[][] map = Scan.readRawMap();
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] == 'X') xs.add(new Pos(i, j));
				else if (map[i][j] == 'O') o = new Pos(i, j);
			}
		}
		
		BFS2D bfs = new BFS2D(map);
		int minLength = Integer.MAX_VALUE;
		List<Pos> bestTrip = null;
		
		for (var l : new MixAll<>(xs, true)) {
			Pos p = o;
			int tripLength = 0;
			List<Pos> trip = new ArrayList<>();
			for (Pos x : l) {
				tripLength += bfs.diffuse(p, '#', () -> bfs.l2 == x.l && bfs.c2 == x.c);
				p = x;
				trip.addAll(bfs.backTrack(x));
			}
			tripLength += bfs.diffuse(p, '#', () -> bfs.l2 == o.l && bfs.c2 == o.c);
			trip.addAll(bfs.backTrack(o));

			if (tripLength < minLength) {
				minLength = tripLength;
				bestTrip = trip;
			}
		}
		System.out.println(minLength + "\n" + bestTrip);
		Table.showMap(map);
		for (Pos p : bestTrip) map[p.l][p.c] = '+';
		Table.showMap(map);
	}
}
