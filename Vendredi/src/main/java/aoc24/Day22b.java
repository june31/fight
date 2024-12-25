package aoc24;

import tools.collections.int32.L;
import tools.scanner.list.ScanL;
import tools.strings.S;

public class Day22b {
	public static void main(String[] args) {
		L l = ScanL.readRaw();
		var t = new int[l.size()][2001];
		for (int i = 0; i < l.size(); i++) {
			var x = l.get(i);
			for (int j = 0; j < 2001; j++) {
				t[i][j] = x % 10;
				x ^= x << 6;
				x &= 0xffffff;
				x ^= x >> 5;
				x &= 0xffffff;
				x ^= x << 11;
				x &= 0xffffff;
			}
		}

		int maxBananas = 0;
		for (int a = -9; a <= 9; a++) {
			for (int b = -9; b <= 9; b++) {
				S.o(a, b);
				for (int c = -9; c <= 9; c++) {
					for (int d = -9; d <= 9; d++) {
						int all = 0;
						for (int i = 0; i < l.size(); i++) {
							for (int j = 0; j < 1997; j++) {
								if (t[i][j + 1] - t[i][j] == a
										&& t[i][j + 2] - t[i][j + 1] == b
										&& t[i][j + 3] - t[i][j + 2] == c
										&& t[i][j + 4] - t[i][j + 3] == d) {
									all += t[i][j + 4];
									break;
								}
							}
						}
						if (maxBananas < all) maxBananas = all;
					}
				}
			}
		}
		S.o(maxBananas);
	}
}
