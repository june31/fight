package decompte;

import java.util.Scanner;

// https://www.codingame.com/ide/puzzle/strobogrammatic-numbers
public class CGS_Strobogrammatic_palindrome_count {
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		long l = sc.nextLong();
		long h = sc.nextLong();
		System.out.println(strobogrammatic(h) - strobogrammatic(l - 1));
	}

	private static long strobogrammatic(long x) {
		if (x < 0) return 0;
		if (x < 1) return 1;
		if (x < 8) return 2;
		if (x < 11) return 3;

		String s = "" + x;
		long z = uptoNDigits(s.length() - 1);
		z += exactNbDigits(s, true);
		return z;
	}

	private static long exactNbDigits(String s, boolean first) {
		// first: le premier chiffre ne peut être zéro, sauf en cas de récursion.
		if (s.isEmpty()) return 1;
		int i = s.charAt(0) - '0';
		if (s.length() == 1) {
			if (i == 0) return 1;
			if (i < 8) return 2;
			return 3;
		}

		long z = 0;

		// 80305 -> bonus = '029' car 80305 -> 80298
		String r = bonus(s, first);
		if (r != null) z = exactNbDigits(r, false);

		// 80305 -> 79999 -> mul(7) * '999'
		int bias0 = first ? 1 : 0;
		if (i != bias0) {
			String w = "" + (i - 1);
			while (w.length() < s.length()) w = w + "9";
			z += (getMul(w) - bias0) * internal9s(s.length() - 2);
		}

		return z;
	}

	private static String bonus(String s, boolean first) {
		int i = s.charAt(0) - '0';
		int j = s.charAt(s.length() - 1) - '0';

		String r = s.substring(1, s.length() - 1);
		if (!first && i == 0) return r;
		if (i == 1) if (j != 0) return r; else return reduce(r); 
		if (i == 6) if (j == 9) return r; else return reduce(r);
		if (i == 8) if (j >= 8) return r; else return reduce(r);
		if (i == 9) if (j >= 6) return r; else return reduce(r);

		return null;
	}

	private static String reduce(String r) {
		long l = Long.parseLong(r) - 1;
		String s = "" + l;
		if (s.charAt(0) != r.charAt(0)) return null; // 200 -> 199
		return s;
	}

	private static int getMul(String s1) {
		int i = s1.charAt(0) - '0';
		if (i >= 9) return 5;
		if (i >= 8) return 4;
		if (i >= 6) return 3;
		if (i >= 1) return 2;
		return 1; 
	}

	private static long uptoNDigits(int l) {
		long s = 0;
		for (int i = 1; i <= l; i++) {
			long m = 1;
			if (i >= 2) m *= 4;
			for (int j = 2; j <= i/2; j++) m *= 5;
			if (i % 2 == 1) m *= 3;
			s += m;
		}
		return s;
	}

	private static long internal9s(int l) {
		long m = 1;
		for (int j = 1; j <= l/2; j++) m *= 5;
		if (l % 2 == 1) m *= 3;
		return m;
	}
}
