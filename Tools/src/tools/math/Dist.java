package tools.math;

import tools.tuple.Pos;

public class Dist {
	public static int square(int x1, int y1, int x2, int y2) {
		return (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);
	}

	public static double square(double x1, double y1, double x2, double y2) {
		return (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);
	}
	
	public static int square(Pos p1, Pos p2) {
		return (p2.l - p1.l) * (p2.l - p1.l) + (p2.c - p1.c) * (p2.c - p1.c);
	}

	public static double calc(double x1, double y1, double x2, double y2) {
		return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}
	
	public static double calc(Pos p1, Pos p2) {
		return Math.sqrt((p2.l - p1.l) * (p2.l - p1.l) + (p2.c - p1.c) * (p2.c - p1.c));
	}

	public static int manh(int l1, int c1, int l2, int c2) {
		return Math.abs(l1 - l2) + Math.abs(c1 - c2);
	}
	
	public static int manh(Pos p, Pos o) {
		return Math.abs(p.l - o.l) + Math.abs(p.c - o.c);
	}
	
	public static int chebyshev(int l1, int c1, int l2, int c2) {
		return Math.max(Math.abs(l1 - l2), Math.abs(c1 - c2));
	}
	
	public static int chebyshev(Pos p, Pos o) {
		return Math.max(Math.abs(p.l - o.l), Math.abs(p.c - o.c));
	}
}
