package aoc2022.day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day13_1 {
	public static void main(String[] args) throws IOException {
		List<L> l1s = new ArrayList<>();
		List<L> l2s = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader("input2.txt"))) {
			int score = 0;
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.isBlank()) continue;
				l1s.add(retrieveL(line));
				l2s.add(retrieveL(reader.readLine()));
			}
			
			int n = l1s.size();
			for (int i = 0; i < n; i++) {
				Boolean b = comp(l1s.get(i), l2s.get(i));
				if (b == null || b) score += i + 1;
			}
			System.out.println(score);
		}
	}
	
	private static Boolean comp(L l1, L l2) {
		if (l1.children != null) {
			if (l2.children == null) {
				if (l2.values != null) {
					L l3 = new L();
					l3.children = new ArrayList<>();
					l3.children.add(l2);
					return comp(l1, l3);
				}
				return false;
			}
			for (int i = 0; i < l1.children.size(); i++) {
				if (i == l2.children.size()) return false;
				Boolean b = comp(l1.children.get(i), l2.children.get(i));
				if (b != null) return b;
			}
			if (l1.children.size() < l2.children.size()) return true;
		} else {
			if (l2.children != null) {
				if (l1.values != null) {
					L l3 = new L();
					l3.children = new ArrayList<>();
					l3.children.add(l1);
					return comp(l3, l2);
				}
				return true;
			}
			if (l1.values == null && l2.values == null) return null;
			if (l1.values == null) return true;
			if (l2.values == null) return false;
			for (int i = 0; i < l1.values.size(); i++) {
				if (i == l2.values.size()) return false;
				if (l1.values.get(i) < l2.values.get(i)) return true;
				if (l1.values.get(i) > l2.values.get(i)) return false;
			}
			if (l1.values.size() < l2.values.size()) return true;
		}
		return null;
	}

	private static L retrieveL(String s) {
		L l = new L();
		if (s.startsWith("[")) {
			if (s.equals("[]")) return l;
			s = s.substring(1, s.length() - 1);
			if (s.indexOf('[') == -1) {
				l.values = new ArrayList<>();
				for (String t : s.split(",")) {
					l.values.add(Integer.parseInt(t));
				}
			} else {
				l.children = new ArrayList<>();
				for (String t : split2(s)) {
					l.children.add(retrieveL(t));
				}
			}
		} else {
			l.values = new ArrayList<>();
			l.values.add(Integer.parseInt(s));
		}
		return l;
	}
	
	private static List<String> split2(String t) {
		List<String> l = new ArrayList<>();
		int index = 0;
		StringBuilder b = new StringBuilder();
		for (char c : t.toCharArray()) {
			if (c == ',' && index == 0) {
				l.add(b.toString());
				b = new StringBuilder();
				continue;
			}
			if (c == '[') index++;
			if (c == ']') index--;
			b.append(c);
		}
		if (!b.toString().isBlank()) l.add(b.toString());
		return l;
	}

	private static class L {
		List<Integer> values;
		List<L> children;
	}
}
