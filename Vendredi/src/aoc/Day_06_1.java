package aoc;

import tools.collections.int32.L;
import tools.scanner.list.ScanL;

public class Day_06_1 {
	
	public static void main(String[] args) {
		L times = ScanL.readLine();
		L dists = ScanL.readLine();
		
		long mul = 1l;
		for (int i = 0; i < times.size(); i++) {
			int ok = 0;
			for (int j = 1; j < times.get(i); j++) if (j*(times.get(i) - j) > dists.get(i)) ok++;
			mul *= ok;
		}
		
		System.out.println(mul);
	}
}
