package tools.mapper;

import tools.collections.int32.L;
import tools.collections.node.Ln;
import tools.structures.graph.node.Node;

public class MapLn {
	public static Ln fromL(L l) {
		Ln ln = new Ln();
		for (int i: l) ln.add(Node.fromName(i));
		return ln;
	}
	public static L toL(Ln ln) {
		L l = new L();
		for (Node n: ln) l.add(n.x);
		return l;
	}
}
