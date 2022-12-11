package aoc2022.day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MonkeysInTheMiddle_2 {
	public static void main(String[] args) throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader("input2.txt"))) {
			String line;
			int n = 0;
			List<Singe> monkeys = new ArrayList<>();
			Singe m = null;
			long divisor = 1;
			while ((line = reader.readLine()) != null) {
				line = line.strip();
				if (line.startsWith("Monkey")) {
					m = new Singe();
					monkeys.add(m);
					n++;
				} else if (line.startsWith("Starting")) {
					String[] items = line.substring(16).split(", ");
					for (String i : items) m.items.add(Long.parseLong(i));
				} else if (line.startsWith("Operation")) {
					m.op = line.substring(17);
				} else if (line.startsWith("Test")) {
					m.div = Integer.parseInt(line.substring(19));
					divisor *= m.div;
				} else if (line.startsWith("If true")) {
					m.vrai = Integer.parseInt(line.substring(25));
				} else if (line.startsWith("If false")) {
					m.faux = Integer.parseInt(line.substring(26));
				}
			}
			
			for (int turn = 0; turn < 10000; turn++) {
				for (Singe monkey : monkeys) {
					for (long item : monkey.items) {
						monkey.inspect++;
						item = calc(monkey.op, item);
						item %= divisor;
						if (item % monkey.div == 0) monkeys.get(monkey.vrai).items.add(item);
						else monkeys.get(monkey.faux).items.add(item);
					}
					monkey.items.clear();
				}
			}
			
			List<Long> inspects = monkeys.stream().mapToLong(x -> x.inspect).sorted().boxed().collect(Collectors.toList());
			
			System.out.println(inspects.get(n - 1) * inspects.get(n - 2));
		}
	}

	private static long calc(String op, long old) {
		String[] items = op.split(" ");
		long a = items[2].equals("old") ? old : Integer.parseInt(items[2]);
		if (items[1].equals("+")) return old + a;
		return old * a;
	}
}

class Singe {
	List<Long> items = new ArrayList<>();
	String op;
	int div;
	int vrai;
	int faux;
	long inspect = 0;
}
