package aoc2022.day15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Sensors_1 {
	public static void main(String[] args) throws IOException {
		int l = 2000000;
		int[] scan = new int[10_000_000];
		int skew = 1_000_000;
		List<Integer> xs = new ArrayList<>();
		List<Integer> ys = new ArrayList<>();
		List<Integer> xb = new ArrayList<>();
		List<Integer> yb = new ArrayList<>();
		List<Integer> dists = new ArrayList<>();
		int n = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader("input2.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] toks = line.substring(12).split(": closest beacon is at x=");
				String[] sensorToks = toks[0].split(", y=");
				String[] beaconToks = toks[1].split(", y=");
				xs.add(Integer.parseInt(sensorToks[0]) + skew);
				ys.add(Integer.parseInt(sensorToks[1]));
				xb.add(Integer.parseInt(beaconToks[0]) + skew);
				yb.add(Integer.parseInt(beaconToks[1]));
				dists.add(manhattan(xs.get(n), ys.get(n), xb.get(n), yb.get(n)));
				n++;
			}
		}
		
		for (int i = 0; i < n; i++) {
			int x = xs.get(i);
			int y = ys.get(i);
			int d = dists.get(i);
			for (int j = x - d + Math.abs(l - y); j <= x + d - Math.abs(l - y); j++) scan[j] = 1;
		}

		for (int i = 0; i < n; i++) {
			if (yb.get(i) == l) scan[xb.get(i)] = 0;
		}

		int k = 0;
		for (int i = 0; i < scan.length; i++) k += scan[i];
		System.out.println(k);
	}

	private static Integer manhattan(int x1, int y1, int x2, int y2) {
		return Math.abs(x2 - x1) + Math.abs(y2 - y1);
	}
}
