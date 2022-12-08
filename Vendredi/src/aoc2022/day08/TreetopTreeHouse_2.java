package aoc2022.day08;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TreetopTreeHouse_2 {
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
			int max = 0;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					int z = t[i][j];
					int a = 0;
					for (int k = i - 1; k >= 0; k--) {
						a++;
						if (t[k][j] >= z) break; 
					}

					int b = 0;
					for (int k = i+1; k < n; k++) {
						b++;
						if (t[k][j] >= z) break; 
					}

					int c = 0;
					for (int k = j-1; k >= 0; k--) {
						c++;
						if (t[i][k] >= z) break; 
					}

					int d = 0;
					for (int k = j+1; k < n; k++) {
						d++;
						if (t[i][k] >= z) break; 
					}
					
					int m = a*b*c*d;
					if (max < m) max = m;
				}
			}
			System.out.println(max);
		}
	}
}
