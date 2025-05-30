package tooltests.bfs;

import tools.bfs.BFS3D;
import tools.scanner.Scan;

// https://adventofcode.com/2022/day/18
// (Part Two)
public class AoC_BFS3D_LavaDroplets {
	
	final static int S = 32;
	static int[][][] tab = new int[S][S][S];
	static int walls = 0;
	
	public static void main(String[] args) {
		
		for (String line : Scan.readRaw()) {
			String[] toks = line.split(",");
			tab[Integer.parseInt(toks[0]) + 1][Integer.parseInt(toks[1]) + 1][Integer.parseInt(toks[2]) + 1] = 1;
		}
		
		BFS3D bfs = new BFS3D(tab);
		bfs.diffuse(0, 0, 0, () -> {
			int a = tab[bfs.x2][bfs.y2][bfs.z2];
			walls += a;
			return a == 0;
		}, () -> false);
		System.out.println("Volume: " + (S*S*S - bfs.scanned));
		System.out.println("Surface: " + walls);
	}
}
