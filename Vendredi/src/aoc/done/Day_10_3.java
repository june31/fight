package aoc.done;

import java.util.List;

import tools.scanner.Scan;
import tools.tables.Table;
import tools.tuple.Pos;

public class Day_10_3 {

	private static int[][] map;

	public static void main(String[] args) {
		map = Scan.readMap0();
		int L = map.length;
		int C = map[0].length;

		Pos p = Table.find(map, 'S');
		int line = p.l;
		if (List.of('-', '7', 'J').contains((char) map[p.l][p.c+1])) follow(p, 0, 1);
		else if (List.of('-', 'F', 'L').contains((char) map[p.l][p.c-1])) follow(p, 0, -1);
		else follow(p, 1, 0);

		int col = 0;
		while (map[line][col] > 0) col++;
		int up = 0;
		int down = 0;
		for (int l = 0; l < L; l++)
			for (int c = 0; c < C; c++)
				if (map[l][c] > 0)
					for (int i = c+1; i < C; i++) {
						if (map[l][i] == -1) { down++; break; }
						if (map[l][i] == -2) { up++; break; }
					}
		System.out.println(map[line][col] == -1 ? up : down);
	}

	private static void follow(Pos p, int dl, int dc) {
		Pos s = p;
		do {
			int ol = dl;
			if (map[p.l][p.c] > 0) map[p.l][p.c] = -3;
			p = new Pos(p.l+dl, p.c+dc);
			int x = map[p.l][p.c];
			if (x == 'J') if (dc == 1) { dc = 0; dl = -1; } else { dc = -1; dl = 0; }
			if (x == '7') if (dc == 1) { dc = 0; dl = 1; } else { dc = -1; dl = 0; }
			if (x == 'F') if (dc == -1) { dc = 0; dl = 1; } else { dc = 1; dl = 0; }
			if (x == 'L') if (dc == -1) { dc = 0; dl = -1; } else { dc = 1; dl = 0; }
			if (ol == 1 || dl == 1) map[p.l][p.c] = -1;
			if (ol == -1 || dl == -1) map[p.l][p.c] = -2;
		} while (!p.equals(s));
	}
}
