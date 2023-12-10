package aoc.done;

import tools.bfs.BFS2D;
import tools.scanner.Scan;
import tools.tables.Table;
import tools.tuple.Pos;

public class Day_10_1 {

	private static BFS2D bfs;

	public static void main(String[] args) {
		var map = Scan.readMap0();
		bfs = new BFS2D(map);
		Pos s = Table.find(map, 'S');
		int t = bfs.diffuse(s, () -> move(), () -> false);
		System.out.println(t - 1);
	}
	
	private static boolean move() {
		return (bfs.l2 == bfs.l1 && bfs.c2 == bfs.c1 + 1 && bfs.v1 == 'S' && bfs.v2 == '-')
			|| (bfs.l2 == bfs.l1 && bfs.c2 == bfs.c1 + 1 && bfs.v1 == 'S' && bfs.v2 == '7')
			|| (bfs.l2 == bfs.l1 && bfs.c2 == bfs.c1 + 1 && bfs.v1 == 'S' && bfs.v2 == 'J')
				
			|| (bfs.l2 == bfs.l1 && bfs.c2 == bfs.c1 - 1 && bfs.v1 == 'S' && bfs.v2 == '-')
			|| (bfs.l2 == bfs.l1 && bfs.c2 == bfs.c1 - 1 && bfs.v1 == 'S' && bfs.v2 == 'L')
			|| (bfs.l2 == bfs.l1 && bfs.c2 == bfs.c1 - 1 && bfs.v1 == 'S' && bfs.v2 == 'F')
										
		    || (bfs.l2 == bfs.l1 + 1 && bfs.c2 == bfs.c1 && bfs.v1 == 'S' && bfs.v2 == '|')
			|| (bfs.l2 == bfs.l1 + 1 && bfs.c2 == bfs.c1 && bfs.v1 == 'S' && bfs.v2 == 'L')
			|| (bfs.l2 == bfs.l1 + 1 && bfs.c2 == bfs.c1 && bfs.v1 == 'S' && bfs.v2 == 'J')
				
			|| (bfs.l2 == bfs.l1 - 1 && bfs.c2 == bfs.c1 && bfs.v1 == 'S' && bfs.v2 == '|')
			|| (bfs.l2 == bfs.l1 - 1 && bfs.c2 == bfs.c1 && bfs.v1 == 'S' && bfs.v2 == '7')
			|| (bfs.l2 == bfs.l1 - 1 && bfs.c2 == bfs.c1 && bfs.v1 == 'S' && bfs.v2 == 'F')

			|| (bfs.l2 == bfs.l1 && bfs.c2 == bfs.c1 + 1 && bfs.v1 == '-' && bfs.v2 == '-')
			|| (bfs.l2 == bfs.l1 && bfs.c2 == bfs.c1 + 1 && bfs.v1 == 'L' && bfs.v2 == '-')
			|| (bfs.l2 == bfs.l1 && bfs.c2 == bfs.c1 + 1 && bfs.v1 == 'F' && bfs.v2 == '-')
			|| (bfs.l2 == bfs.l1 && bfs.c2 == bfs.c1 + 1 && bfs.v1 == '-' && bfs.v2 == '7')
			|| (bfs.l2 == bfs.l1 && bfs.c2 == bfs.c1 + 1 && bfs.v1 == 'L' && bfs.v2 == '7')
			|| (bfs.l2 == bfs.l1 && bfs.c2 == bfs.c1 + 1 && bfs.v1 == 'F' && bfs.v2 == '7')
			|| (bfs.l2 == bfs.l1 && bfs.c2 == bfs.c1 + 1 && bfs.v1 == '-' && bfs.v2 == 'J')
			|| (bfs.l2 == bfs.l1 && bfs.c2 == bfs.c1 + 1 && bfs.v1 == 'L' && bfs.v2 == 'J')
			|| (bfs.l2 == bfs.l1 && bfs.c2 == bfs.c1 + 1 && bfs.v1 == 'F' && bfs.v2 == 'J')
			
			|| (bfs.l2 == bfs.l1 && bfs.c2 == bfs.c1 - 1 && bfs.v1 == '-' && bfs.v2 == '-')
			|| (bfs.l2 == bfs.l1 && bfs.c2 == bfs.c1 - 1 && bfs.v1 == '7' && bfs.v2 == '-')
			|| (bfs.l2 == bfs.l1 && bfs.c2 == bfs.c1 - 1 && bfs.v1 == 'J' && bfs.v2 == '-')
			|| (bfs.l2 == bfs.l1 && bfs.c2 == bfs.c1 - 1 && bfs.v1 == '-' && bfs.v2 == 'L')
			|| (bfs.l2 == bfs.l1 && bfs.c2 == bfs.c1 - 1 && bfs.v1 == '7' && bfs.v2 == 'L')
			|| (bfs.l2 == bfs.l1 && bfs.c2 == bfs.c1 - 1 && bfs.v1 == 'J' && bfs.v2 == 'L')
			|| (bfs.l2 == bfs.l1 && bfs.c2 == bfs.c1 - 1 && bfs.v1 == '-' && bfs.v2 == 'F')
			|| (bfs.l2 == bfs.l1 && bfs.c2 == bfs.c1 - 1 && bfs.v1 == '7' && bfs.v2 == 'F')
			|| (bfs.l2 == bfs.l1 && bfs.c2 == bfs.c1 - 1 && bfs.v1 == 'J' && bfs.v2 == 'F')
									
		    || (bfs.l2 == bfs.l1 + 1 && bfs.c2 == bfs.c1 && bfs.v1 == '|' && bfs.v2 == '|')
			|| (bfs.l2 == bfs.l1 + 1 && bfs.c2 == bfs.c1 && bfs.v1 == '7' && bfs.v2 == '|')
			|| (bfs.l2 == bfs.l1 + 1 && bfs.c2 == bfs.c1 && bfs.v1 == 'F' && bfs.v2 == '|')
			|| (bfs.l2 == bfs.l1 + 1 && bfs.c2 == bfs.c1 && bfs.v1 == '|' && bfs.v2 == 'L')
			|| (bfs.l2 == bfs.l1 + 1 && bfs.c2 == bfs.c1 && bfs.v1 == '7' && bfs.v2 == 'L')
			|| (bfs.l2 == bfs.l1 + 1 && bfs.c2 == bfs.c1 && bfs.v1 == 'F' && bfs.v2 == 'L')
			|| (bfs.l2 == bfs.l1 + 1 && bfs.c2 == bfs.c1 && bfs.v1 == '|' && bfs.v2 == 'J')
			|| (bfs.l2 == bfs.l1 + 1 && bfs.c2 == bfs.c1 && bfs.v1 == '7' && bfs.v2 == 'J')
			|| (bfs.l2 == bfs.l1 + 1 && bfs.c2 == bfs.c1 && bfs.v1 == 'F' && bfs.v2 == 'J')
			
			|| (bfs.l2 == bfs.l1 - 1 && bfs.c2 == bfs.c1 && bfs.v1 == '|' && bfs.v2 == '|')
			|| (bfs.l2 == bfs.l1 - 1 && bfs.c2 == bfs.c1 && bfs.v1 == 'L' && bfs.v2 == '|')
			|| (bfs.l2 == bfs.l1 - 1 && bfs.c2 == bfs.c1 && bfs.v1 == 'J' && bfs.v2 == '|')
			|| (bfs.l2 == bfs.l1 - 1 && bfs.c2 == bfs.c1 && bfs.v1 == '|' && bfs.v2 == '7')
			|| (bfs.l2 == bfs.l1 - 1 && bfs.c2 == bfs.c1 && bfs.v1 == 'L' && bfs.v2 == '7')
			|| (bfs.l2 == bfs.l1 - 1 && bfs.c2 == bfs.c1 && bfs.v1 == 'J' && bfs.v2 == '7')
			|| (bfs.l2 == bfs.l1 - 1 && bfs.c2 == bfs.c1 && bfs.v1 == '|' && bfs.v2 == 'F')
			|| (bfs.l2 == bfs.l1 - 1 && bfs.c2 == bfs.c1 && bfs.v1 == 'L' && bfs.v2 == 'F')
			|| (bfs.l2 == bfs.l1 - 1 && bfs.c2 == bfs.c1 && bfs.v1 == 'J' && bfs.v2 == 'F');
	}
}
