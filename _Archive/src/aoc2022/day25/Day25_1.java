package aoc2022.day25;

import java.util.ArrayList;
import java.util.List;

import tools.scanner.Scan;

public class Day25_1 {
	
	static { Scan.open("input2.txt"); }
	
	public static void main(String[] args) {
		long sum = 0;
		for (String s : Scan.readRawLines()) {
			long mul = 1;
			char[] cs = s.toCharArray();
			for (int i = cs.length - 1; i >= 0; i--) {
				sum += v(cs[i]) * mul;
				mul *= 5;
			}
		}
		System.out.println("Sum: " + sum);
		
		List<Character> chars = new ArrayList<>();
		while (sum > 0) {
			int i = Math.floorMod(sum, 5);
			if (i < 3) chars.add((char) ('0' + i));
			else {
				sum += 5;
				if (i == 3) chars.add('=');
				else chars.add('-');
			}
			sum /= 5;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = chars.size() - 1; i >= 0; i--) sb.append(chars.get(i));
		System.out.println(sb);
	}
	
	private static int v(char c) {
		if (c == '=') return -2;
		else if (c == '-') return -1;
		return c - '0';
	}
}
