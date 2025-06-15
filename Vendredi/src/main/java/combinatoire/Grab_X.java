package combinatoire;

import java.util.ArrayList;
import java.util.List;

import tools.bfs.BFS2D;
import tools.enumeration.permutations.MixPermutations;
import tools.scanner.Scan;
import tools.tables.Table;
import tools.tuple.Pos;

public class Grab_X {

	public static void main(String[] args) {
		List<Pos> xs = new ArrayList<>();
		Pos o = null;
		int[][] map = Scan.readMapRaw();
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] == 'X') xs.add(new Pos(i, j));
				else if (map[i][j] == 'O') o = new Pos(i, j);
			}
		}
		
		BFS2D bfs = new BFS2D(map).wall('#');
		int minLength = Integer.MAX_VALUE;
		List<Pos> bestTrip = null;
			
		for (var l : new MixPermutations<>(xs)) {
			Pos p = o;
			int tripLength = 0;
			List<Pos> trip = new ArrayList<>();
			for (Pos x : l) {
				bfs.end(x).diffuse(p);
				tripLength += bfs.turn;
				p = x;
				trip.addAll(bfs.shortestPath());
			}
			bfs.end(o).diffuse(p);
			tripLength += bfs.turn;
			trip.addAll(bfs.shortestPath());

			if (tripLength < minLength) {
				minLength = tripLength;
				bestTrip = trip;
			}
		}
		System.out.println(minLength + "\n" + bestTrip);
		Table.printMap(map);
		System.out.println();
		for (Pos p : bestTrip) map[p.l][p.c] = '+';
		Table.printMap(map);
	}
}
