package aoc24;

import tools.chrono.Chrono;
import tools.collections.object.Lo;
import tools.collections.string.Ls;
import tools.enumeration.combinations.Combinations;
import tools.function.BiIntToIntFunction;
import tools.scanner.list.ScanLs;
import tools.strings.S;
import tools.structures.graph.node.Node;

public class Day24c {
	static Lo<Op> ops = new Lo<>(); 
	static int bits = ScanLs.readRaw().size() / 2;
	public static void main(String[] args) {
		Chrono.start();
		var ls2 = ScanLs.readRaw();
		for (var s: ls2) {
			var t = s.split(" ");
			Node a = Node.get(t[0]);
			Node b = Node.get(t[2]);
			Node c = Node.get(t[4]);
			if (t[1].equals("XOR")) ops.add(new Op(a,b,c, (x,y) -> x^y));
			if (t[1].equals("AND")) ops.add(new Op(a,b,c, (x,y) -> x&y));
			if (t[1].equals("OR")) ops.add(new Op(a,b,c, (x,y) -> x|y));
		}
		
		Ls sol = new Ls();
		int errors = getErrors();
		while (errors > 0) {
			int max = errors;
			Op ma = null;
			Op mb = null;
			for (var sw: new Combinations<>(ops, 2)) {
				var a = sw.get(0).c;
				var b = sw.get(1).c;
				sw.get(0).c = b;
				sw.get(1).c = a;
				int er2 = getErrors();
				if (er2 < max) {
					max = er2;
					ma = sw.get(0);
					mb = sw.get(1);
				}
				sw.get(0).c = a;
				sw.get(1).c = b;
			}
			var tmp = ma.c;
			ma.c = mb.c;
			mb.c = tmp;
			sol.add(ma.c.name);
			sol.add(mb.c.name);
		}
		S.o(sol.sortedUp().join(","));
		Chrono.stop();
	}
	
	static int getErrors() {
		int errors = 0;
		for (int i = 0; i < bits; i++) {
			for (Node n: Node.all) n.x = 0;
			for (int j = 0; j < bits; j++) {
				Node.get("x" + S.s(j, 2)).x = (i == j) ? 2 : 1;
				Node.get("y" + S.s(j, 2)).x = (i == j) ? 2 : 1;
			}
			if (calc() != 2l << i) errors++;
		}
		return errors;
	}
	
	static long calc() {
		Lo<Op> ops2 = new Lo<>(ops);
		int size = 0;
		while (ops2.size() != size) {
			size = ops2.size();
			for (Op op: new Lo<>(ops2)) {
				if (op.a.x > 0 && op.b.x > 0) {
					ops2.remove(op);
					op.c.x = op.f.apply(op.a.x - 1, op.b.x - 1) + 1;
				}
			}
		}
		if (size > 0) return 64;
		long z = 0;
		for (Node n: Node.all)
			if (n.name.startsWith("z") && n.x == 2) z |= 1l << S.i(n.name.substring(1));
		return z;
	}
	
	static class Op {
		Node a;
		Node b;
		Node c;
		BiIntToIntFunction f;

		Op(Node a, Node b, Node c, BiIntToIntFunction f) {
			this.a = a;
			this.b = b;
			this.c = c;
			this.f = f;
		}
	}
}
