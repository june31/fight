package aoc24.done;

import tools.collections.int32.L;
import tools.collections.multi.Lii;
import tools.scanner.Scan;
import tools.scanner.list.ScanL;
import tools.strings.S;
import tools.tuple.II;

public class Day17a {
	static int a = ScanL.readLine().get(0);
	static int b = ScanL.readLine().get(0);
	static int c = ScanL.readLine().get(0);
	public static void main(String[] args) {
		Scan.readLine();
		L z = ScanL.readLine();
		Lii l = new Lii();
		for (int i = 0; i < z.size(); i+=2) l.add(new II(z.get(i), z.get(i+1)));
		int x = 0;
		L out = new L();
		S.o(a, b, c);
		while (x < l.size()) {
			II m = l.get(x);
			x += 1;
			if (m.i == 0) a = a >> op(m);
			if (m.i == 1) b = b ^ m.v;
			if (m.i == 2) b = op(m) & 7;
			if (m.i == 3) if (a != 0) x = m.v;
			if (m.i == 4) b = b ^ c;
			if (m.i == 5) out.add(op(m) & 7);
			if (m.i == 6) b = a >> op(m);
			if (m.i == 7) c = a >> op(m);
		}
		S.o(out.join(","));
		S.o(a, b, c);
	}
	private static int op(II m) {
		if (m.v == 4) return a;
		if (m.v == 5) return b;
		if (m.v == 6) return c;
		return m.v;
	}
}
