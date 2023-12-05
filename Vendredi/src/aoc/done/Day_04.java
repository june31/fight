package aoc.done;

import tools.collections.int32.L;
import tools.scanner.Scan;
import tools.tables.Table;

public class Day_04 {
	
	public static void main(String[] args) {
		String[] input = Scan.readRawStrings();

		// Star 1
		int s = 0;
		for (String card: input) {
			String[] t = card.split(":|\\|");
			L w = new L(t[1]);
			L p = new L(t[2]);
			p.retainAll(w);
			s += (1 << p.size()) / 2;
		}
		System.out.println(s);
		
		// Star 2
		int[] copies = new int[input.length];
		Table.fill(copies, 1);
		
		for (int i = 0; i < input.length; i++) {
			String[] t = input[i].split(":|\\|");
			L w = new L(t[1]);
			L p = new L(t[2]);
			p.retainAll(w);
			int l = p.size();
			for (int j = i+1; j < i+1+l; j++) copies[j] += copies[i];
		}
		System.out.println(new L(copies).sum());
	}
}
