package aoc2022.day03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RucksackReorg$2 {
	public static void main(String[] args) throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader("input2.txt"))) {
			int score = 0;
			String line;
			while ((line = reader.readLine()) != null) {
				List<Byte> l1 = new ArrayList<>();
				for (byte b : line.getBytes()) l1.add(b);
				List<Byte> l2 = new ArrayList<>();
				for (byte b : reader.readLine().getBytes()) l2.add(b);
				List<Byte> l3 = new ArrayList<>();
				for (byte b : reader.readLine().getBytes()) l3.add(b);
				byte b = l1.stream().filter(l2::contains).filter(l3::contains).findFirst().get();
				if (b < 96) score += 26;
				score += b & 31;
			}
			System.out.println(score);
		}
	}
}
