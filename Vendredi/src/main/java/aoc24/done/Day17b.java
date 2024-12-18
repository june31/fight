package aoc24.done;

import tools.chrono.Chrono;
import tools.collections.int32.L;
import tools.collections.multi.Lii;
import tools.scanner.Scan;
import tools.scanner.list.ScanL;
import tools.strings.S;
import tools.tuple.II;

public class Day17b {
	static long a, b, c;
	public static void main(String[] args) {
		Chrono.start();
		Scan.readLine();
		Scan.readLine();
		Scan.readLine();
		Scan.readLine();
		L z = ScanL.readLine();
		Lii l = new Lii();
		for (int i = 0; i < z.size(); i+=2) l.add(new II(z.get(i), z.get(i+1)));
		long n = Long.parseLong("1000000000", 8);
		long o = Long.parseLong("105714775", 8);
		long ap = -n+o;
		Loop: while (true) {
			int pos = 0;
			ap += n;
			a = ap;
			b = 0;
			c = 0;
			int x = 0;
			while (x < l.size()) {
				II m = l.get(x);
				x += 1;
				if (m.i == 0) a = a >> op(m);
				if (m.i == 1) b = b ^ m.v;
				if (m.i == 2) b = op(m) & 7;
				if (m.i == 3) if (a != 0) x = m.v;
				if (m.i == 4) b = b ^ c;
				if (m.i == 5) {
					int k = (int) (op(m) & 7);
					if (z.get(pos) != k) continue Loop;
					pos++;
					if (pos == z.size()) break Loop;
					//if (pos == 6) S.o(Long.toOctalString(ap));
				}
				if (m.i == 6) b = a >> op(m);
				if (m.i == 7) c = a >> op(m);
			}
		}
		S.o(ap);
		Chrono.stop();
	}
	private static long op(II m) {
		if (m.v == 4) return a;
		if (m.v == 5) return b;
		if (m.v == 6) return c;
		return m.v;
	}
}
