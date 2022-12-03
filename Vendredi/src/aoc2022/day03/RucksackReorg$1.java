package aoc2022.day03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RucksackReorg$1 {
	public static void main(String[] args) throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
			int score = 0;
			String line;
			while ((line = reader.readLine()) != null) {
				byte[] left = Arrays.copyOfRange(line.getBytes(), 0, line.length() / 2);
				byte[] right = Arrays.copyOfRange(line.getBytes(), line.length() / 2, line.length());
				List<Byte> l = new ArrayList<>();
				for (Byte b : left) l.add(b);
				List<Byte> r = new ArrayList<>();
				for (Byte b : right) r.add(b);
				byte b = l.stream().filter(r::contains).findFirst().get();
				if (b < 96) score += 26;
				score += b & 31;
			}
			System.out.println(score);
		}
	}
}
