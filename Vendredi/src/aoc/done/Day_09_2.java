package aoc.done;

import java.util.ArrayList;

import tools.collections.int64.Ll;
import tools.scanner.Scan;

public class Day_09_2 {
	
	public static void main(String[] args) {
		String[] in = Scan.readRawStrings();
		long sum = 0;
		for (var s: in) {
			Ll l = new Ll(s);
			var all = new ArrayList<Ll>(); 
			while (l.min() != 0 || l.max() != 0) {
				all.add(l);
				Ll l2 = new Ll();
				for (int i = 0; i < l.size() - 1; i++) l2.add(l.get(i+1) - l.get(i));
				l = l2;
			}
			long d = 0;
			for (int i = all.size()-1; i >= 0; i--) {
				l = all.get(i);
				d = l.get(0) - d;
			}
			sum += d;
		}
		
		System.out.println(sum);
	}
}
