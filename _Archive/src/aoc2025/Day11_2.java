package aoc2025;

import java.util.HashMap;
import java.util.Map;

import tools.scanner.list.ScanLs;
import tools.strings.S;
import tools.structures.graph.node.Node;

public class Day11_2 {
	Map<Node, long[]> memo = new HashMap<>();
	void main() {
		for (String s: ScanLs.readRaw()) {
			String[] tks = s.split(": ");
			for (String cs: tks[1].split(" ")) Node.buildSingle(tks[0], cs);
		}
		S.o(search(Node.get("svr"))[3]);
	}
	
	long[] search(Node n) {
		if (memo.containsKey(n)) return memo.get(n);
		long[] res = new long[4];
		Node target = Node.get("out");
		if (n == target) {
			res[0] = 1;
			return res;
		}
		int m = 0;
		if (n.equals(Node.get("fft"))) m = 2;
		else if (n.equals(Node.get("dac"))) m = 1;
		for (Node nn: n.links) {
			long[] v = search(nn);
			res[0|m] += v[0];
			res[1|m] += v[1];
			res[2|m] += v[2];
			res[3|m] += v[3];
		}
		memo.put(n, res);
		return res;
	}
}
