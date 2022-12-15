package aoc2022.day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day14_2 {
	public static void main(String[] args) throws IOException {
		int N = 1000;
		int[][] map = new int[1000][1000];
		try (BufferedReader reader = new BufferedReader(new FileReader("input2.txt"))) {
			String line;
			int maxY = -1;
			while ((line = reader.readLine()) != null) {
				String[] t1 = line.split(" -> ");
				int n = t1.length;
				int[] xs = new int[n];
				int[] ys = new int[n];
				int id = 0;
				for (String t : t1) {
					String[] t2 = t.split(",");
					xs[id] = Integer.parseInt(t2[0]);
					ys[id] = Integer.parseInt(t2[1]);
					if (ys[id] > maxY) maxY = ys[id];
					id++;
				}
				int oldX = xs[0];
				int oldY = ys[0];
				for (int i = 1; i < n; i++) {
					int x = xs[i];
					int y = ys[i];
					if (x < oldX) {
						for (int j = x; j <= oldX; j++) {
							map[j][y] = 1;
						}
					} else if (x > oldX) {
						for (int j = x; j >= oldX; j--) {
							map[j][y] = 1;
						}
					} else if (y < oldY) {
						for (int j = y; j <= oldY; j++) {
							map[x][j] = 1;
						}
					} else if (y > oldY) {
						for (int j = y; j >= oldY; j--) {
							map[x][j] = 1;
						}
					} else System.out.println("oups");
					oldX = x;
					oldY = y;
				}
			}

			for (int i = 0; i < 1000; i++) {
				map[i][maxY + 2] = 3;
			}

			int sand = 0;
			Loop: while (true) {
				int x = 500;
				int y = 0;
				while (true) {
					if (map[x][y+1] == 0) {
						y++;
					} else if (map[x-1][y+1] == 0) {
						x--;
						y++;
					} else if (map[x+1][y+1] == 0) {
						x++;
						y++;
					} else {
						map[x][y] = 2;
						sand++;
						if (y == 0) break Loop;
						break;
					}
				}
			}

			System.out.println(sand);

			for (int y = 0; y <= maxY+3; y++) {
				for (int x = 200; x <= 800; x++) {
					int h = map[x][y];
					if (h == 0) System.out.print('.');
					if (h == 1) System.out.print('#');
					if (h == 2) System.out.print('o');
					if (h == 3) System.out.print('%');
				}
				System.out.println();
			}
		}
	}
}
