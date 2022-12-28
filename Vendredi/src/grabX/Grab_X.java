package grabX;

import java.util.ArrayList;
import java.util.List;

import tools.bfs.BFS2D;
import tools.enumeration.SelectMode;
import tools.enumeration.permutations.MixPermutation;
import tools.scanner.Scan;
import tools.tables.Table;
import tools.tuple.Pos;

public class Grab_X {

	static { Scan.open("src/grabX/House.txt"); }

	public static void main(String[] args) {
		List<Pos> xs = new ArrayList<>();
		Pos o = null;
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
			
		for (var l : new MixPermutation<>(xs, SelectMode.SYMMETRICAL)) {
			Pos p = o;
			int tripLength = 0;
			List<Pos> trip = new ArrayList<>();
			for (Pos x : l) {
				tripLength += bfs.diffuse(p, '#', x);
				p = x;
				trip.addAll(bfs.backTrack(x));
			}
			tripLength += bfs.diffuse(p, '#', o);
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
