package aoc;

import tools.scanner.Scan;

public class Day_01 {
	
	public static void main(String[] args) {
		String[] input = Scan.readRawStrings();
		
		// Star 1
		int s = 0;
		for (String line: input) {
			char[] chars = line.replaceAll("\\D", "").toCharArray();
			s += Integer.parseInt(chars[0] + "" + chars[chars.length - 1]);
		}
		System.out.println(s);
		
		// Star 2
		s = 0;
		for (String line: input) {
			for (int i = 1; i <= line.length(); i++) {
				var e = getDigit(line.substring(0, i));
				if (e != null) {
					s += e * 10;
					break;
				}
			}
			for (int i = line.length() - 1; i >= 0; i--) {
				var e = getDigit(line.substring(i));
				if (e != null) {
					s += e;
					break;
				}
			}
		}
		System.out.println(s);
	}
	
	private static Integer getDigit(String s) {
		s = s.replace("one", "1")
			 .replace("two", "2")
			 .replace("three", "3")
			 .replace("four", "4")
			 .replace("five", "5")
			 .replace("six", "6")
			 .replace("seven", "7")
			 .replace("eight", "8")
			 .replace("nine", "9")
			 .replaceAll("\\D", "");
		if (s.isEmpty()) return null;
		return Integer.parseInt(s);
	}
}
