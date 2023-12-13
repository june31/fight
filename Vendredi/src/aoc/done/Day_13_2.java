package aoc.done;

import tools.scanner.Scan;
import tools.tables.Table;

public class Day_13_2 {

	public static void main(String[] args) {
		long s = 0;
		try {
			while (true) {
				int[][] map = Scan.readMap0();
				int L = map.length;
				int C = map[0].length;
				Table.printMap(map);
				int ss = -1;
				loop: for (int i = 1; i < C; i++) {
					for (int j = 0; j < Math.min(i, C - i); j++) {
						for (int k = 0; k < L; k++) {
							if (map[k][i-1-j] != map[k][i+j]) continue loop;
						}
					}
					System.out.println(i);
					ss = i;
				}
				loop: for (int i = 1; i < L; i++) {
					for (int j = 0; j < Math.min(i, L - i); j++) {
						for (int k = 0; k < C; k++) {
							if (map[i-1-j][k] != map[i+j][k]) continue loop;
						}
					}
					System.out.println(i);
					ss = i * 100;
				}
				System.out.println();
				main: for (int y = 0; y < L; y++) {
					for (int x = 0; x < C; x++) {
						int[][] m = Table.copy(map);
						m[y][x] = m[y][x] == '.' ? '#' : '.';
						loop: for (int i = 1; i < C; i++) {
							for (int j = 0; j < Math.min(i, C - i); j++) {
								for (int k = 0; k < L; k++) {
									if (m[k][i-1-j] != m[k][i+j]) continue loop;
								}
							}
							if (ss == i) continue;
							s += i;
							break main;
						}
						loop: for (int i = 1; i < L; i++) {
							for (int j = 0; j < Math.min(i, L - i); j++) {
								for (int k = 0; k < C; k++) {
									if (m[i-1-j][k] != m[i+j][k]) continue loop;
								}
							}
							if (ss == i * 100) continue;
							s += i * 100;
							break main;
						}
					}
				}
			}
		} catch (NullPointerException e) {}
		System.out.println(s);
	}
}
