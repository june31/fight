package aoc24;

import tools.collections.int32.L;
import tools.collections.multi.LLi;
import tools.scanner.Scan;
import tools.strings.S;

public class Day25 {
	public static void main(String[] args) {
		int z = 0;
		LLi locks = new LLi();
		LLi keys = new LLi();
		int[][] map;
		while ((map = Scan.readMapRaw()) != null) {
			L a = new L();
			if (map[0][0] == '#') { 
				for (int c = 0; c < 5; c++) {
					int l = 0;
					while (map[l++][c] == '#');
					a.add(l - 2);
				}
				locks.add(a);
			} else {
				for (int c = 0; c < 5; c++) {
					int l = 6;
					while (map[l--][c] == '#');
					a.add(4 - l);
				}
				keys.add(a);
			}
		} 
		for (L l: locks) {
			Loop: for (L k: keys) {
				for (int i = 0; i < 5; i++)
					if (l.get(i) + k.get(i) > 5) continue Loop;
				z++;
			}
		}
		S.o(z);
	}
}
