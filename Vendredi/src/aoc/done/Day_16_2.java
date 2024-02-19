package aoc.done;

import java.util.HashSet;
import java.util.Set;

import tools.scanner.Scan;
import tools.tables.Table;
import tools.tuple.Pos;

public class Day_16_2 {
	private static int[][] map = Scan.readMap0();
	private static int L = map.length;
	private static int C = map[0].length;
	private static int[][] m;
	private static Set<String> cache;
	
	public static void main(String[] args) {
		int[][] mm = null;
		int max = 0;
		for (int i = 0; i < L; i++) {
			int s = score(new Pos(i, 0), 0, 1);
			if (s > max) { max = s; mm = m; }
			s = score(new Pos(i, C-1), 0, -1);
			if (s > max) { max = s; mm = m; }
		}
		for (int i = 0; i < C; i++) {
			int s = score(new Pos(0, i), 1, 0);
			if (s > max) { max = s; mm = m; }
			s = score(new Pos(L-1, i), -1, 0);
			if (s > max) { max = s; mm = m; }
		}
		
		Table.debug(mm);
		System.out.println(max);
	}
	
	private static int score(Pos p, int dl, int dc) {
		m = new int[L][C];
		cache = new HashSet<>();
		go(p, dl, dc);
		int s = 0;
		for (int i = 0; i < L; i++) {
			for (int j = 0; j < C; j++) {
				if (m[i][j] == '#') s++;
			}			
		}
		return s;
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
