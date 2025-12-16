package aoc2025;

import java.util.HashMap;
import java.util.Map;

import tools.chrono.Chrono;
import tools.collections.int32.L;
import tools.collections.multi.LLi;
import tools.collections.string.Ls;
import tools.scanner.list.ScanLs;
import tools.strings.S;

public class Day10_2 {
	Map<L, Integer> memo = new HashMap<>();
	
	void main(String[] args) {
		Chrono.start();
		long z = 0;
		Ls in = ScanLs.readRaw();
		for (String s: in) {
			var tks = s.split(" ");
			L x = new L(tks[tks.length -1]);
			LLi sw = new LLi();
			for (int i = 1; i < tks.length - 1; i++) {
				sw.add(new L(tks[i]));
			}
			S.o("---- ", x, sw);
			z += calc(x, sw, 0);
		}
		S.o(z);
		Chrono.stop();
	}
	private int calc(L x, LLi sw, int depth) {
		int sum = 0;
		for (int i: x) {
			if (i < 0) return 1_000_000;
			sum += i;
		}
		if (sum == 0) {
			return 0;
		}
		// Nombre le moins représenté
		int[] rep = new int[x.size()];
		for (L l: sw) for (int i: l) rep[i]++;
		int min = Integer.MAX_VALUE;
		int res = -1;
		for (int i = 0; i < rep.length; i++) {
			if (rep[i] > 0 && rep[i] < min) {
				min = rep[i];
				res = i;
			}
		}
		int fres = res;
		if (res == -1) {
			//S.o("Fail");
			return 1_000_000;
		}
		LLi filtered = sw.filtered(l -> l.contains(fres));
		LLi others = sw.filtered(l -> !l.contains(fres));
		//S.o(fres, x, filtered, depth);
		
		int v = x.get(fres);
		L y;
		switch (filtered.size()) {
		case 0:
			return 1_000_000;
		case 1: 
			y = x.copy();
			for (int i: filtered.first()) {
				y.set(i, y.get(i) - v);
			}
			return v + calc(y, others, depth + 1); 
		case 2:
			min = Integer.MAX_VALUE;
			for (int i = 0; i <= v; i++) {
				y = x.copy();
				for (int j: filtered.first()) {
					y.set(j, y.get(j) - i);
				}
				for (int j: filtered.last()) {
					y.set(j, y.get(j) - (v - i));
				}
				int cv = calc(y, others, depth + 1);
				if (cv < min) min = cv;
			}
			return v + min;
		case 3:
			min = Integer.MAX_VALUE;
			for (int i = 0; i <= v; i++) {
				for (int j = 0; j <= v - i; j++) {
					y = x.copy();
					for (int k: filtered.get(0)) {
						y.set(k, y.get(k) - i);
					}
					for (int k: filtered.get(1)) {
						y.set(k, y.get(k) - j);
					}
					for (int k: filtered.get(2)) {
						y.set(k, y.get(k) - (v - i - j));
					}
					int cv = calc(y, others, depth + 1);
					if (cv < min) min = cv;
				}
			}
			return v + min;
		case 4:
			min = Integer.MAX_VALUE;
			for (int i = 0; i <= v; i++) {
				for (int j = 0; j <= v - i; j++) {
					for (int k = 0; k <= v - i - j; k++) {
						y = x.copy();
						for (int l: filtered.get(0)) {
							y.set(l, y.get(l) - i);
						}
						for (int l: filtered.get(1)) {
							y.set(l, y.get(l) - j);
						}
						for (int l: filtered.get(2)) {
							y.set(l, y.get(l) - k);
						}
						for (int l: filtered.get(3)) {
							y.set(l, y.get(l) - (v - i - j - k));
						}
						int cv = calc(y, others, depth + 1);
						if (cv < min) min = cv;
					}
				}
			}
			return v + min;
		case 5:
			S.o("5 swappers");
			min = Integer.MAX_VALUE;
			for (int i = 0; i <= v; i++) {
				for (int j = 0; j <= v - i; j++) {
					for (int k = 0; k <= v - i - j; k++) {
						for (int m = 0; m <= v - i - j - k; m++) {
							y = x.copy();
							for (int l: filtered.get(0)) {
								y.set(l, y.get(l) - i);
							}
							for (int l: filtered.get(1)) {
								y.set(l, y.get(l) - j);
							}
							for (int l: filtered.get(2)) {
								y.set(l, y.get(l) - k);
							}
							for (int l: filtered.get(3)) {
								y.set(l, y.get(l) - m);
							}
							for (int l: filtered.get(4)) {
								y.set(l, y.get(l) - (v - i - j - k - m));
							}
							int cv = calc(y, others, depth + 1);
							if (cv < min) min = cv;
						}
					}
				}
			}
			return v + min;
		case 6 :
			S.o("6 swappers");
			min = Integer.MAX_VALUE;
			for (int i = 0; i <= v; i++) {
				for (int j = 0; j <= v - i; j++) {
					for (int k = 0; k <= v - i - j; k++) {
						for (int m = 0; m <= v - i - j - k; m++) {
							for (int n = 0; n <= v - i - j - k - m; n++) {
								y = x.copy();
								for (int l: filtered.get(0)) {
									y.set(l, y.get(l) - i);
								}
								for (int l: filtered.get(1)) {
									y.set(l, y.get(l) - j);
								}
								for (int l: filtered.get(2)) {
									y.set(l, y.get(l) - k);
								}
								for (int l: filtered.get(3)) {
									y.set(l, y.get(l) - m);
								}
								for (int l: filtered.get(4)) {
									y.set(l, y.get(l) - n);
								}
								for (int l: filtered.get(5)) {
									y.set(l, y.get(l) - (v - i - j - k - m - n));
								}
								int cv = calc(y, others, depth + 1);
								if (cv < min) min = cv;
							}
						}
					}
				}
			}
			return v + min;
		default:
			S.o("Too many swappers:", filtered.size());
			throw new RuntimeException("Too many swappers");
		}
	}
}