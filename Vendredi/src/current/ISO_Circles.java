package current;

import tools.chrono.Chrono;
import tools.collections.pos.Lp;
import tools.enumeration.combinations.MixCombinations;
import tools.geometry.Circle;
import tools.geometry.Triangle;
import tools.math.Dist;
import tools.math.Num;
import tools.random.RND;
import tools.scanner.list.ScanLp;
import tools.tuple.Pos;

public class ISO_Circles {
	
	public static void main(String[] args) {
		Lp points = ScanLp.readCL();
		
		// Elimination d'un max de points
		Chrono.play(250, () -> {
			if (points.size() < 4) return false;
			Pos[] ps = RND.pickPos(4, points);
			double a = Triangle.area(ps[0], ps[1], ps[2]);
			double b = Triangle.area(ps[3], ps[1], ps[2]);
			double c = Triangle.area(ps[0], ps[3], ps[2]);
			double d = Triangle.area(ps[0], ps[1], ps[3]);
			if (a == b + c + d) points.remove(ps[3]); 
			if (b == a + c + d) points.remove(ps[0]); 
			if (c == b + a + d) points.remove(ps[1]); 
			if (d == b + c + a) points.remove(ps[2]); 
			return true;
		});

		// Groupes de 2 points
		G2: for (var ps : new MixCombinations<Pos>(points, 2)) {
			Pos p1 = ps.get(0);
			Pos p2 = ps.get(1);
			double x = (p1.c + p2.c) / 2d;
			double y = (p1.l + p2.l) / 2d;
			double rr = Dist.square(x, y, p1.c, p1.l);
			for (Pos p: points) if (Dist.square(x, y, p.c, p.l) > rr + Num.EPSILON) continue G2;
			System.out.println(Num.round(rr * Math.PI, 5, 5));
			return;
		}
		
		// Groupes de 3 points
		double best = Double.POSITIVE_INFINITY;
		G3: for (var ps : new MixCombinations<Pos>(points, 3)) {
			Pos p1 = ps.get(0);
			Pos p2 = ps.get(1);
			Pos p3 = ps.get(2);
			double[] c = Circle.findCenter(p1, p2, p3);
			if (Double.isFinite(c[0])) {
				double rr = Dist.square(c[0], c[1], p1.c, p1.l);
				if (rr < best) {
					for (Pos p: points) if (Dist.square(c[0], c[1], p.c, p.l) > rr + Num.EPSILON) continue G3;
					best = rr;
				}
			}
		}
		System.out.println(Num.round(best * Math.PI, 5, 5));
	}
}
