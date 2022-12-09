package aoc2022.day09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day09_2 {
	public static void main(String[] args) throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader("input3.txt"))) {
			int score = 0;
			boolean[][] traces = new boolean[2000][2000];
			
			int[] x = new int[10];
			int[] y = new int[10];
			int[] oldX = new int[10];
			int[] oldY = new int[10];
			for (int i = 0; i < 10; i++) {
				x[i] = 1000;
				y[i] = 1000;
			}
			traces[x[9]][y[9]] = true;
			String line;
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split(" ");
				char dir = tokens[0].charAt(0);
				int len = Integer.parseInt(tokens[1]);
				for (int i = 0; i < len; i++) {
					for (int j = 0; j < 10; j++) {
						oldX[j] = x[j];
						oldY[j] = y[j];
						if (j == 0) {
							if (dir == 'U') y[0]--;
							else if (dir == 'D') y[0]++;
							else if (dir == 'L') x[0]--;
							else x[0]++;
						} else {
							if (Math.abs(x[j] - x[j - 1]) + Math.abs(y[j] - y[j - 1]) >= 3) {
								if (x[j - 1] - x[j] > 0 && y[j - 1] - y[j] > 0) {
									x[j]++;
									y[j]++;
								}
								else if (x[j - 1] - x[j] < 0 && y[j - 1] - y[j] > 0) {
									x[j]--;
									y[j]++;
								}
								else if (x[j - 1] - x[j] > 0 && y[j - 1] - y[j] < 0) {
									x[j]++;
									y[j]--;
								}
								else if (x[j - 1] - x[j] < 0 && y[j - 1] - y[j] < 0) {
									x[j]--;
									y[j]--;
								}
							}
							else if (Math.abs(x[j] - x[j - 1]) == 2 || Math.abs(y[j] - y[j - 1]) == 2) {
								x[j] = oldX[j - 1];
								y[j] = oldY[j - 1];
							}
						}
					}
					traces[x[9]][y[9]] = true;
				}
			}

			for (int i = 0; i < 2000; i++) for (int j = 0; j < 2000; j++) if (traces[i][j]) score++; 
			System.out.println(score);
		}
	}
}
