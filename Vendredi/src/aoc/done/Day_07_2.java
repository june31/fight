package aoc.done;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

import tools.collections.int32.L;
import tools.scanner.Scan;

public class Day_07_2 {
	
	public static void main(String[] args) {
		String[] in = Scan.readRawStrings();
		var vals = new LinkedHashMap<String, Integer>();
		var hands = new ArrayList<String>();
		for (var l: in) {
			String[] t = l.split(" ");
			vals.put(t[0], Integer.parseInt(t[1]));
			hands.add(t[0]);
		}
		Collections.sort(hands, (x, y) -> compare(x, y));
		
		long s = 0l;
		int r = 1;
		for (String h: hands) s += vals.get(h) * r++;
		System.out.println(s);
	}
	
	private static int compare(String h1, String h2) {
		int v1 = pow(h1);
		int v2 = pow(h2);
		if (v1 < v2) return -1;
		if (v1 > v2) return 1;
		for (int i = 0; i < 5; i++) {
			char c1 = h1.charAt(i);
			char c2 = h2.charAt(i);
			if (val(c1) < val(c2)) return -1;
			if (val(c1) > val(c2)) return 1;
		}
		return 0;
	}

	private static int val(char c) {
		if (c == 'T') return 10;
		if (c == 'J') return 0;
		if (c == 'Q') return 12;
		if (c == 'K') return 13;
		if (c == 'A') return 14;
		return c - '0';
	}

	private static int pow(String h) {
		L l = new L(h.toCharArray());
		l = l.sortUp();
		L a = new L();
		int j = 0;
		int c = 1;
		int prev = -1;
		for (int i: l) {
			if (i != prev && prev != -1) {
				a.add(c);
				c = 1;
			}
			if (i == 'J') {
				j++;
				prev = -1;
				continue;
			}
			if (i == prev) c++;
			prev = i;
		}
		if (prev != -1) a.add(c);
		if (a.isEmpty()) a.add(0);
		a = a.sortDown();
		if (a.get(0) + j == 5) return 7;
		if (a.get(0) + j == 4) return 6;
		if (a.get(0) + j == 3 && a.get(1) == 2) return 5;
		if (a.get(0) + j == 3) return 4;
		if (a.get(0) + j == 2 && a.get(1) == 2) return 3;
		if (a.get(0) + j == 2) return 2;
		return 1;
	}
}
