package currentCG;

public class _DigitSumCalculator {
    private static final long MOD = 4293918721L;
    private static final int MAX_DIGIT_VALUE = 9;

    public static void main(String[] args) {
        int n = 27;  // Sum of digits
        int r = 3;   // Number of digits
        System.out.println(countNumbersWithDigitSum(n, r));
    }

    public static long countNumbersWithDigitSum(int n, int r) {
        if (n > r * MAX_DIGIT_VALUE) {
            return 0;
        }
        long result = nCrMod(n + r - 1, r - 1, MOD);

        int sign = -1;
        for (int i = 1; i <= r; i++) {
            int remainingSum = n - i * (MAX_DIGIT_VALUE + 1);
            if (remainingSum < 0) {
                break;
            }
            result = (result + sign * nCrMod(r, i, MOD) * nCrMod(remainingSum + r - 1, r - 1, MOD) + MOD) % MOD;
            sign = -sign;
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
            y = y >> 1;
            x = (x * x) % p;
        }
        return result;
    }

    // Compute nCr % p using Lucas Theorem
    private static long nCrMod(int n, int r, long p) {
        if (r == 0) {
            return 1L;
        }

        long[] fac = new long[n + 1];
        fac[0] = 1;

        for (int i = 1; i <= n; i++) {
            fac[i] = fac[i - 1] * i % p;
        }

        return (fac[n] * powerMod(fac[r], p - 2, p) % p * powerMod(fac[n - r], p - 2, p) % p) % p;
    }
}
