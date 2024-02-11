package tools.mapper;

import tools.collections.int32.L;
import tools.collections.node.Ln;
import tools.collections.string.Ls;
import tools.structures.graph.node.Node;

public class MapLn {
	public static Ln fromL(L l) {
		Ln ln = new Ln();
		for (int i: l) ln.add(Node.get(i));
		return ln;
	}
	public static L toL(Ln ln) {
		L l = new L();
		for (Node n: ln) l.add(n.x);
		return l;
	}
	public static Ln fromLs(Ls l) {
		Ln ln = new Ln();
		for (String s: l) ln.add(Node.get(s));
		return ln;
	}
	public static Ls toLs(Ln ln) {
		Ls l = new Ls();
		for (Node n: ln) l.add(n.name);
		return l;
	}
}
