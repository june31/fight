package aoc2022.day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day13_2 {
	public static void main(String[] args) throws IOException {
		List<L> ls = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader("input2.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.isBlank()) continue;
				ls.add(retrieveL(line));
			}
			L l2 = retrieveL("[[2]]");
			L l6 = retrieveL("[[6]]");
			ls.add(l2);
			ls.add(l6);

			ls.sort((x, y) -> {
				Boolean b = comp(x, y);
				if (b == null) return 0;
				if (b) return -1;
				return 1;
			});
			
			System.out.println((ls.indexOf(l2) + 1) * (ls.indexOf(l6) + 1));
		}
	}
	
	private static Boolean comp(L l1, L l2) {
		if (l1.children != null) {
			if (l2.children == null) {
				if (l2.value != null) {
					L l3 = new L();
					l3.value = l2.value;
					L l4 = new L();
					l4.children = new ArrayList<>();
					l4.children.add(l3);
					return comp(l1, l4);
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
				if (l1.value != null) {
					L l3 = new L();
					l3.value = l1.value;
					L l4 = new L();
					l4.children = new ArrayList<>();
					l4.children.add(l3);
					return comp(l4, l2);
				}
				return true;
			}
			if (l1.value == null && l2.value == null) return null;
			if (l1.value == null) return true;
			if (l2.value == null) return false;
			if (l1.value < l2.value) return true;
			if (l1.value > l2.value) return false;
		}
		return null;
	}

	private static L retrieveL(String s) {
		L l = new L();
		if (s.startsWith("[")) {
			String s2 = s.substring(1, s.length() - 1);
			l.children = new ArrayList<>();
			var tokens = split2(s2);
			for (String t : tokens) {
				l.children.add(retrieveL(t));
			}
		} else {
			l.value = Integer.parseInt(s);
		}
		return l;
	}
	
	private static List<String> split2(String t) {
		List<String> l = new ArrayList<>();
		int index = 0;
		StringBuilder b = new StringBuilder();
		char[] cc = t.toCharArray();
		String s;
		for (int i = 0; i < cc.length; i++) {
			char c = cc[i];
			if (c == ',' && index == 0) {
				s = b.toString();
				if (s.endsWith(",")) s = s.substring(0, s.length() - 1);
				l.add(s);
				b = new StringBuilder();
				continue;
			}
			if (c == '[') index++;
			if (c == ']') index--;
			b.append(c);
		}
		s = b.toString();
		if (s.endsWith(",")) s = s.substring(0, s.length() - 1);
		if (!s.isBlank()) l.add(s);
		return l;
	}

	private static class L {
		Integer value;
		List<L> children;
		
		@Override
		public String toString() {
			if (value != null) return "" + value;
			else return children.stream().map(x -> "" + x).collect(Collectors.joining(",", "[", "]"));
		}
	}
}
