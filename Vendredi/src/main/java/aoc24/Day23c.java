package aoc24;

import tools.collections.node.Ln;
import tools.mapper.MapLn;
import tools.scanner.list.ScanLs;
import tools.strings.S;
import tools.structures.graph.node.Node;

public class Day23c {
	public static void main(String[] args) {
		var ls = ScanLs.readRaw();
		for (var x: ls) {
			var t = x.split("-");
			Node.buildDual(t[0], t[1]);
		}
		S.o(MapLn.toLs(rec(new Ln(Node.all), new Ln())).sortedUp().join(","));
	}
	
	private static Ln rec(Ln remain, Ln keep) {
		Ln maxLan = keep;
		while (!remain.isEmpty()) {
			Node n = remain.first();
			if (remain.size() == Node.all.size()) S.o(n);
			//S.o(MapLn.toLs(remain));
			Ln subK = new Ln(keep);
			subK.add(n);
			Ln subR = new Ln(remain).filtered(r -> r.isLinkedTo(n));
			if (subK.size() > maxLan.size()) maxLan = subK; 
			remain.removeAll(rec(subR, subK));
		}
		return maxLan;
	}
}
