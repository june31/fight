package aoc.done;

import java.util.HashSet;
import java.util.Set;

import tools.scanner.Scan;
import tools.tables.Table;
import tools.tuple.Pos;

public class Day_16_1 {
	private static int[][] map = Scan.readMap0();
	private static int L = map.length;
	private static int C = map[0].length;
	private static int[][] m = new int[L][C];
	private static Set<String> cache = new HashSet<>();
	
	public static void main(String[] args) {
		
		go(new Pos(0, 0), 0, 1);
		
		Table.debugMap(m);
		int s = 0;
		for (int i = 0; i < L; i++) {
			for (int j = 0; j < C; j++) {
				if (m[i][j] == '#') s++;
			}			
		}
		System.out.println(s);
	}

	private static void go(Pos p, int dl, int dc) {
		while (p.l >= 0 && p.c >= 0 && p.l < L && p.c < C) {
			if (!cache.add(p.toString() + "|" + dl + "|" + dc)) return;
			m[p.l][p.c] = '#';
			int k = map[p.l][p.c];
			if (k == '\\') {
				if (dc == 1) { dl = 1; dc = 0;}
				else if (dc == -1) { dl = -1; dc = 0;}
				else if (dl == 1) { dl = 0; dc = 1;}
				else if (dl == -1) { dl = 0; dc = -1;}
			}
			else if (k == '/') {
				if (dc == 1) { dl = -1; dc = 0;}
				else if (dc == -1) { dl = 1; dc = 0;}
				else if (dl == 1) { dl = 0; dc = -1;}
				else if (dl == -1) { dl = 0; dc = 1;}
			}
			else if (k == '|') {
				if (dc != 0) { 
					go(p, 1, 0);
					go(p, -1, 0);
					return;
				}
			}
			else if (k == '-') {
				if (dl != 0) {
					go(p, 0, 1);
					go(p, 0, -1);
					return;
				}
			}
			p = new Pos(p.l + dl, p.c + dc);
		}
	}
}
