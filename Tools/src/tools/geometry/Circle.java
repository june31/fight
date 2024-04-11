package tools.geometry;

import tools.tuple.Pos;

public class Circle {
	public static double[] findCenter(double x1, double y1, double x2, double y2, double x3, double y3) {
        double d = 2 * (x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2));
        double x = ((x1 * x1 + y1 * y1) * (y2 - y3) + (x2 * x2 + y2 * y2) * (y3 - y1) + (x3 * x3 + y3 * y3) * (y1 - y2)) / d;
        double y = ((x1 * x1 + y1 * y1) * (x3 - x2) + (x2 * x2 + y2 * y2) * (x1 - x3) + (x3 * x3 + y3 * y3) * (x2 - x1)) / d;
        return new double[]{x, y};
    }
	public static double[] findCenter(Pos p1, Pos p2, Pos p3) {
        double d = 2 * (p1.c * (p2.l - p3.l) + p2.c * (p3.l - p1.l) + p3.c * (p1.l - p2.l));
        double x = ((p1.c * p1.c + p1.l * p1.l) * (p2.l - p3.l) + (p2.c * p2.c + p2.l * p2.l) * (p3.l - p1.l) + (p3.c * p3.c + p3.l * p3.l) * (p1.l - p2.l)) / d;
        double y = ((p1.c * p1.c + p1.l * p1.l) * (p3.c - p2.c) + (p2.c * p2.c + p2.l * p2.l) * (p1.c - p3.c) + (p3.c * p3.c + p3.l * p3.l) * (p2.c - p1.c)) / d;
        return new double[]{x, y};
    }
}
