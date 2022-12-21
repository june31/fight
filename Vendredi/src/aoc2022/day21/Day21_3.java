package aoc2022.day21;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Day21_3 {

	public static void main(String[] args) throws IOException {
		Map<String, M> mkys = new LinkedHashMap<>();
		M root = null;
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
				} else m.result = Long.parseLong(line.substring(6));
				if (m.name.equals("root")) { root = m; m.line = m.line.replace('+', '='); }
				if (!m.name.equals("humn")) mkys.put(m.name, m);
			}
		}

		for (M m : mkys.values()) {
			if (m.m1L != null) m.m1 = mkys.get(m.m1L);
			if (m.m2L != null) m.m2 = mkys.get(m.m2L);
		}

		M curr = root;
		long res = 0;
		char op = '=';
		while (true) {
			while (curr.m1.result == null && curr.m2.result == null) {
				for (M m : mkys.values()) {
					try {
						if (m.result == null && m.m1.result != null && m.m2.result != null) {
							if (m.op == '+') m.result = m.m1.result + m.m2.result;
							else if (m.op == '-') m.result = m.m1.result - m.m2.result;
							else if (m.op == '*') m.result = m.m1.result * m.m2.result;
							else m.result = m.m1.result / m.m2.result;
						}
					} catch (NullPointerException ex) {
						// Human found
					}
				}
			}
			int opn = curr.m1.result == null ? 2 : 1;
			M mok = opn == 1 ? curr.m1 : curr.m2;
			M mko = opn == 2 ? curr.m1 : curr.m2;
			if (op == '=') {
				res = mok.result; 
			} else if (op == '+') {
				res -= mok.result;
			} else if (op == '-') {
				if (opn == 1) res = mok.result - res;
				else res += mok.result;
			} else if (op == '*') {
				res /= mok.result;
			} else if (op == '/') {
				if (opn == 1) res = mok.result / res;
				else res *= mok.result;
			}
			System.out.println(curr + " (" + opn + "); " + mok.name + "=" + mok.result + "; " + mko.name + " shall be " + res);
			curr = mko;
			if (curr.m1 == null || curr.m2 == null) {
				System.out.println(curr);
				if (curr.m1 == null) System.out.println(curr.m2.name + " value: " + curr.m2.result); // Need luck
				else System.out.println(curr.m1.name + " value: " + curr.m1.result);
				break;
			}
			op = mko.op;
		}
	}

	public static class M {
		String name;
		String line;
		String m1L;
		String m2L;
		M m1;
		M m2;
		char op;
		Long result;
		int use = 0;
		public String toString() { return line; }
	}
}
