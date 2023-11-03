package currentCG;

public class _Test {
    private static final long MOD = 2147483647l;

    public static void main(String[] args) {
        int n = 10;
        int r = 2;
        System.out.println(nCrMod(n, r));
    }

    public static long nCrMod(int n, int r) {
        if (r == 0) {
            return 1L;
        }

        long[] fac = new long[n + 1];
        fac[0] = 1;

        for (int i = 1; i <= n; i++) {
            fac[i] = Math.floorMod(fac[i - 1] * i, MOD);
        }

        return Math.floorMod(Math.floorMod(fac[n] * modInverse(fac[r], MOD), MOD) * modInverse(fac[n - r], MOD), MOD);
    }

    private static long modInverse(long a, long p) {
        return powerMod(a, p - 2, p);
    }

    private static long powerMod(long x, long y, long p) {
        long result = 1;
        x = Math.floorMod(x, p);

        while (y > 0) {
            if ((y & 1) == 1) {
                result = Math.floorMod(result * x, p);
            }
            y = y >> 1;
            x = Math.floorMod(x * x, p);
        }
        return result;
    }
}