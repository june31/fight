package aoc2022.day08;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TreetopTreeHouse_1 {
	public static void main(String[] args) throws IOException {
		int n = 99;
		int[][] t = new int[n][n];
		String line;
		int ii = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader("input2.txt"))) {
			while ((line = reader.readLine()) != null) {
				char[] cs = line.toCharArray();
				for (int j = 0; j < n; j++) t[ii][j] = cs[j] - 48;
				ii++;
			}
			int score = 0;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					int max = -1;
					for (int k = 0; k < i; k++) {
						if (t[k][j] > max) max = t[k][j]; 
					}
					if (max < t[i][j]) continue;

					max = -1;
					for (int k = i+1; k < n; k++) {
						if (t[k][j] > max) max = t[k][j]; 
					}
					if (max < t[i][j]) continue;

					max = -1;
					for (int k = 0; k < j; k++) {
						if (t[i][k] > max) max = t[i][k]; 
					}
					if (max < t[i][j]) continue;

					max = -1;
					for (int k = j+1; k < n; k++) {
						if (t[i][k] > max) max = t[i][k]; 
					}
					if (max < t[i][j]) continue;

					score++;
				}
			}
			System.out.println(n*n - score);
		}
	}
}
