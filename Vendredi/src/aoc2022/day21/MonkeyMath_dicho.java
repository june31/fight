package aoc2022.day21;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import tools.dichotomy.Search;

public class MonkeyMath_dicho {

	private static Map<String, M> mkys = new LinkedHashMap<>();
	private static M root = null;
	private static M humn = null;

	public static void main(String[] args) throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader("input3.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				M m = new M();
				m.name = line.substring(0, 4);
				m.line = line;
				if (line.length() == 17) {
					m.m1L = line.substring(6, 10);
					m.op = line.charAt(11);
					m.m2L = line.substring(13);
				} else m.val = Long.parseLong(line.substring(6));
				if (m.name.equals("root")) { root = m; m.line = m.line.replace('+', '='); }
				if (m.name.equals("humn")) humn = m;
				mkys.put(m.name, m);
			}
		}

		for (M m : mkys.values()) {
			if (m.m1L != null) m.m1 = mkys.get(m.m1L);
			if (m.m2L != null) m.m2 = mkys.get(m.m2L);
		}

		humn.val = 0l;
		long v1 = calc(root.m1);
		humn.val = 2_000_000l;
		long v2 = calc(root.m1);
		long ref;
		M unknown;
		if (v1 == v2) {
			ref = v1;
			unknown = root.m2;
		} else {
			ref = calc(root.m2);
			unknown = root.m1;
		}

		System.out.println(Search.reachLongLow(ref, x -> {
			humn.val = x;
			return calc(unknown);
		}));

		System.out.println(Search.reachLongHigh(ref, x -> {
			humn.val = x;
			return calc(unknown);
		}));

		for (long i = 300; i < 305; i++) {
			humn.val = i;
			System.out.println(i + " -> " + calc(unknown));
		}
	}

	private static long calc(M monkey) {
		for (M m : mkys.values()) if (m.op != 0) m.val = null;
		while (monkey.val == null) {
			for (M m : mkys.values()) {
				if (m.val == null) {
					if (m.m1.val != null) {
						if (m.m2.val != null) {
							if (m.op == '+') m.val = m.m1.val + m.m2.val;
							else if (m.op == '-') m.val = m.m1.val - m.m2.val;
							else if (m.op == '*') m.val = m.m1.val * m.m2.val;
							else m.val = m.m1.val / m.m2.val;
						}
					}
				}
			}
		}
		return monkey.val;
	}
	public static class M {
		String name;
		String line;
		String m1L;
		String m2L;
		M m1;
		M m2;
		char op;
		Long val;
		int use = 0;
		public String toString() { return line; }
	}
}
