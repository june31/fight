package tools.scanner.list;

import tools.collections.node.Ln;
import tools.scanner.Scan;
import tools.structures.graph.node.Node;

public class ScanLn {
	public static Ln readRawNodesDual() { return Ln.createNodesAndLinks(Scan.readRaw(), true); }
	public static Ln readRawNodesSingle() { return Ln.createNodesAndLinks(Scan.readRaw(), false); }
	public static Ln readNodesDual() { return Ln.createNodesAndLinks(Scan.readLines(), true); }
	public static Ln readNodesSingle() { return Ln.createNodesAndLinks(Scan.readLines(), false); }
	public static Ln readNodes() { return readNodes(Scan.readInt()); }
	public static Ln readNodes(int n) {
		Ln ln = new Ln();
		for (int i = 0; i < n; i++) ln.add(Node.get(Scan.readString()));  
		return ln;
	}
}
