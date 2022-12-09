package aoc2022.day09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day09_1 {
	public static void main(String[] args) throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader("input2.txt"))) {
			int score = 0;
			boolean[][] traces = new boolean[2000][2000];
			int xh = 1000;
			int yh = 1000;
			int xt = 1000;
			int yt = 1000;
			traces[xt][yt] = true;
			String line;
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split(" ");
				char dir = tokens[0].charAt(0);
				int len = Integer.parseInt(tokens[1]);
				for (int i = 0; i < len; i++) {
					int oldXH = xh;
					int oldYH = yh;
					if (dir == 'U') yh--;
					else if (dir == 'D') yh++;
					else if (dir == 'L') xh--;
					else xh++;
					if (Math.abs(xh - xt) == 2 || Math.abs(yh - yt) == 2) {
						xt = oldXH;
						yt = oldYH;
						traces[xt][yt] = true;
					}
				}
			}

			for (int i = 0; i < 2000; i++) for (int j = 0; j < 2000; j++) if (traces[i][j]) score++; 
			System.out.println(score);
		}
	}
}
