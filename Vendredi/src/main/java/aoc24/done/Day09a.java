package aoc24.done;

import tools.collections.int32.L;
import tools.scanner.Scan;
import tools.strings.S;

public class Day09a {
	public static void main(String[] args) {
		var s = Scan.readLine() + "0";
		var l = new L();
		int id = 0;
		for (int i = 0; i < s.length(); i+=2) {
			int a = s.charAt(i) - '0';
			int b = s.charAt(i+1) - '0';
			for (int j = 0; j < a; j++) {
				l.add(id);
			}
			for (int j = 0; j < b; j++) {
				l.add(-1);
			}
			if (a!=0) id++;
		}
		int e = l.size() - 1;
		for (int i = 0; i < l.size(); i++) {
			int a = l.get(i);
			if (a == -2) break;
			if (a == -1) {
				if (l.get(e) == -2) S.o("huh");
				while (l.get(e) == -1) {
					l.set(e, -2);
					e--;
				}
				if (i < e) {
					int b = l.get(e);
					l.set(i, b);
					l.set(e, -2);
					e--;
				}
			}
		}
		long z = 0;
		//S.o(l.join("\n"));
		for (int i = 0; i < l.size(); i++) {
			long a = l.get(i);
			if (a == -2) break;
			if (a < 0) S.o("error");
			z += i*a;
		}
		S.o(z);
	}
}
