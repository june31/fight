package aoc;

import tools.bfs.BFS2D;
import tools.scanner.Scan;
import tools.tables.Table;
import tools.tuple.Pos;

public class Day_18_1 {
	
	private static BFS2D bfs;
	public static void main(String[] args) {
		String[] in = Scan.readRawStrings();
		int[][] map = new int[1000][1000];
		int L = map.length;
		int C = map[0].length;
		
		int l = 500;
		int c = 500;
		map[l][c] = '#';
		
		for (String a: in) {
			String[] t = a.split(" ");
			for (int i = 0; i < Integer.parseInt(t[1]); i++) {
				if (t[0].equals("D")) l++;
				if (t[0].equals("U")) l--;
				if (t[0].equals("R")) c++;
				if (t[0].equals("L")) c--;
				map[l][c] = '#';
			}
		}

		bfs = new BFS2D(map);
		bfs.diffuse(new Pos(501, 501), '#', () -> { map[bfs.l2][bfs.c2] = '#'; return false; });
		
		int s = Table.findAll(map, '#').length;
		
		System.out.println(s);
	}
}
