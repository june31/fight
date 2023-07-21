package tooltests.bfs;

import java.util.ArrayList;
import java.util.List;

import tools.bfs.BFSGraph;
import tools.enumeration.any.MixAny;
import tools.output.Out;
import tools.scanner.Scan;
import tools.structures.graph.Graph;
import tools.structures.graph.node.Node;
import tools.tables.Table;

// https://www.codingame.com/ide/puzzle/hexagonal-maze---part2
public class CGS_BFSGraph_HexMaze2 {
	
	static int[][] map;
	static Node[][] nodes;
	static Node S, E, ka, kb, kc, kd;
	static int L, C, keychain;
	static byte[][] linkConditions;
	
	public static void main(String[] args) {
		map = Scan.readMapCL();
		L = map.length;
		C = map[0].length;
		nodes = new Node[L][C];
		Graph graph = new Graph();
		Table.forEach(map, (l, c) -> {
			nodes[l][c] = new Node();
		    graph.addNode(nodes[l][c]);
		});
		linkConditions = new byte[graph.size()][graph.size()];
		Table.forEach(nodes, (l, c, node) -> {
			if (map[l][c] == 'S') S = node;
			if (map[l][c] == 'E') E = node;
			if (map[l][c] == 'a') ka = node;
			if (map[l][c] == 'b') kb = node;
			if (map[l][c] == 'c') kc = node;
			if (map[l][c] == 'd') kd = node;
			addLink(node, 0, -1);
			addLink(node, 0, 1);
			addLink(node, -1, 0);
			addLink(node, 1, 0);
			addLink(node, -1, l % 2 == 0 ? -1 : 1);
			addLink(node, 1, l % 2 == 0 ? -1 : 1);
		});
		
		BFSGraph bfs = new BFSGraph(graph);
		List<Node> shortestPath = null;
		int shortestDist = Integer.MAX_VALUE;
		
		List<Node> allKeys = new ArrayList<>();
		if (ka != null) allKeys.add(ka);
		if (kb != null) allKeys.add(kb);
		if (kc != null) allKeys.add(kc);
		if (kd != null) allKeys.add(kd);
		Loop: for (List<Node> keys: new MixAny<Node>(allKeys)) {
			keys.add(E);
			Node start = S;
			keychain = 0;
			List<Node> path = new ArrayList<>();
			int dist = 0;
			for (Node target: keys) {
				bfs.diffuse(start, target, () -> {
					int cond = linkConditions[bfs.n1.id][bfs.n2.id];
					if (cond == 0) {
						int v = map[bfs.n2.id/C][bfs.n2.id%C] - 'A'; 
						if (v < 0 || v > 3) return true;
						return (keychain & 1<<v) != 0;
					} else return (keychain & 1<<(cond - 'A')) == 0;
				});
				if (!bfs.found) continue Loop;
				List<Node> miniPath = bfs.shortestPath();
				miniPath.remove(miniPath.size() - 1);
				dist += miniPath.size();
				if (dist >= shortestDist) continue Loop;
				path.addAll(miniPath);
				if (target == ka) keychain |= 1;
				if (target == kb) keychain |= 2;
				if (target == kc) keychain |= 4;
				if (target == kd) keychain |= 8;
				start = target;
			}
			if (dist < shortestDist) {
				shortestDist = dist;
				shortestPath = path;
			}
		}
		
		shortestPath.add(E);
		for (int i = 0; i < shortestDist; i++) {
			Node n1 = shortestPath.get(i);
			Node n2 = shortestPath.get(i + 1);
			int dl = n2.id/C - n1.id/C;
			int dc = n2.id%C - n1.id%C;
			if (dc == 0) dc = (n2.id/C) % 2 == 1 ? 1 : -1;
			if (dc > 0) Out.space(dl < 0 ? "UR" : (dl > 0 ? "DR" : "R"));
			else Out.space(dl < 0 ? "UL" : (dl > 0 ? "DL" : "L"));
		}
		Out.flushln();
	}

	static void addLink(Node node, int dl, int dc) {
		Node slide = null;
		int l = node.id / C;
		int c = node.id % C;
		while (true) {
			l += dl;
			c += dc;
			if (l < 0 || c < 0 || l >= L || c >= C || map[l][c] == '#') {
				if (slide != null) node.links.add(slide);
				return;
			}
			int v = map[l][c];
			if (v != '_') {
				node.links.add(nodes[l][c]);
				if (slide != null && v >= 'A' && v <= 'D') {
					node.links.add(slide);
					linkConditions[node.id][slide.id] = (byte) v;
				}
				return;
			}
			slide = nodes[l][c];
			if (dl != 0) dc += l % 2 == 1 ? 1 : -1;
		}
	}
}
