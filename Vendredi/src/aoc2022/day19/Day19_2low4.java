package aoc2022.day19;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import tools.chrono.Chrono;
import tools.math.Num;

public class Day19_2low4 {

	public static final int D = 32;
	public static List<BP> bps = new ArrayList<>();
	
	public static void main(String[] args) throws Exception {
		Chrono.start();
		try (BufferedReader reader = new BufferedReader(new FileReader("input3.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] toks = line.split(" Each ");
				BP bp = new BP();
				bp.id = Integer.parseInt(toks[0].substring(10, toks[0].length() - 1));
				bp.a_a = Integer.parseInt(toks[1].split(" ")[3]);
				bp.b_a = Integer.parseInt(toks[2].split(" ")[3]);
				bp.c_a = Integer.parseInt(toks[3].split(" ")[3]);
				bp.c_b = Integer.parseInt(toks[3].split(" ")[6]);
				bp.d_a = Integer.parseInt(toks[4].split(" ")[3]);
				bp.d_c = Integer.parseInt(toks[4].split(" ")[6]);
				bp.maxA = Num.max(bp.b_a, bp.c_a, bp.d_a);
				bps.add(bp);
			}
		}
		
		List<Thread> ths = new ArrayList<>();
		for (BP bp : bps) {
			Thread t = new Thread(() -> {
				bp.score = recurse(bp, D, 1, 0, 0, 0, 0, 0, 0, 0);
				System.out.println(bp.id + ": " + bp.score);
			});
			ths.add(t);
			t.start();
		}
		for (Thread t : ths) t.join();
		
		int qual1 = 0;
		int qual2 = 1;
		for (BP bp : bps) { qual1 += bp.id * bp.score; qual2 *= bp.score; }
		System.out.println("Std Quality level: " + qual1);
		System.out.println("Mul Quality level: " + qual2);
		Chrono.stop();
	}
	
	private static int recurse(BP bp, int t, int na, int nb, int nc, int nd, int sa, int sb, int sc, int sd) {
		if (t == 0) return sd;
		int max = 0;
		if (na < bp.maxA && t >= 2) {
			int lackA = bp.a_a - sa;
			int tNeed = 1;
			while (lackA > 0) {
				lackA -= na;
				tNeed++;
			}
			if (tNeed < t - 1) {
				int s = recurse(bp, t - tNeed, na + 1, nb, nc, nd, sa + na*tNeed - bp.a_a, sb + nb*tNeed, sc + nc*tNeed, sd + nd*tNeed);
				if (s > max) max = s;
			}
		}
		if (nb < bp.c_b && t >= 4) {
			int lackA = bp.b_a - sa;
			int tNeed = 1;
			while (lackA > 0) {
				lackA -= na;
				tNeed++;
			}
			if (tNeed < t - 3) {
				int s = recurse(bp, t - tNeed, na, nb + 1, nc, nd, sa + na*tNeed - bp.b_a, sb + nb*tNeed, sc + nc*tNeed, sd + nd*tNeed);
				if (s > max) max = s;
			}
		}
		if (nb >= 1 && nc < bp.d_c && t >= 2) {
			int lackA = bp.c_a - sa;
			int lackB = bp.c_b - sb;
			int tNeed = 1;
			while (lackA > 0 || lackB > 0) {
				lackA -= na;
				lackB -= nb;
				tNeed++;
			}
			if (tNeed < t - 1) {
				int s = recurse(bp, t - tNeed, na, nb, nc + 1, nd, sa + na*tNeed - bp.c_a, sb + nb*tNeed - bp.c_b, sc + nc*tNeed, sd + nd*tNeed);
				if (s > max) max = s;
			}
		}
		if (nc >= 1 && t >= 1) {
			int lackA = bp.d_a - sa;
			int lackC = bp.d_c - sc;
			int tNeed = 1;
			while (lackA > 0 || lackC > 0) {
				lackA -= na;
				lackC -= nc;
				tNeed++;
			}
			if (tNeed < t) {
				int s = recurse(bp, t - tNeed, na, nb, nc, nd + 1, sa + na*tNeed - bp.d_a, sb + nb*tNeed, sc + nc*tNeed - bp.d_c, sd + nd*tNeed);
				if (s > max) max = s;
			}
		}
		int idle = sd + nd * t;
		if (max < idle) max = idle;
		return max;
	}

	public static class BP {
		int id;
		int a_a;
		int b_a;
		int c_a;
		int c_b;
		int d_a;
		int d_c;
		int maxA;
		int score;
	}
}
