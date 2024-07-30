package currentCG;

import tools.scanner.Scan;
import tools.strings.S;

public class CGS_Clash {
	public static void main(String[] args) {
		long l = Scan.readLong();
		long h = Scan.readLong();
		S.o(strobogrammatic(h) - strobogrammatic(l - 1));
	}
	
	private static long strobogrammatic(long x) {
		if (x == 0) return 1;
		if (x < 0) return 0;
		String s = "" + x;
		int i = s.charAt(0) - '0';
		int j = (int) x % 10;
		long z = fullStrobogrammatic(s.length() - 1);
		//z += 
		if (i == 6 || i == 7) z *= 2;
		
		//long x 
		return z;
	}
	
	private static long fullStrobogrammatic(int l) {
		if (l < 0) return 0;
		if (l % 2 == 0) return (long) Math.pow(5, l/2);
		else return 2 * (long) Math.pow(5, l/2);
	}
}
