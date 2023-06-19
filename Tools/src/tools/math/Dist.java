package tools.math;

public class Dist {
	public static int square(int x1, int y1, int x2, int y2) {
		return (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);
	}
	
	public static double calc(double x1, double y1, double x2, double y2) {
		return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}

	public static int manhattan(int l1, int c1, int l2, int c2) {
		return Math.abs(l1 - l2) + Math.abs(c1 - c2);
	}
}
