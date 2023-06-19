package current;

import java.util.ArrayList;
import java.util.List;

import tools.bfs.BFS2D;
import tools.function.BiIntConsumer;
import tools.scanner.Scan;
import tools.tables.Table;
import tools.tuple.Pos;

public class SavaneBFS {
	static { Scan.open("src/current/SavaneBFS.txt"); }
	private static final int LIONS = 8;
	private static int[][][] dists = new int[LIONS][][];
	private static double[] area;

	public static void main(String[] args) {
		int[][] map = Scan.readMap0();
		Pos[] start = new Pos[LIONS];
		Table.forEach(map, (l, c, v) -> {
			if (v >= '1' && v <= '0' + LIONS) start[v - '1'] = new Pos(l, c);
		});

		BFS2D bfs = new BFS2D(map);
		for (int i = 0; i < LIONS; i++) {
			dists[i] = new int[map.length][map[0].length];
			int fi = i;
			bfs.diffuse(start[i],
					() -> {
						if (bfs.v2 == '.') {
							dists[fi][bfs.l2][bfs.c2] = bfs.turn;
							return true;
						}
						return false;
					}, () -> false);
		}

		// No shared area
		calc(map, (lig, col) -> {
			int min = Integer.MAX_VALUE;
			int lion = -1;
			boolean same = false;
			for (int x = 0; x < LIONS; x++) {
				int v = dists[x][lig][col];
				if (min == v) same = true;
				if (v != 0 && min > v) {
					min = v;
					lion = x;
					same = false;
				}
			}
			if (!same && lion != -1) area[lion]++;
		});

		// Lion 1 has priority
		calc(map, (lig, col) -> {
			int min = Integer.MAX_VALUE;
			int lion = -1;
			for (int x = 0; x < LIONS; x++) {
				int v = dists[x][lig][col];
				if (v != 0 && min > v) {
					min = v;
					lion = x;
				}
			}
			if (lion != -1) area[lion]++;
		});

		// Shared area
		calc(map, (lig, col) -> {
			int min = Integer.MAX_VALUE;
			List<Integer> lions = new ArrayList<>();
			for (int x = 0; x < LIONS; x++) {
				int v = dists[x][lig][col];
				if (min == v) lions.add(x);
				if (v != 0 && min > v) {
					min = v;
					lions.clear();
					lions.add(x);
				}
			}
			for (int lion : lions) area[lion] += 1d / lions.size();
		});
	}

	private static void calc(int[][] map, BiIntConsumer task) {
		area = new double[LIONS];
		Table.forEach(map, task);
		for (int i = 0; i < LIONS; i++)
			System.out.println("Lion " + (i+1) + ": " + (area[i]+1));
		System.out.println();
	}
}
