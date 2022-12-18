package aoc2022.day18;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import tools.dfs.BFS3D;

public class LavaDroplets_2 {
	
	final static int S = 32;
	static int[][][] tab = new int[S][S][S];
	static int walls = 0;
	
	public static void main(String[] args) throws IOException {
		
		try (BufferedReader reader = new BufferedReader(new FileReader("input2.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] toks = line.split(",");
				tab[Integer.parseInt(toks[0]) + 1][Integer.parseInt(toks[1]) + 1][Integer.parseInt(toks[2]) + 1] = 1;
			}
		}
		
		BFS3D bfs = new BFS3D(tab);
		bfs.diffuse(0, 0, 0, (x1, y1, z1, x2, y2, z2) -> {
			int a = tab[x2][y2][z2];
			walls += a;
			return a == 0;
		});
		System.out.println("Volume: " + (S*S*S - bfs.scanned));
		System.out.println("Surface: " + walls);
	}
}
