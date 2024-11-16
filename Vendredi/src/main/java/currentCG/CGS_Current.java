package currentCG;

import tools.enumeration.gray.Gray;
import tools.scanner.Scan;
import tools.strings.S;

public class CGS_Current {
	public static void main(String[] args) throws Exception {
        S.o(Math.abs(Gray.fromGray(Scan.readBinary()) - Gray.fromGray(Scan.readBinary())));
	}
}
