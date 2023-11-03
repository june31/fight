package currentCG;

public class CGS_Digit2 {
	private static final long MOD = 4293918721L;

	public static void main(String[] args) {
		int n = 27;  // Sum of digits
		int r = 3;   // Number of digits
		System.out.println(countNumbersWithDigitSum(n, r));
		System.out.println(nCrMod(10, 3, MOD));
		System.out.println(factorialMod(3, MOD));
		System.out.println(factorialMod(5, MOD));
		System.out.println(factorialMod(8, MOD));
		System.out.println(factorialMod(9, MOD));
		System.out.println(factorialMod(10, MOD));
	}

	public static long countNumbersWithDigitSum(int n, int r) {
		long result = nCrMod(n + r - 1, r - 1, MOD);

		int sign = -1;
		for (int i = 1; i <= r; i++) {
			result = (result + sign * nCrMod(r, i, MOD) * nCrMod(n - i * 10 + r - 1, r - 1, MOD)) % MOD;
			sign = -sign;
		}

		return result;
	}

	// Compute n! % p using iterative method
	private static long factorialMod(int n, long p) {
		long result = 1;
		for (int i = 1; i <= n; i++) {
			result = (result * i) % p;
		}
		return result;
	}

	// Compute (x^y) % p using binary exponentiation
	private static long powerMod(long x, long y, long p) {
		long result = 1;
		x = x % p;

		while (y > 0) {
			if ((y & 1) == 1) {
				result = (result * x) % p;
			}
			y >>= 1;
			x = (x * x) % p;
		}
		return result;
	}

	// Compute nCr % p using Lucas Theorem
	private static long nCrMod(int n, int r, long p) {
		if (r == 0) return 1L;
		long facN = factorialMod(n, p);
		long facR = factorialMod(r, p);
		long facNR = factorialMod(n - r, p);
		return (facN * powerMod(facR, p - 2, p) % p * powerMod(facNR, p - 2, p) % p) % p;
	}
	
	
}
