package aoc.done;

import tools.scanner.Scan;

public class Day_02 {
	
	public static void main(String[] args) {
		String[] input = Scan.readRawStrings();
		
		int s = 0;
		
		// Star 1
		Loop: for (String in: input) {
			String[] t = in.split(": ");
			int id = Integer.parseInt(t[0].split(" ")[1]);
			String[] balls = t[1].split(", |; ");
			for (String ball: balls) {
				String[] t2 = ball.split(" ");
				int v = Integer.parseInt(t2[0]);
				if (v > 12 && t2[1].equals("red")) continue Loop; 
				if (v > 13 && t2[1].equals("green")) continue Loop; 
				if (v > 14 && t2[1].equals("blue")) continue Loop;
			}
			s += id;
		}
		System.out.println(s);
		
		// Star 2
		s = 0;
		for (String in: input) {
			String[] t = in.split(": ");
			String[] balls = t[1].split(", |; ");
			int maxR = 0; 
			int maxG = 0; 
			int maxB = 0; 
			for (String ball: balls) {
				String[] t2 = ball.split(" ");
				int v = Integer.parseInt(t2[0]);
				if (t2[1].equals("red")) if (v > maxR) maxR = v; 
				if (t2[1].equals("green")) if (v > maxG) maxG = v; 
				if (t2[1].equals("blue")) if (v > maxB) maxB = v;
			}
			s += maxR * maxG * maxB;
		}
		System.out.println(s);
	}
}
