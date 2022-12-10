package aoc2022.day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CathodeRay_2 {
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
			for (int i = 0; i < n; i += 40) {
				System.out.println();
				for (int j = 0; j < 40; j++) {
					char c = Math.abs(x[i+j] - j) <= 1 ? '#' : '.';
					System.out.print(c);
				}
			}
		}
	}
}
