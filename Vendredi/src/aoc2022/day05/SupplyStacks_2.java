package aoc2022.day05;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SupplyStacks_2 {

	static String[] strings;
	static int n;

	public static void main(String[] args) throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader("input2.txt"))) {
			one(reader);
			two(reader);
		}
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < n; i++) builder.append(strings[i].charAt(0));
		System.out.println(builder);
	}

	private static void one(BufferedReader reader) throws IOException {
		while (true) {
			String line = reader.readLine();
			if (line.startsWith(" 1 ")) {
				reader.readLine();
				return;
			}
			if (strings == null) {
				n = (line.length() + 1) / 4;
				strings = new String[n];
				for (int i = 0; i < n; i++) strings[i] = "";
			}
			for (int i = 0; i < n; i++) {
				char c = line.charAt(i * 4 + 1);
				if (c != ' ') strings[i] = strings[i] + c;
			}

		}
	}

	private static void two(BufferedReader reader) throws IOException {
		String line;
		while ((line = reader.readLine()) != null) {
			String[] tokens = line.split(" ");
			int move = Integer.parseInt(tokens[1]);
			int from = Integer.parseInt(tokens[3]) - 1;
			int to = Integer.parseInt(tokens[5]) - 1;
			String m = strings[from].substring(0, move);
			strings[from] = strings[from].substring(move);
			strings[to] = m + strings[to];
		}
	}
}
