package aoc2025;

import java.util.HashMap;
import java.util.Map;

import tools.scanner.list.ScanLs;
import tools.strings.S;
import tools.structures.graph.node.Node;

public class Day11_1 {
	Map<Node, Long> memo = new HashMap<>();
	void main() {
		for (String s: ScanLs.readRaw()) {
			String[] tks = s.split(": ");
			for (String cs: tks[1].split(" ")) Node.buildSingle(tks[0], cs);
		}
		S.o(search(Node.get("you")));
	}
	
	long search(Node n) {
		if (memo.containsKey(n)) return memo.get(n);
		if (n == Node.get("out")) return 1;
		long total = 0;
		for (Node nn: n.links) {
			total += search(nn);
		}
		memo.put(n, total);
		return total;
	}
}
