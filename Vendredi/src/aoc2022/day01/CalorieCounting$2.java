package aoc2022.day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import tools.Leaderboard;

public class CalorieCounting$2 {

	public static void main(String[] args) throws IOException {
		int sum = 0;
		Leaderboard<Integer> board = new Leaderboard<>(3);
		try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				line = line.strip();
				if (line.isEmpty()) {
					board.add(sum, sum);
					sum = 0;
				} else sum += Integer.parseInt(line);
			}
		}
		if (sum > 0) board.add(sum, sum);
		System.out.println(board.copyToList().stream().mapToInt(Integer::intValue).sum());
	}
}
