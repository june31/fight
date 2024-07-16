package currentCG;

import tools.scanner.Scan;
import tools.strings.Regex;
import tools.strings.S;

public class CGS_Clash {
	public static void main(String[] args) {
		String t = Scan.readLine();
		String r = Regex.escape(Scan.readLine().replace('?', '€')).replace("~", ".*").replace("€", ".");
		S.o(t.matches(r) ? "MATCH" : "FAIL");
	}
}
