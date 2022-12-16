package aoc2022.day15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tools.chrono.Chrono;

public class Day15_2 {

	//private static int limit = 4_000_000;
	private static int limit = 20;

	public static void main(String[] args) throws IOException {
		Chrono.start();
		List<Integer> xs = new ArrayList<>();
		List<Integer> ys = new ArrayList<>();
		List<Integer> xb = new ArrayList<>();
		List<Integer> yb = new ArrayList<>();
		List<Integer> dists = new ArrayList<>();
		int n = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] toks = line.substring(12).split(": closest beacon is at x=");
				String[] sensorToks = toks[0].split(", y=");
				String[] beaconToks = toks[1].split(", y=");
				xs.add(Integer.parseInt(sensorToks[0]));
				ys.add(Integer.parseInt(sensorToks[1]));
				xb.add(Integer.parseInt(beaconToks[0]));
				yb.add(Integer.parseInt(beaconToks[1]));
				dists.add(manhattan(xs.get(n), ys.get(n), xb.get(n), yb.get(n)));
				n++;
			}
		}
		
		Map<Long, Integer> map = new HashMap<>();
		
		for (int i = 0; i < n; i++) {
			int x = xs.get(i);
			int y = ys.get(i);
			int d = dists.get(i) + 1;
			for (int j = 0; j < d; j++) {
				incMap(map, x + j, y + d - j);
				incMap(map, x + d - j, y - j);
				incMap(map, x - j, y - d + j);
				incMap(map, x - d + j, y + j);
			}
		}

		for (var e : map.entrySet())
			if (e.getValue() >= 4) {
				int[] scan = new int[limit];
				for (int i = 0; i < n; i++) {
					int x = xs.get(i);
					int y = ys.get(i);
					int d = dists.get(i);
					int l = (int) (e.getKey() % limit);
					int min = Math.max(0, x - d + Math.abs(l - y));
					int max = Math.min(limit - 1, x + d - Math.abs(l - y));
					for (int j = min; j <= max; j++) scan[j] = 1;
				}
				int k = 0;
				for (int i = 0; i < scan.length; i++) k += scan[i];
				System.out.println(e.getKey() + " - " + e.getValue() + " - " + k);
			}
		Chrono.stop();
	}

	private static void incMap(Map<Long, Integer> map, int x, int y) {
		if (x < 0 || y < 0 || x > limit || y > limit) return;
		long l = 4_000_000l * x + y;
		Integer v = map.get(l);
		if (v == null) map.put(l, 1); else map.put(l, v + 1);
	}

	private static Integer manhattan(int x1, int y1, int x2, int y2) {
		return Math.abs(x2 - x1) + Math.abs(y2 - y1);
	}
}
