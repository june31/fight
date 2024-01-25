package currentCG;

import tools.output.Out;
import tools.scanner.Scan;

public class CGS_Clash {
	public static void main(String[] args) {
		int nl = Scan.readInt();
		int a = Scan.readInt();
		String s = Scan.readLine();
		for (int i = 0; i < nl; i++) {
			for (int j = 0; j < a; j++) {
				Out.buf(s.charAt(i%s.length()));
			}
			Out.bufln();
		}
		Out.flush();
	}
}
