package aoc24;

import tools.scanner.list.ScanLs;
import tools.strings.S;
import tools.structures.graph.node.Node;

public class Day23a {
	private static long z = 0;
	public static void main(String[] args) {
		var ls = ScanLs.readRaw();
		for (var x: ls) {
			var t = x.split("-");
			Node.buildDual(t[0], t[1]);
		}
		for (int i = 0; i < Node.count; i++) {
			Node a = Node.all.get(i);
			for (int j = i+1; j < Node.count; j++) {
				Node b = Node.all.get(j);
				if (a.isLinkedTo(b)) {
					for (int k = j+1; k < Node.count; k++) {
						Node c = Node.all.get(k);
						if (a.isLinkedTo(c) && b.isLinkedTo(c)) {
							if (a.name.startsWith("t") || b.name.startsWith("t") || c.name.startsWith("t")) z++;
						}
					}
				}
			}
		}
		S.o(z);
	}
}
