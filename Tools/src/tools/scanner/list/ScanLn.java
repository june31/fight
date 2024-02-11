package tools.scanner.list;

import tools.collections.node.Ln;
import tools.scanner.Scan;

public class ScanLn {
	public static Ln readRawNodesDual() { return Ln.createNodesAndLinks(Scan.readRawLines(), true); }
	public static Ln readRawNodesSingle() { return Ln.createNodesAndLinks(Scan.readRawLines(), false); }
	public static Ln readNodesDual() { return Ln.createNodesAndLinks(Scan.readLines(), true); }
	public static Ln readNodesSingle() { return Ln.createNodesAndLinks(Scan.readLines(), false); }
}
