package training;

import tools.output.Out;
import tools.scanner.Scan;

public class IsoContest_2 {

	public static void main(String[] args) {
		int n = Scan.readInt();
		String[] t = Scan.readStringArray(n);
		for (int i = 0; i < n; i++) {
			if (t[i].equals("" + new StringBuilder(t[i]).reverse())) Out.space(t[i]);
		}
		Out.flush();
	}
}
