package aoc.done;

import tools.bfs.BFSGraph;
import tools.collections.node.Ln;
import tools.scanner.Scan;

public class Day_25_1 {
	public static void main(String[] args) {
		Ln nodes = Scan.readRawNodesDual();
		int s = 0;
		BFSGraph bfs = new BFSGraph();
		for (int i = 1; i < nodes.size(); i++) {
			Ln copy = nodes.deepCopy();
			for (int j = 0; j < 4; j++) {
				bfs.diffuse(copy.get(0), copy.get(i));
				Ln sp = bfs.shortestPath();
				if (sp == null) { s++; break; }
				copy.removeLinksDual(sp.getLinks());
			}
		}
		System.out.println(s * (nodes.size() - s));
	}
}