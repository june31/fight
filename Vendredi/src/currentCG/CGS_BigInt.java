package currentCG;

import java.math.BigInteger;

public class CGS_BigInt {
	private static final BigInteger MOD = new BigInteger("4293918721");

	public static void main(String[] args) {
		int n = 49999; // Number of digits
		int s = 418647; // Sum of digits
		System.out.println(countNumbersWithDigitSum(s, n).mod(MOD));
	}

	public static BigInteger countNumbersWithDigitSum(int s, int n) {
		BigInteger result = nCr(s + n - 1, n - 1);
		int sign = -1;
		for (int i = 1; i <= n; i++) {
			System.out.println(i);
			BigInteger m2 = nCr(s - i * 10 + n - 1, n - 1);
			if (m2.equals(BigInteger.ZERO)) break;
			BigInteger m1 = nCr(n, i);
			result = result.add(BigInteger.valueOf(sign).multiply(m1).multiply(m2));
			sign = -sign;
		}
		
		return result;
	}

    public static BigInteger nCr(int n, int r) {
        if (r > n) return BigInteger.ZERO;
        if (r == 0 || r == n) return BigInteger.ONE;
        BigInteger result = BigInteger.ONE;
        for (int i = 0; i < r; i++) result = result.multiply(BigInteger.valueOf(n - i)).divide(BigInteger.valueOf(i + 1));
        return result;
    }
}
