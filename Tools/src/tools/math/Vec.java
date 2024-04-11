package tools.math;

import tools.tuple.Pos;

public class Vec {
	public static int scalar(Pos a1, Pos a2, Pos b1, Pos b2) { return (a2.c - a1.c) * (b2.c - b1.c) + (a2.l - a1.l) * (b2.l - b1.l); }
	public static int mul(Pos a1, Pos a2, Pos b1, Pos b2) { return (a2.c - a1.c) * (b2.l - b1.l) + (a2.l - a1.l) * (b2.c - b1.c); }
}
