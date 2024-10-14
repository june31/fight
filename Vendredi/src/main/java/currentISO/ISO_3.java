package currentISO;

import tools.collections.map.Msi;
import tools.collections.multi.Lis;
import tools.scanner.list.ScanLis;
import tools.scanner.list.ScanMsi;
import tools.strings.S;
import tools.tuple.IS;

public class ISO_3 {
	public static void main(String[] args) {
		Lis a = ScanLis.read();
		Msi m = ScanMsi.readInverse();
		S.o(a.mapped(is -> new IS(Math.min(is.i, m.getOrZero(is.s)), is.s)).sum());
	}
}
