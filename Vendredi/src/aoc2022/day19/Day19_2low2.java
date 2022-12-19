package aoc2022.day19;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import tools.chrono.Chrono;
import tools.math.Num;

public class Day19_2low2 {

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
		int max = recurse(bp, t - 1, na, nb, nc, nd, sa + na, sb + nb, sc + nc, sd + nd);
		if (sa >= bp.a_a && na < bp.maxA && t >= 4) {
			int s = recurse(bp, t - 1, na + 1, nb, nc, nd, sa + na - bp.a_a, sb + nb, sc + nc, sd + nd);
			if (s > max) max = s;
		}
		if (sa >= bp.b_a && nb < bp.c_b && t >= 6) {
			int s = recurse(bp, t - 1, na, nb + 1, nc, nd, sa + na - bp.b_a, sb + nb, sc + nc, sd + nd);
			if (s > max) max = s;
		}
		if (sa >= bp.c_a && sb >= bp.c_b && nc < bp.d_c && t >= 4) {
			int s = recurse(bp, t - 1, na, nb, nc + 1, nd, sa + na - bp.c_a, sb + nb - bp.c_b, sc + nc, sd + nd);
			if (s > max) max = s;
		}
		if (sa >= bp.d_a && sc >= bp.d_c && t >= 2) {
			int s = recurse(bp, t - 1, na, nb, nc, nd + 1, sa + na - bp.d_a, sb + nb, sc + nc - bp.d_c, sd + nd);
			if (s > max) max = s;
		}
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
