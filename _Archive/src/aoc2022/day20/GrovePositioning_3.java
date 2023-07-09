package aoc2022.day20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GrovePositioning_3 {

	private static final long KEY = 1; //811589153;
	private static final int M = 1; //10;
	
	public static void main(String[] args) throws IOException {
		List<Pair> list = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader("input2.txt"))) {
			int index = 0;
			String line;
			while ((line = reader.readLine()) != null) {
				list.add(new Pair(index++, KEY * Long.parseLong(line)));
			}
		}

		int n = list.size();
		for (int m = 0; m < M; m++) {
			for (int i = 0; i < n; i++) {
				int id = 0;
				for (int j = 0; j < n; j++) if (list.get(j).a == i) id = j;
				Pair p = list.remove(id);
				list.add(Math.floorMod(id + p.b, n - 1), p);
			}
		}
		for (int i = 0; i < n; i++) if (list.get(i).b == 0) 
			System.out.println(list.get(Math.floorMod(i + 1000, n)).b
					+ list.get(Math.floorMod(i + 2000, n)).b
					+ list.get(Math.floorMod(i + 3000, n)).b);
	}

	public static record Pair(int a, long b) {}
}
