package tools.math;

public class Num {
	public static int gcd(int a, int b) {
		if (b == 0) return a;
		else return gcd(b, a % b);
	}
	
	public static long gcd(long a, long b) {
		if (b == 0) return a;
		else return gcd(b, a % b);
	}
	
	public static int lcm(int a, int b) {
		return a * b / gcd(a, b);
	}

	public static long lcm(long a, long b) {
		return a * b / gcd(a, b);
	}
	
	public static int max(int... xs) {
		int max = Integer.MIN_VALUE;
		for (int x : xs) if (x > max) max = x;
		return max;
	}
	
	public static long max(long... xs) {
		long max = Long.MIN_VALUE;
		for (long x : xs) if (x > max) max = x;
		return max;
	}
	
	public static double max(double... xs) {
		double max = Double.NEGATIVE_INFINITY;
		for (double x : xs) if (x > max) max = x;
		return max;
	}
	
	public static int min(int... xs) {
		int min = Integer.MAX_VALUE;
		for (int x : xs) if (x < min) min = x;
		return min;
	}
	
	public static long min(long... xs) {
		long min = Long.MAX_VALUE;
		for (long x : xs) if (x < min) min = x;
		return min;
	}
	
	public static double min(double... xs) {
		double min = Double.POSITIVE_INFINITY;
		for (double x : xs) if (x < min) min = x;
		return min;
	}
	
}
