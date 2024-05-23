package tools.reference;

import tools.collections.int32.L;
import tools.collections.map.Mii;

public class Marienbad {

	public static Mii solve(L heaps) {
		int n = heaps.size();
		Mii map = new Mii();
		int xor = 0;
		int multi = 0;
		for (int x: heaps) {
			xor ^= x;
			if (x > 1) multi++;
		}
		if (multi == 0 && xor == 0) for (int a = 0; a < n; a++) map.put(a, 1);
		else if (multi == 1) {
			for (int a = 0; a < n; a++) {
				int x = heaps.get(a);
				if (x > 1) map.put(a, x - (xor ^ x ^ 1));
			}
		}
		else if (multi > 1 && xor != 0) {
			for (int a = 0; a < n; a++) {
				int x = heaps.get(a);
				if ((x & Integer.highestOneBit(xor)) != 0)
					map.put(a, (x - (xor ^ x)));
			}
		}
		return map; 
	}
}
