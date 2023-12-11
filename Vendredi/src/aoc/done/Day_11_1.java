package aoc.done;

import tools.math.Dist;
import tools.scanner.Scan;
import tools.tables.Table;

public class Day_11_1 {
	
	public static void main(String[] args) {
		int[][] map = Scan.readMap0();
		int L = map.length;
		int C = map[0].length;
		boolean[] lbs = new boolean[L];
		boolean[] cbs = new boolean[C];
		Table.forEach(map, (l, c, v) -> {
			if (v != '.') {
				lbs[l] = true;
				cbs[c] = true;
			}
		});
		int ls = 0;
		for (int i = 0; i < L; i++) if (!lbs[i]) ls++;
		int cs = 0;
		for (int i = 0; i < C; i++) if (!cbs[i]) cs++;
		int[][] m = new int[L + ls][C + cs];
		float l = 0;
		for (int i = 0; i < L + ls; i++) {
			float c = 0;
			for (int j = 0; j < C + cs; j++) {
				int x;
				if (!lbs[(int) l] || !cbs[(int) c]) x = '.';
				else x = map[(int) l][(int) c];
				m[i][j] = x;
				if (!cbs[(int) c]) c += 0.5; else c += 1; 
			}
			if (!lbs[(int) l]) l += 0.5; else l += 1; 
		}
		
		long s = 0;
		var ps = Table.findAll(m, '#');
		for (int i = 0; i < ps.length; i++) {
			for (int j = i + 1; j < ps.length; j++) {
				s += Dist.manh(ps[i], ps[j]);
			}
		}
		System.out.println(s);
	}
}
		
