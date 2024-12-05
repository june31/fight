package aoc24.done;

import java.util.LinkedList;
import java.util.Queue;

import tools.collections.int32.L;
import tools.collections.node.Ln;
import tools.collections.node.Sn;
import tools.mapper.MapLn;
import tools.scanner.list.ScanLs;
import tools.strings.S;
import tools.structures.graph.node.Node;

public class Day05a {
	
	private static Ln ln;
	public static void main(String[] args) {
		var ls = ScanLs.readRaw();
		long z = 0;
		for (var x: ls) {
			String[] parts = x.split("\\|"); 
			Node.buildSingle(parts[1], parts[0]);
		}
		ls = ScanLs.readRaw();
		Loop: for (var x: ls) {
			L l = new L(x);
			ln = MapLn.fromL(l);
			for (int i = 0; i < l.size(); i++) {
				Ln lnp = propagate(Node.get(l.get(i)));
				for (int j = i + 1; j < l.size(); j++) {
					if (lnp.contains(Node.get(l.get(j)))) continue Loop;
				}
			}
			z += l.get(l.size() / 2);
		}
		S.o(z);
	}
	
	public static Ln propagate(Node s) {
		Ln contacts = new Ln();
		Sn reached = new Sn();
		Queue<Node> queue = new LinkedList<>();
		queue.add(s);
		while (!queue.isEmpty()) {
			Node n = queue.poll();
			if (reached.contains(n)) continue;
			if (!ln.contains(n)) continue;
			reached.add(n);
			contacts.add(n);
			queue.addAll(n.links);
		}
		return contacts; 
	}
}
