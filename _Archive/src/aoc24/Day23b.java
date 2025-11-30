package aoc24;

import tools.collections.node.Ln;
import tools.mapper.MapLn;
import tools.scanner.list.ScanLs;
import tools.strings.S;
import tools.structures.graph.node.Node;

public class Day23b {
	private static Ln maxLan = new Ln();
	public static void main(String[] args) {
		var ls = ScanLs.readRaw();
		for (var x: ls) {
			var t = x.split("-");
			Node.buildDual(t[0], t[1]);
		}
		rec(new Ln(Node.all), new Ln());
	}
	
	private static void rec(Ln remain, Ln keep) {
		for (Node n: remain) {
			Ln subK = new Ln(keep);
			subK.add(n);
			Ln subR = new Ln(remain).filtered(r -> r.isLinkedTo(n));
			if (subR.isEmpty()) {
				if (subK.size() > maxLan.size()) {
					maxLan = subK; 
					S.o(MapLn.toLs(maxLan).sortedUp().join(","));

				}
			}
			else rec(subR, subK);
		}
	}
}
