package memoization.urinal;

import tools.scanner.Scan;

// The Optimal Urinal Problem
// https://www.codingame.com/training/medium/the-optimal-urinal-problem
class CG_OptimalUrinalProblemOpti {

	public static void main(String[] args) {
		int n = Scan.readInt();
	    int pos = magicPos(n - 1);
	    int nb = magicNb(n - 1, pos);
	    System.out.println(nb + " " + (pos + 1));
	}

	private static int magicPos(int i) {
		int msb1 = 31 - Integer.numberOfLeadingZeros(i);
		if (i >> (msb1 - 2) == 7) return 0;
		i ^= 1 << msb1;
		int r = 1 << (31 - Integer.numberOfLeadingZeros(i));
		return (r | r >> 1) < i ? i : r & ~1;
	}

	private static int magicNb(int i, int pos) {
		int msb1 = 31 - Integer.numberOfLeadingZeros(i);
		if (i >> (msb1 - 2) == 7) return (i ^ 1 << msb1) + 1;
		if (pos == 0) return i <= 1 ? 1 : (1 << (msb1 - 1)) + 1;
		i ^= 1 << msb1;
		int msb2 = 31 - Integer.numberOfLeadingZeros(i);
		int r = 1 << msb2;
		if ((r | r >> 1) < i) return i + (1 << (msb1 - 1)) - (1 << msb2) + 1;
	    return (1 << (msb1 - 1)) + (pos >> 1) + 1;
	}
}
