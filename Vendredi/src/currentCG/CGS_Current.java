package currentCG;

import tools.collections.multi.Lii;
import tools.scanner.Scan;
import tools.scanner.list.ScanLii;
import tools.strings.S;

public class CGS_Current {
	public static void main(String[] args) {
		int n = Scan.readInt();
		Lii l = ScanLii.read(n).sortedUp();
		S.e(l);
		S.o(path(l));
	}

	private static Object path(Lii l) {
		return null;
	}
}
