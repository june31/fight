package aoc.done;

import java.util.ArrayList;

import tools.bfs.BFSWGraph;
import tools.chrono.Chrono;
import tools.reference.Dijkstra;
import tools.reference.DijkstraWGraph;
import tools.scanner.Scan;
import tools.structures.graph.WGraph;
import tools.structures.graph.node.WNode;

public class Day_17_1 {
	
	private static final int Up = 0;
	private static final int Down = 3;
	private static final int Left = 6;
	private static final int Right = 9;
	private static final int[][] map = Scan.readMap0();
	private static final int L = map.length;
	private static final int C = map[0].length;
	private static final WNode[][][] nodes = new WNode[L][C][12];
	
	public static void main(String[] args) {

		var nodeList = new ArrayList<WNode>();
		for (int l = 0; l < L; l++) {
			for (int c = 0; c < C; c++) {
				for (int i = 0; i < 12; i++) {
					nodes[l][c][i] = new WNode(map[l][c] - '0');
					nodeList.add(nodes[l][c][i]);
				}
			}
		}

		WGraph g = new WGraph(nodeList);
		for (int l = 0; l < L; l++) {
			for (int c = 0; c < C; c++) {
				if (l > 0) {
					g.singleLink(nodes[l][c][Left], nodes[l-1][c][Up]);
					g.singleLink(nodes[l][c][Left+1], nodes[l-1][c][Up]);
					g.singleLink(nodes[l][c][Left+2], nodes[l-1][c][Up]);
					g.singleLink(nodes[l][c][Right], nodes[l-1][c][Up]);
					g.singleLink(nodes[l][c][Right+1], nodes[l-1][c][Up]);
					g.singleLink(nodes[l][c][Right+2], nodes[l-1][c][Up]);
					g.singleLink(nodes[l][c][Up], nodes[l-1][c][Up+1]);
					g.singleLink(nodes[l][c][Up+1], nodes[l-1][c][Up+2]);
				}
				if (l < L-1) {
					g.singleLink(nodes[l][c][Left], nodes[l+1][c][Down]);
					g.singleLink(nodes[l][c][Left+1], nodes[l+1][c][Down]);
					g.singleLink(nodes[l][c][Left+2], nodes[l+1][c][Down]);
					g.singleLink(nodes[l][c][Right], nodes[l+1][c][Down]);
					g.singleLink(nodes[l][c][Right+1], nodes[l+1][c][Down]);
					g.singleLink(nodes[l][c][Right+2], nodes[l+1][c][Down]);
					g.singleLink(nodes[l][c][Down], nodes[l+1][c][Down+1]);
					g.singleLink(nodes[l][c][Down+1], nodes[l+1][c][Down+2]);
				}
				if (c > 0) {
					g.singleLink(nodes[l][c][Up], nodes[l][c-1][Left]);
					g.singleLink(nodes[l][c][Up+1], nodes[l][c-1][Left]);
					g.singleLink(nodes[l][c][Up+2], nodes[l][c-1][Left]);
					g.singleLink(nodes[l][c][Down], nodes[l][c-1][Left]);
					g.singleLink(nodes[l][c][Down+1], nodes[l][c-1][Left]);
					g.singleLink(nodes[l][c][Down+2], nodes[l][c-1][Left]);
					g.singleLink(nodes[l][c][Left], nodes[l][c-1][Left+1]);
					g.singleLink(nodes[l][c][Left+1], nodes[l][c-1][Left+2]);
				}
				if (c < C-1) {
					g.singleLink(nodes[l][c][Up], nodes[l][c+1][Right]);
					g.singleLink(nodes[l][c][Up+1], nodes[l][c+1][Right]);
					g.singleLink(nodes[l][c][Up+2], nodes[l][c+1][Right]);
					g.singleLink(nodes[l][c][Down], nodes[l][c+1][Right]);
					g.singleLink(nodes[l][c][Down+1], nodes[l][c+1][Right]);
					g.singleLink(nodes[l][c][Down+2], nodes[l][c+1][Right]);
					g.singleLink(nodes[l][c][Right], nodes[l][c+1][Right+1]);
					g.singleLink(nodes[l][c][Right+1], nodes[l][c+1][Right+2]);
				}
			}
		}
		
		WNode start = new WNode();
		g.addNode(start);
		g.singleLink(start, nodes[0][1][Right]);
		g.singleLink(start, nodes[1][0][Down]);
		
		WNode end = new WNode();
		g.addNode(end);
		for (int i = 0; i < 12; i++) g.singleLink(nodes[L-1][C-1][i], end);
		
		Chrono.start();
		int weight = new BFSWGraph(g).diffuse(start, end);
		Chrono.stop();
		System.out.println(weight);

		Chrono.start();
		weight = DijkstraWGraph.process(g, start).get(end);
		Chrono.stop();
		System.out.println(weight);
	}
}
