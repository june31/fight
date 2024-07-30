package currentCG;

import tools.scanner.Scan;
import tools.strings.S;

public class CGS_Clash {
	public static void main(String[] args) {
		for (int i = 0; i < 15; i++) {
			S.e(i, fullStrobogrammatic(i));
		}
		
		long l = Scan.readLong();
		long h = Scan.readLong();
		S.o(strobogrammatic(h), strobogrammatic(l - 1));

		S.o(strobogrammatic(h) - strobogrammatic(l - 1));
	}
	
	private static long strobogrammatic(long x) {
		// 0, 1, 8, 11, 69, 88, 96, 101
		if (x < 0) return 0;
		if (x == 0) return 1;
		if (x < 8) return 2;
		if (x < 11) return 3;
		if (x < 69) return 4;
		if (x < 88) return 5;
		if (x < 96) return 6;
		if (x < 101) return 7;
		
		String s = "" + x;
		int i = s.charAt(0) - '0';
		int j = (int) x % 10;
		long z = fullStrobogrammatic(s.length() - 1);
		z += stringgrammatic(s);
		return z;
	}
	
	private static long stringgrammatic(String s) {
		// TODO Auto-generated method stub
		return 0;
	}

	private static long fullStrobogrammatic(int l) {
		long s = 0;
		for (int i = 0; i <= l; i++) {
			long m = 1;
			if (i >= 2) m *= 4;
			for (int j = 2; j <= i/2; j++) m *= 5;
			if (i % 2 == 1) m *= 2;
			s += m;
		}
		return s;
	}
	
	private static long fixedStrobogrammatic(int l) {
		long s = 0;
		for (int i = 0; i <= l; i++) {
			long m = 1;
			for (int j = 1; j <= i/2; j++) m *= 5;
			if (i % 2 == 1) m *= 2;
			s += m;
		}
		return s;
	}
	
	private static long nines(int l) {
		long s = 0;
		for (int i = 0; i < l; i++) {
			s *= 10;
			s += 9;
		}
		return s;
	}
}
