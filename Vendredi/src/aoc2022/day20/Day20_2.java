package aoc2022.day20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day20_2 {

	private static final long KEY = 811589153;
	private static Pair[] t;
	private static Pair[] t2;
	private static int n;

	public static void main(String[] args) throws IOException {
		List<Pair> list = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader("input2.txt"))) {
			int index = 0;
			String line;
			while ((line = reader.readLine()) != null) {
				list.add(new Pair(index++, KEY * Long.parseLong(line)));
			}
		}

		t = list.toArray(new Pair[0]);
		n = t.length;
		t2 = new Pair[n - 1];

		for (int m = 0; m < 10; m++) {
			for (int i = 0; i < n; i++) {
				int id = seekIndex(i);
				remove(id);
				insert(t[id], c2(id + t[id].b));
			}
		}
		int zId = seekZero();
		System.out.println(t[c(zId + 1000)].b + t[c(zId + 2000)].b + t[c(zId + 3000)].b);
	}

	private static void insert(Pair pair, int pos) {
		for (int i = 0; i < pos; i++) t[i] = t2[i];
		t[pos] = pair;
		for (int i = pos + 1; i < n; i++) t[i] = t2[i - 1];
	}

	private static void remove(int id) {
		for (int i = 0; i < id; i++) t2[i] = t[i];
		for (int i = id+1; i < n; i++) t2[i-1] = t[i];
	}

	private static int seekIndex(int id) {
		for (int i = 0; i < n; i++) if (t[i].a == id) return i;
		return -1;
	}

	private static int seekZero() {
		for (int i = 0; i < n; i++) if (t[i].b == 0) return i;
		return -1;
	}

	private static int c(long i) {
		int e = (int) (i % n);
		if (e < 0) return e + n;
		return e;
	}	

	private static int c2(long i) {
		int e = (int) (i % (n-1));
		if (e < 0) return e + n - 1;
		return e;
	}	

	public static record Pair(int a, long b) {
		public String toString() {
			return "" + b;
		}
	} 
}
