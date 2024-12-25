package aoc24;

import tools.collections.object.Lo;
import tools.scanner.list.ScanLs;
import tools.strings.S;
import tools.structures.graph.node.Node;

public class Day24a {
	private static long z = 0;
	public static void main(String[] args) {
		var ls1 = ScanLs.readRaw();
		var ls2 = ScanLs.readRaw();
		for (var x: ls1) {
			var t = x.split(": ");
			Node.get(t[0]).x = t[1].equals("1") ? 2 : 1;
		}
		Lo<Tri> xor = new Lo<>(); 
		Lo<Tri> and = new Lo<>(); 
		Lo<Tri> or = new Lo<>(); 
		for (var x: ls2) {
			var t = x.split(" ");
			Node a = Node.get(t[0]);
			Node b = Node.get(t[2]);
			Node c = Node.get(t[4]);
			if (t[1].equals("XOR")) xor.add(new Tri(a,b,c));
			if (t[1].equals("AND")) and.add(new Tri(a,b,c));
			if (t[1].equals("OR")) or.add(new Tri(a,b,c));
		}
		while (xor.size() + and.size() + or.size() > 0) {
			for (Tri t: new Lo<>(xor)) {
				if (t.a.x > 0 && t.b.x > 0) {
					xor.remove(t);
					t.c.x = ((t.a.x - 1) ^ (t.b.x - 1)) + 1;
				}
			}
			for (Tri t : new Lo<>(and)) {
				if (t.a.x > 0 && t.b.x > 0) {
					and.remove(t);
					t.c.x = ((t.a.x - 1) & (t.b.x - 1)) + 1;
				}
			}
			for (Tri t : new Lo<>(or)) {
				if (t.a.x > 0 && t.b.x > 0) {
					or.remove(t);
					t.c.x = ((t.a.x - 1) | (t.b.x - 1)) + 1;
				}
			}
		}
		for (Node n: Node.all) {
			if (n.name.startsWith("z")) {
				if (n.x == 2) z |= 1l << S.i(n.name.substring(1));
			}
		}
		
		S.o(z);
	}
	
	static record Tri(Node a, Node b, Node c) {}
}
