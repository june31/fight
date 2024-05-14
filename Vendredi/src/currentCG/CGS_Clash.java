package currentCG;

import java.util.ArrayList;

import tools.chrono.Chrono;
import tools.collections.map.Mii;
import tools.scanner.Scan;
import tools.strings.S;

public class CGS_Clash {
	public static void main(String[] args) {
		Chrono.start();
		int n = Scan.readInt();
		int a = Scan.readInt();
		var m = new Mii();
		m.put(a, 1);
		int max = 10000 * a;
		for (int z = 2; z <= 12; z++) {
			System.err.println(z);
			var l1 = new ArrayList<>(m.entrySet());
			int fz = z;
			var l2 = new ArrayList<>(l1).stream().filter(e -> e.getValue() <= fz/2).toList();
			for (var e: l1) {
				int x = e.getKey();
				int v = e.getValue();
				for (var f: l2) {
					int y = f.getKey();
					int w = v + f.getValue();
					if (w > 12) continue;
					if (w < z) continue;
					int r;
					r = x + y;
					if (r < max && m.getOrDefault(r, Integer.MAX_VALUE) > w) m.put(r, w);
					r = x - y;
					if (r > 0 && m.getOrDefault(r, Integer.MAX_VALUE) > w) m.put(r, w);
					r = y - x;
					if (r > 0 && m.getOrDefault(r, Integer.MAX_VALUE) > w) m.put(r, w);
					r = x * y;
					if (r < max && m.getOrDefault(r, Integer.MAX_VALUE) > w) m.put(r, w);
					if (y != 0 && x % y == 0) {
						r = x / y;
						if (m.getOrDefault(r, Integer.MAX_VALUE) > w) m.put(r, w);
					}
					if (x != 0 && y % x == 0) {
						r = y / x;
						if (m.getOrDefault(r, Integer.MAX_VALUE) > w) m.put(r, w);
					}
				}
			}
		}
		S.o(m.get(n));
		Chrono.stop();
	}
}