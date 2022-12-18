package aoc2022.day18;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LavaDroplets_1 {
	
	static int[][][][] tab = new int[3][32][32][32];
	static int sum = 0;
	
	public static void main(String[] args) throws IOException {
		
		try (BufferedReader reader = new BufferedReader(new FileReader("input2.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] toks = line.split(",");
				int x = Integer.parseInt(toks[0]);
				int y = Integer.parseInt(toks[1]);
				int z = Integer.parseInt(toks[2]);
				check(0, x, y, z);
				check(0, x, y, z+1);
				check(1, y, z, x);
				check(1, y, z, x+1);
				check(2, z, x, y);
				check(2, z, x, y+1);
			}
			System.out.println(sum);
		}
	}

	private static void check(int id, int x, int y, int z) {
		int v = tab[id][x][y][z];
		tab[id][x][y][z] = 1;
		if (v == 0) sum++; else sum--;
	}
}
