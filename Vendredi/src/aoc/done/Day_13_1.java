package aoc.done;

import tools.scanner.Scan;
import tools.tables.Table;

public class Day_13_1 {

	public static void main(String[] args) {
		long s = 0;
		try {
			while (true) {
				int[][] map = Scan.readMap0();
				int L = map.length;
				int C = map[0].length;
				Table.printMap(map);
				loop: for (int i = 1; i < C; i++) {
					for (int j = 0; j < Math.min(i, C - i); j++) {
						for (int k = 0; k < L; k++) {
							if (map[k][i-1-j] != map[k][i+j]) continue loop;
						}
					}
					System.out.println(i);
					s += i;
				}
				loop: for (int i = 1; i < L; i++) {
					for (int j = 0; j < Math.min(i, L - i); j++) {
						for (int k = 0; k < C; k++) {
							if (map[i-1-j][k] != map[i+j][k]) continue loop;
						}
					}
					System.out.println(i);
					s += i * 100;
				}
			}
		} catch (NullPointerException e) {}
		System.out.println(s);
	}
}
