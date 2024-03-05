package currentCG;

import java.util.HashSet;
import java.util.Set;

import tools.collections.string.Ls;
import tools.scanner.Scan;
import tools.strings.S;
import tools.structures.graph.node.Node;

public class CGS_Clash {
	public static void main(String[] args) {
		// 1 = farmer R, 2 = wolf R, 4 = goat R, 8 = cabbage R
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				if ((i & 1) == (j & 1)) continue; // Farmer shall cross the river
				if (j == 6 || j == 12 || j == 14) continue; // Nothing eaten on right side
				if (j == 1 || j == 3 || j == 9) continue; // Nothing eaten on left side
				if (Integer.bitCount(i^j) > 2) continue; // 2 places on boat
				Node.buildSingle(getName(i), getName(j));
			}
		}
		S.o(scan(new HashSet<>(), Node.get(Scan.readLine()), Node.get(Scan.readLine())));
	}

	private static String scan(Set<Node> cache, Node n, Node e) {
		if (n == e) return e.name;
		if (!cache.add(n)) return null;
		Ls ls = new Ls();
		for (Node c: n.links) {
			String s = scan(new HashSet<>(cache), c, e);
			if (s != null) ls.add(s);
		}
		if (ls.isEmpty()) return null;
		ls.sort((s1, s2) -> {
			if (s1.length() != s2.length()) return s1.length() - s2.length();
			return s1.compareTo(s2);
		});
		return n.name + "\n" + ls.get(0);
	}

	private static String getName(int n) {
		return ((n & 1) == 0 ? "L " : "R ")
				+ ((n & 2) == 0 ? "L " : "R ")
				+ ((n & 4) == 0 ? "L " : "R ")
				+ ((n & 8) == 0 ? "L" : "R");
	}
}