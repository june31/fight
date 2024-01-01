package current;

import tools.collections.int64.Ll;
import tools.scanner.list.ScanLl;

public class Day_0122 {
	public static void main(String[] args) {
		Ll m = new Ll();
		while (true) {
			Ll l = ScanLl.readRaw();
			long s = l.sum();
			if (s == 0) break;
			m.add(s);
		}
		m = m.sortedDown();
		System.out.println(m.get(0));
		System.out.println(m.get(0) + m.get(1) + m.get(2));
	}
}
