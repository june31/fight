package aoc2022.day02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class RockPaperScissors_1 {

	static Map<String, Integer> rules = Map.of(
			"A X", 4,
			"A Y", 8,
			"A Z", 3,
			"B X", 1,
			"B Y", 5,
			"B Z", 9,
			"C X", 7,
			"C Y", 2,
			"C Z", 6
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
