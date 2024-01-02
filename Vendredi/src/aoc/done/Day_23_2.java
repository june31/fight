package aoc.done;

import java.util.LinkedHashMap;
import java.util.Map;

import tools.bfs.BFS2D;
import tools.chrono.Chrono;
import tools.collections.pos.Lp;
import tools.scanner.Scan;
import tools.structures.graph.node.Node;
import tools.tuple.Pos;

public class Day_23_2 {

	private static int[][] map = Scan.readMap0();
	private static int L = map.length;
	private static int C = map[0].length;
	private static BFS2D bfs;
	private static Lp lp = new Lp();
	private static int[][] dists;
	private static Pos start;
	private static Node[] nodes;
	private static Map<Pos, Integer> posToId = new LinkedHashMap<>();
	private static int max = 0;
	
	public static void main(String[] args) throws InterruptedException {
		Chrono.start();
		lp.add(new Pos(0, 1));
		posToId.put(new Pos(0, 1), 0);
		int id = 1;
		for (int l = 1; l < L - 1; l++) {
			for (int c = 1; c < C - 1; c++) {
				if (map[l][c] == '#') continue;
				int d = 0;
				if (map[l - 1][c] != '#') d++;
				if (map[l + 1][c] != '#') d++;
				if (map[l][c - 1] != '#') d++;
				if (map[l][c + 1] != '#') d++;
				if (d > 2) {
					Pos p = new Pos(l, c);
					lp.add(p);
					posToId.put(p, id++);
				}
			}
		}
		lp.add(new Pos(L-1, C-2));
		posToId.put(new Pos(L-1, C-2), id++);
		dists = new int[id][id];
		System.out.println(lp.size() + " nodes, including start and end");
		bfs = new BFS2D(map);
		for (Pos p: lp) {
			start = p;
			bfs.diffuse(p, () -> cond(), () -> false);
		}
		
		nodes = new Node[lp.size()];
		for (int i = 0; i < nodes.length; i++) nodes[i] = new Node();
		for (int i = 0; i < nodes.length; i++) {
			for (int j = 0; j < nodes.length; j++) {
				if (dists[i][j] != 0) nodes[i].links.add(nodes[j]);
			}			
		}
		
		rec(nodes[0], 0, 0);
		System.out.println(max);
		Chrono.stop();
	}
	
	private static void rec(Node node, int score, long used) {
		long bit = 1l<<node.x;
		if ((used & bit) != 0) return;
		for (Node n : node.links) {
			int s = score + dists[node.x][n.x];
			if (n.x == nodes.length - 1) {
				if (s > max) max = s; 
			}
			rec(n, s, used | bit);
		}
	}

	private static boolean cond() {
		if (bfs.v2 < '.') return false;
		Pos p = new Pos(bfs.l2, bfs.c2);
		if (lp.contains(p)) {
			dists[posToId.get(start)][posToId.get(p)] = bfs.turn;
			return false;
		}
		return true;
	}
}
