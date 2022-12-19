package aoc2022.day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tables.Table;
import tools.bfs.BFS2D;

public class HillClimbing_1_2 {
	public static void main(String[] args) throws IOException {
		List<int[]> data = new ArrayList<>();  
		try (BufferedReader reader = new BufferedReader(new FileReader("input2.txt"))) {
			String line;
			int lS = 0;
			int cS = 0;
			int lE = 0;
			int cE = 0;
			int currentL = 0;
			while ((line = reader.readLine()) != null) {
				byte[] bs = line.getBytes();
				int[] l = new int[bs.length];
				for (int i = 0; i < bs.length; i++) {
					if (bs[i] == 'S') {
						l[i] = 'a' - 1;
						cS = i;
						lS = currentL;
					}
					else if (bs[i] == 'E') {
						l[i] = 'z' + 1;
						cE = i;
						lE = currentL;
					}
					else l[i] = bs[i];
				}
				data.add(l);
				currentL++;
			}
			int[][] t = Table.convert(data);
			BFS2D bfs = new BFS2D(t);
			System.out.println(bfs.diffuse(lS, cS, (l1, c1, l2, c2) -> t[l2][c2] - t[l1][c1] <= 1, (l, c) -> t[l][c] == 'z' + 1));
			System.out.println(bfs.found);
			System.out.println(bfs.diffuse(lE, cE, (l1, c1, l2, c2) -> t[l1][c1] - t[l2][c2] <= 1, (l, c) -> t[l][c] == 'a'));
			System.out.println(bfs.found);
		}
	}
}