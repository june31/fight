package tooltests.bfs;

import tools.bfs.BFSGraph;
import tools.scanner.Scan;
import tools.structures.graph.Graph;
import tools.structures.graph.node.Node;
import tools.tables.Table;

// https://www.codingame.com/ide/puzzle/hexagonal-maze
public class CGS_BFSGraph_HexMaze1 {
	
	static int[][] map;
	static Node[][] nodes;
	static Node S, E;
	static int L, C;
	
	public static void main(String[] args) {
		map = Scan.readMapCL();
		L = map.length;
		C = map[0].length;
		nodes = new Node[L][C];
		Graph graph = new Graph(); 
		Table.forEach(map, (l, c, v) -> {
			nodes[l][c] = new Node();
		    graph.addNode(nodes[l][c]);
		});
		Table.forEach(nodes, (l, c, node) -> {
			if (map[l][c] == 'S') S = node;
			if (map[l][c] == 'E') E = node;
			addLink(node, l, c - 1);
			addLink(node, l, c + 1);
			addLink(node, l - 1, c);
			addLink(node, l + 1, c);
			addLink(node, l - 1, c + (l % 2 == 0 ? -1 : 1));
			addLink(node, l + 1, c + (l % 2 == 0 ? -1 : 1));
		});
		
		BFSGraph bfs = new BFSGraph(graph);
		bfs.diffuse(S, E);
		bfs.shortestPath().stream()
			.skip(1) // S
			.forEach(n -> map[n.id/C][n.id%C] = '.');
		map[E.id/C][E.id%C] = 'E';
		
		Table.showMap(map);
	}

	static void addLink(Node node, int l, int c) {
		l = Math.floorMod(l, L);
		c = Math.floorMod(c, C);
		if (map[l][c] == '#') return;
		node.links.add(nodes[l][c]);
	}
}
