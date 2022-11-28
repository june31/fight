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
}
