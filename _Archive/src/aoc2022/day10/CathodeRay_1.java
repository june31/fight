package aoc2022.day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CathodeRay_1 {
	public static void main(String[] args) throws IOException {
		int[] x = new int[10000];
		x[0] = 1;
		int n = 1;
		try (BufferedReader reader = new BufferedReader(new FileReader("input2.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.equals("noop")) {
					x[n] = x[n-1];
					n++;
				} else {
					int v = Integer.parseInt(line.substring(5));
					x[n] = x[n-1];
					x[n+1] = x[n-1] + v;
					n += 2;
				}
			}
			int sum = 0;
			for (int i = 19; i < n; i += 40) {
				sum += (i+1) * x[i];
			}
			System.out.println(sum);
		}
	}
}
