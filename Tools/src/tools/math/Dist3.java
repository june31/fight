package tools.math;

import tools.tuple.Pos3;

public class Dist3 {
	public static double standard(Pos3 p1, Pos3 p2) {
		return Math.sqrt((p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y) + (p2.z - p1.z) * (p2.z - p1.z));
	}
	public static long squared(Pos3 p1, Pos3 p2) {
		return (long) (p2.x - p1.x) * (p2.x - p1.x) + (long) (p2.y - p1.y) * (p2.y - p1.y) + (long) (p2.z - p1.z) * (p2.z - p1.z);
	}
}
