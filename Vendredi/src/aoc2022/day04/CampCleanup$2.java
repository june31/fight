package aoc2022.day04;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CampCleanup$2 {
	public static void main(String[] args) throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader("input2.txt"))) {
			int score = 0;
			String line;
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split(",");
				String[] left = tokens[0].split("-");
				int l1 = Integer.parseInt(left[0]);
				int l2 = Integer.parseInt(left[1]);
				String[] right = tokens[1].split("-");
				int r1 = Integer.parseInt(right[0]);
				int r2 = Integer.parseInt(right[1]);
				if ((l1 <= r1 && l2 >= r2) || (l1 >= r1 && l2 <= r2)) score++;
			}
			System.out.println(score);
		}
	}
}
