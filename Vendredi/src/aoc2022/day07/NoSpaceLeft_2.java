package aoc2022.day07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NoSpaceLeft_2 {
	public static void main(String[] args) throws IOException {
		long threshold = 30_000_000 + 49_199_225 - 70_000_000;
		long min = Long.MAX_VALUE;
		try (BufferedReader reader = new BufferedReader(new FileReader("input2.txt"))) {
			List<Long> paths = new ArrayList<>();
			paths.add(0l);
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.equals("$ cd /") || line.equals("$ ls") || line.startsWith("dir ")) continue;
				if (line.equals("$ cd ..")) {
					long s = paths.remove(paths.size() - 1);
					if (s >= threshold && s < min) min = s; 
				} else if (line.startsWith("$ cd ")) {
					paths.add(0l);
				} else {
					long size = Long.parseLong(line.split(" ")[0]);
					List<Long> newPaths = new ArrayList<>();
					for (long s : paths) newPaths.add(s + size);
					paths = newPaths;
				}
			}
			for (long s : paths) if (s >= threshold && s < min) min = s; 
			System.out.println(min);
		}
	}
}
