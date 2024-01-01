package aoc.done;

import tools.collections.int64.Ll;
import tools.scanner.Scan;

public class Day_05_1 {
	
	private static final long BIT = 1l << 60; 
	public static void main(String[] args) {
		Ll seeds = new Ll(Scan.readLine());
		System.out.println(seeds);
		Scan.readLine();
		for (int z = 0; z < 7; z++) {
			Scan.readLine();
			String[] lines = Scan.readRawStrings();
			for (String line: lines) {
				Ll l = new Ll(line);
				for (int i = 0; i < seeds.size(); i++) {
					long diff = seeds.get(i) - l.get(1);
					if (diff >= 0 && diff < l.get(2)) seeds.set(i, (l.get(0) + diff) | BIT);
				}
			}
			seeds = seeds.mapped(x -> x & ~BIT);
			System.out.println(seeds);
		}
		System.out.println(seeds.min());
	}
}
