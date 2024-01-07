package aoc.done;

import java.util.ArrayList;
import java.util.List;

import tools.scanner.Scan;

public class Day_24_1 {
	
	private static double EPS = 1;
	private static double MIN = 200_000_000_000_000d;
	private static double MAX = 400_000_000_000_000d;
	private static String[] input = Scan.readRawLines();

	public static void main(String[] args) {

		List<LL> ps = new ArrayList<>();
		List<LL> ss = new ArrayList<>();
		for (String in: input) {
			String[] t = in.split(" @ ");
			String[] t1 = t[0].split(", ");
			String[] t2 = t[1].split(", ");
			ps.add(new LL(Long.parseLong(t1[0]), Long.parseLong(t1[1])));
			ss.add(new LL(Long.parseLong(t2[0].trim()), Long.parseLong(t2[1].trim())));
		}
		int n = ps.size();

		long count = 0;
		for (int i = 0; i < n; i++) {
			var p1 = ps.get(i);
			var s1 = ss.get(i);
			double x1 = p1.a;
			double y1 = p1.b;
			var vx1 = s1.a;
			var vy1 = s1.b;
			for (int j = i+1; j < n; j++) {
				var p2 = ps.get(j);
				var s2 = ss.get(j);
				double x2 = p2.a;
				double y2 = p2.b;
				var vx2 = s2.a;
				var vy2 = s2.b;
				double numX = vx1*vx2*y1-vx1*vx2*y2+vx1*vy2*x2-vx2*vy1*x1;
				double numY = vx1*vy2*y1-vx2*vy1*y2-vy1*vy2*x1+vy1*vy2*x2;
				long deno = vx1*vy2 - vx2*vy1;
				if (deno == 0) {
					if (numX == 0) {
						System.out.println("0/0");
					} else System.out.println("parallel");
				} else {
					double x = 1d * numX / deno;
					double y = 1d * numY / deno;
					if (x >= MIN-EPS && y >= MIN-EPS && x <= MAX+EPS && y <= MAX+EPS) {
						if (vx1 != 0) {
							if ((x - x1)/vx1 < 0) continue; 
						} else {
							System.out.println("vx1 null");
							if ((y - y1)/vy1 < 0) continue; 
						}
						if (vx2 != 0) {
							if ((x - x2)/vx2 < 0) continue; 
						} else {
							System.out.println("vx2 null");
							if ((y - y2)/vy2 < 0) continue; 
						}
						System.out.println(i + " " + j + " " + x + " " + y);
						count++;
					}
				}
			}
		}
		
		System.out.println(count);
	}
	
	private static record LL(long a, long b) {}
}
