package aoc;

import tools.collections.int32.L;
import tools.enumeration.combinations.IntCombinations;
import tools.scanner.Scan;

public class Day_12_1 {
	
	public static void main(String[] args) {
		String[] in = Scan.readRawStrings();
		long s = 0;
		for (var line: in) {
			System.out.println(line);
			String[] tk = line.split(" ");
			L vs = new L(tk[1]);
			int nv = vs.size();
			String[] t = tk[0].replace('.', ' ').trim().split(" +");
			int nt = t.length; 
			int[] nbs = new int[vs.size() - 1];
			for (int i = 0; i < nbs.length; i++) nbs[i] = i + 1;
			long ss = 0;
			for (var comb: new IntCombinations(nbs, nt - 1)) {
				L c = new L(comb);
				c.add(nv);
				System.out.println(c);
				int prev = 0;
				long sss = 1;
				for (int i = 0; i < nt; i++) {
					String block = t[i];
					int n = block.length();
					L sub = vs.sub(prev, c.get(i));
					prev = c.get(i);
					System.out.println("-" + sub + ": " + cmb(block, sub));
					sss *= cmb(block, sub);
				}
				System.out.println(sss);
				ss += sss;
			}
			System.out.println("-> " + ss + "\n");
			s += ss;
		}
		
		System.out.println(s);
	}

	private static long cmb(String s, L l) {
		long sum = 0;
		
		int n = s.length();
		int mask = 0;
		for (char c: s.toCharArray()) {
			mask *= 2;
			if (c == '#') mask++;
		}
		
		int max = 1<<n;
		Loop: for (int i = 0; i < max; i++) {
			if ((mask & ~i) != 0) continue;
			int pos = 1<<n;
			for (int v: l) {
				while ((i & pos) == 0 && pos > 0) pos /= 2;
				if ((i & pos) == 0 && pos == 0) continue Loop;
				int nb = 0;
				while ((i & pos) != 0 && pos > 0) {
					pos /= 2;
					nb++;
				}
				if (nb != v) continue Loop;
			}
			while ((i & pos) == 0 && pos > 0) pos /= 2;
			if (pos == 0) sum++;
		}
		return sum;
	}
}
