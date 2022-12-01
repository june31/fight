package aoc2022.day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CalorieCounting$1 {

	public static void main(String[] args) throws IOException {
		int sum = 0;
		int max = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				line = line.strip();
				if (line.isEmpty()) {
					if (sum > max) max = sum;
					sum = 0;
				} else sum += Integer.parseInt(line);
			}
		}
		if (sum > max) max = sum;
		System.out.println(max);
	}
}
