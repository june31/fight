package aoc.done;

import tools.bfs.BFSGraph;
import tools.collections.node.Ln;
import tools.scanner.list.ScanLn;

public class Day_25_1 {
	public static void main(String[] args) {
		Ln nodes = ScanLn.readRawNodesDual();
		int s = 0;
		for (int i = 1; i < nodes.size(); i++) {
			Ln copy = nodes.deepCopy();
			for (int j = 0; j < 4; j++) {
				Ln path = new BFSGraph().reach(copy.get(0), copy.get(i));
				if (path == null) { s++; break; }
				copy.removeLinksDual(path.getLinks());
			}
		}
		System.out.println(s * (nodes.size() - s));
	}
}