package aoc24;

import tools.collections.object.Lo;
import tools.collections.string.Ls;
import tools.scanner.list.ScanLs;
import tools.strings.S;
import tools.structures.graph.node.Node;

public class Day24b {
	static Lo<Tri> xor = new Lo<>(); 
	static Lo<Tri> and = new Lo<>(); 
	static Lo<Tri> or = new Lo<>(); 
	public static void main(String[] args) {
		var ls1 = ScanLs.readRaw();
		var ls2 = ScanLs.readRaw();
		for (var x: ls1) {
			var t = x.split(": ");
			Node.get(t[0]).x = t[1].equals("1") ? 2 : 1;
		}
		for (var x: ls2) {
			var t = x.split(" ");
			Node a = Node.get(t[0]);
			Node b = Node.get(t[2]);
			Node c = Node.get(solve(t[4]));
			if (t[1].equals("XOR")) {
				xor.add(new Tri(a,b,c));
				//S.o(a, b, c);
			}
			if (t[1].equals("AND")) and.add(new Tri(a,b,c));
			if (t[1].equals("OR")) or.add(new Tri(a,b,c));
		}
		
		for (int i = 0; i <= 44; i++) {
			for (Node n: Node.all) n.x = 0;
			for (int j = 0; j <= 44; j++) {
				Node.get("x" + S.s(j, 2)).x = (i == j) ? 2 : 1;
				Node.get("y" + S.s(j, 2)).x = (i == j) ? 2 : 1;
			}			
			int h = Long.numberOfTrailingZeros(calc()) - 1;
			if (i != h) S.o(i, h, Long.toBinaryString(calc()));
			String n = null;
			Tri tt = null;
			String s = "x" + S.s(i, 2);
			for (Tri t: xor) {
				if (t.a.name.equals(s) || t.b.name.equals(s)) {
					n = t.c.name;
					tt = t;
				}
			}
			for (Tri t: xor) {
				if (t.a.name.equals(n) || t.b.name.equals(n)) {
					if (!t.c.name.equals("z" + S.s(i, 2))) {
						S.o(i, tt, t);
					}
				}
			}
		}

		
		Ls sol = Ls.of("vvr", "z08", "bkr", "rnq", "tfb", "z28", "mqh", "z39");
		S.o(sol.sortedUp().join(","));
		
	}
	private static String solve(String s) {
		return switch (s) {
		case "vvr" -> "z08";
		case "z08" -> "vvr";
		case "bkr" -> "rnq";
		case "rnq" -> "bkr";
		case "tfb" -> "z28";
		case "z28" -> "tfb";
		case "mqh" -> "z39";
		case "z39" -> "mqh";
		default -> s;
		};
	}
	static long calc() {
		Lo<Tri> xor2 = new Lo<>(xor);
		Lo<Tri> and2 = new Lo<>(and);
		Lo<Tri> or2 = new Lo<>(or);
		long z = 0;
		while (xor2.size() + and2.size() + or2.size() > 0) {
			for (Tri t: new Lo<>(xor2)) {
				if (t.a.x > 0 && t.b.x > 0) {
					xor2.remove(t);
					t.c.x = ((t.a.x - 1) ^ (t.b.x - 1)) + 1;
				}
			}
			for (Tri t : new Lo<>(and2)) {
				if (t.a.x > 0 && t.b.x > 0) {
					and2.remove(t);
					t.c.x = ((t.a.x - 1) & (t.b.x - 1)) + 1;
				}
			}
			for (Tri t : new Lo<>(or2)) {
				if (t.a.x > 0 && t.b.x > 0) {
					or2.remove(t);
					t.c.x = ((t.a.x - 1) | (t.b.x - 1)) + 1;
				}
			}
		}
		for (Node n: Node.all) {
			if (n.name.startsWith("z")) {
				if (n.x == 2) z |= 1l << S.i(n.name.substring(1));
			}
		}
		return z;
	}
	
	static record Tri(Node a, Node b, Node c) {}
}
