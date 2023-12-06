package aoc;

import tools.collections.int64.Ll;
import tools.scanner.Scan;

public class Day_06_2 {
	
	public static void main(String[] args) {
		long t = new Ll(Scan.readLine().replace(" ", "")).get(0);
		long d = new Ll(Scan.readLine().replace(" ", "")).get(0);

		long ok = 0;
		for (int j = 1; j < t; j++) if (j*(t - j) > d) ok++;

		System.out.println(ok);
	}
}
