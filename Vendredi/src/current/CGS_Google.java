package current;

import tools.dichotomy.Search;
import tools.scanner.Scan;

public class CGS_Google {
	public static void main(String[] args) {
		int n = Scan.readInt();
		System.out.println(Search.minTrue(i -> i*(i+1) >= 2*n));
	}
}
