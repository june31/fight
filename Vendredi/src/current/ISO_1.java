package current;

import java.util.HashMap;

import tools.collections.int32.L;
import tools.scanner.Scan;
import tools.scanner.list.ScanL;

public class ISO_1 {
	public static void main(String[] args) {
		Scan.readInt();
		long K = Scan.readLong();
		L l = ScanL.readLine().dec();
		var s = new HashMap<Integer, Integer>();
		int x = 0;
		boolean loop = false;
		for (int i = 0; i < K; i++) {
			var o = s.get(x);
			if (!loop && o != null) {
				K = (i-o) + i + (K-i) % (i-o);
				loop = true;
			}
			s.put(x, i);
			x = l.get(x);
		}
		System.out.println(x + 1);
	}
}
