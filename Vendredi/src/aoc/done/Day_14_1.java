package aoc.done;

import tools.scanner.Scan;
import tools.tables.Table;

public class Day_14_1 {
	
	public static void main(String[] args) {
		int[][] map = Scan.readMap0();
		int L = map.length;
		int C = map[0].length;
		long s = 0;
		for (int l = 0; l < L; l++) {
			for (int c = 0; c < C; c++) {
				if (map[l][c] == 'O') {
					int x = l - 1;
					while (x >= 0 && map[x][c] == '.') {
						map[x][c] = 'O';
						map[x+1][c] = '.';
						x--;
					}
				}
			}
		}
		Table.debug(map);
		int id = 0;
		for (int l = L-1; l >= 0; l--) {
			id++;
			for (int c = 0; c < C; c++) if (map[l][c] == 'O') s += id;
		}
		
		System.out.println(s);
	}
}
