package tools.geometry;

import tools.tuple.Pos;

public class Triangle {
	public static double area(Pos a, Pos b, Pos c) {
		return Math.abs((a.c*(b.l-c.l) + b.c*(c.l-a.l) + c.c*(a.l-b.l)) / 2.0d);
	}
}
