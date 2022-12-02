package aoc2022.day02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class RockPaperScissors$2 {

	static Map<String, Integer> rules = Map.of(
			"A X", 0+3,
			"A Y", 3+1,
			"A Z", 6+2,
			"B X", 0+1,
			"B Y", 3+2,
			"B Z", 6+3,
			"C X", 0+2,
			"C Y", 3+3,
			"C Z", 6+1
			);
	
	public static void main(String[] args) throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
			int score = 0;
			String line;
			while ((line = reader.readLine()) != null) {
				score += rules.get(line);
			}
			System.out.println(score);
		}
	}
}
