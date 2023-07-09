package aoc2022.day21;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class MonkeyMath_1 {
	
	public static void main(String[] args) throws IOException {
		Map<String, M> mkys = new LinkedHashMap<>();
		try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				M m = new M();
				m.line = line;
				if (line.length() == 17) {
					m.m1 = line.substring(6, 10);
					m.op = line.charAt(11);
					m.m2 = line.substring(13);
				} else m.result = Long.parseLong(line.substring(6));
				mkys.put(line.substring(0, 4), m);
			}
		}
		
		M root = mkys.get("root");
		while (root.result == null) {
			for (M m : mkys.values()) {
				if (m.result == null) {
					M m1 = mkys.get(m.m1);
					if (m1.result != null) {
						M m2 = mkys.get(m.m2);
						if (m2.result != null) {
							if (m.op == '+') m.result = m1.result + m2.result;
							else if (m.op == '-') m.result = m1.result - m2.result;
							else if (m.op == '*') m.result = m1.result * m2.result;
							else m.result = m1.result / m2.result;
						}
					}
				}
			}
		}
		System.out.println(root.result);
	}
	
	public static class M {
		String line;
		String m1;
		String m2;
		char op;
		Long result;
		public String toString() { return line; }
	}
}
