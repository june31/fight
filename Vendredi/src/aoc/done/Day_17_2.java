package aoc.done;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tools.bfs.BFSWGraph;
import tools.chrono.Chrono;
import tools.reference.DijkstraWGraph;
import tools.scanner.Scan;
import tools.structures.graph.WGraph;
import tools.structures.graph.node.WNode;
import tools.tables.Table;

public class Day_17_2 {
	private static final int M = 10;
	private static final int Up = 0;
	private static final int Down = M;
	private static final int Left = 2*M;
	private static final int Right = 3*M;
	private static final int[][] map = Scan.readMap0();
	private static final int L = map.length;
	private static final int C = map[0].length;
	private static final WNode[][][] nodes = new WNode[L][C][4*M];
	private static final Map<WNode, Integer> coordsL = new HashMap<>();
	private static final Map<WNode, Integer> coordsC = new HashMap<>();
	
	
	public static void main(String[] args) {

		var nodeList = new ArrayList<WNode>();
		for (int l = 0; l < L; l++) {
			for (int c = 0; c < C; c++) {
				for (int i = 0; i < 4*M; i++) {
					nodes[l][c][i] = new WNode(map[l][c] - '0');
					coordsL.put(nodes[l][c][i], l);
					coordsC.put(nodes[l][c][i], c);
					nodeList.add(nodes[l][c][i]);
				}
			}
		}

		WGraph g = new WGraph(nodeList);
		for (int l = 0; l < L; l++) {
			for (int c = 0; c < C; c++) {
				for (int i = 0; i < M-1; i++) {
					if (l > 0) g.singleLink(nodes[l][c][Up+i], nodes[l-1][c][Up+i+1]);
					if (l < L-1) g.singleLink(nodes[l][c][Down+i], nodes[l+1][c][Down+i+1]);
					if (c > 0) g.singleLink(nodes[l][c][Left+i], nodes[l][c-1][Left+i+1]);
					if (c < C-1) g.singleLink(nodes[l][c][Right+i], nodes[l][c+1][Right+i+1]);
				}
				for (int i = 3; i < M; i++) {
					if (l > 0) g.singleLink(nodes[l][c][Left+i], nodes[l-1][c][Up]);
					if (l > 0) g.singleLink(nodes[l][c][Right+i], nodes[l-1][c][Up]);
					if (l < L-1) g.singleLink(nodes[l][c][Left+i], nodes[l+1][c][Down]);
					if (l < L-1) g.singleLink(nodes[l][c][Right+i], nodes[l+1][c][Down]);
					if (c > 0) g.singleLink(nodes[l][c][Up+i], nodes[l][c-1][Left]);
					if (c > 0) g.singleLink(nodes[l][c][Down+i], nodes[l][c-1][Left]);
					if (c < C-1) g.singleLink(nodes[l][c][Up+i], nodes[l][c+1][Right]);
					if (c < C-1) g.singleLink(nodes[l][c][Down+i], nodes[l][c+1][Right]);
				}
			}
		}
		
		WNode start = new WNode();
		g.addNode(start);
		g.singleLink(start, nodes[0][1][Right]);
		g.singleLink(start, nodes[1][0][Down]);
		
		WNode end = new WNode();
		g.addNode(end);
		for (int i = 3; i < M; i++) {
			g.singleLink(nodes[L-1][C-1][i], end);
			g.singleLink(nodes[L-1][C-1][i+M], end);
			g.singleLink(nodes[L-1][C-1][i+2*M], end);
			g.singleLink(nodes[L-1][C-1][i+3*M], end);
		}
		
		Chrono.start();
		BFSWGraph bfs = new BFSWGraph(g);
		int weight = bfs.diffuse(start, end);
		Chrono.stop();
		for (var n: bfs.shortestPath()) {
			System.out.print(coordsL.get(n) + ":" + coordsC.get(n) + " ");
			if (coordsL.get(n) != null) map[coordsL.get(n)][coordsC.get(n)] = '*';
		}
		System.out.println();
		Table.printMap(map);
		System.out.println();
		System.out.println(weight);
		
		Chrono.start();
		weight = DijkstraWGraph.process(g, start).get(end);
		Chrono.stop();
		System.out.println(weight);
	}
}
