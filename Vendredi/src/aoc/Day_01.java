package aoc;

import tools.collections.int32.L;
import tools.scanner.Scan;

public class Day_01 {
	
	private static final String[] FIGS = { "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };

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
			int min = -1;
			do {
				L l = new L();
				for (int i = 1; i <= 9; i++) l.add(line.indexOf(FIGS[i - 1]));
				min = l.map(x -> x == -1 ? Integer.MAX_VALUE : x).minII().index; 
				if (min >= 0) line = line.replaceFirst(FIGS[min], "" + (min + 1));
			} while (min >= 0);
			char[] chars = line.replaceAll("\\D", "").toCharArray();
			//System.err.println(chars);
			s += Integer.parseInt(chars[0] + "" + chars[chars.length - 1]);
		}
		System.out.println(s);
		
		// Star 2+
		s = 0;
		for (String line: input) {
			L l = new L();
			String l1 = line;
			for (int i = 1; i <= 9; i++) l.add(l1.indexOf(FIGS[i - 1]));
			int min1 = l.map(x -> x == -1 ? Integer.MAX_VALUE : x).minII().index; 
			if (min1 >= 0) l1 = l1.replaceFirst(FIGS[min1], "" + (min1 + 1));
			s += (l1.replaceAll("\\D", "").charAt(0) - '0') * 10;;

			String l2 = line;
			for (int i = 1; i <= 9; i++) l.add(l2.lastIndexOf(FIGS[i - 1]));
			int min2 = l.maxII().index; 
			if (min2 >= 0) l2 = l2.replaceFirst(FIGS[min2], "" + (min2 + 1));
			s += (l2.replaceAll("\\D", "").charAt(0) - '0') * 10;;
		
			
			s += Integer.parseInt(chars[0] + "" + chars[chars.length - 1]);
		}
		System.out.println(s);

	}
}
