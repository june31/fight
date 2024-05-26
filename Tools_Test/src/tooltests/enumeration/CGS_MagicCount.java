package tooltests.enumeration;

import tools.collections.int32.L;
import tools.enumeration.combinations.IntCombinations;
import tools.scanner.Scan;
import tools.scanner.list.ScanL;
import tools.strings.S;

// https://www.codingame.com/ide/puzzle/magic-count-of-numbers
public class CGS_MagicCount {
	public static void main(String[] args) {
		long n = Scan.readLong();
		int k = Scan.readInt();
		L l = ScanL.read(k);
		long sum = 0;
		for (int i = 1; i <= k; i++) {
			for (L c : new IntCombinations(l, i)) {
				long prod = c.mulLong();
				sum += n / prod * ((i & 1) * 2 - 1);
			}
		}
		S.o(sum);
	}
}
