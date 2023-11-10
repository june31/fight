package currentCG;

public class CGS_Digit2 {
	private static final long MOD = 1_000_003L;

	public static void main(String[] args) {
		int n = 4; // Number of digits
		int s = 18; // Sum of digits
		System.out.println(countNumbersWithDigitSum(s, n));
	}

	public static long countNumbersWithDigitSum(int s, int n) {
		long result = nCrMod(s + n - 1, n - 1, MOD);
		int sign = -1;
		for (int i = 1; i <= n; i++) {
			long m1 = nCrMod(n, i, MOD);
			long m2 = nCrMod(s - i * 10 + n - 1, n - 1, MOD);
			result = (result + sign * m1 * m2) % MOD;
			sign = -sign;
		}
		return result;
	}

	// Compute n! % p using iterative method
	private static long factorialMod(int n, long p) {
		long result = 1;
		for (int i = 2; i <= n; i++) {
			result = (result * i) % p;
		}
		return result;
	}

	// Compute (x^y) % p using binary exponentiation
	private static long powerMod(long x, long y, long p) {
		long result = 1;
		x = x % p;
		while (y > 0) {
			if ((y & 1) == 1) result = (result * x) % p;
			y >>= 1;
			x = (x * x) % p;
		}
		return result;
	}

	// Compute nCr % p using Lucas Theorem
	private static long nCrMod(int n, int r, long p) {
		if (n < r) return 0;
		if (r == 0) return 1L;
		long facN = factorialMod(n, p);
		long facR = factorialMod(r, p);
		long facNR = factorialMod(n - r, p);
		return (facN * powerMod(facR, p - 2, p) % p * powerMod(facNR, p - 2, p) % p) % p;
	}
}
