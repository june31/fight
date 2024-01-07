package aoc.done;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import tools.collections.int32.L;
import tools.math.Primes;
import tools.scanner.Scan;

public class Day_24_2 {

	private static String[] input = Scan.readRawLines();

	public static void main(String[] args) {

		List<T> ps = new ArrayList<>();
		List<T> ss = new ArrayList<>();
		Map<Long, L> vMap = new TreeMap<>();
		int id = 0;
		for (String in: input) {
			String[] t = in.split(" @ ");
			String[] t1 = t[0].split(", ");
			String[] t2 = t[1].split(", ");
			ps.add(new T(Long.parseLong(t1[0]), Long.parseLong(t1[1]), Long.parseLong(t1[2])));
			T v = new T(Long.parseLong(t2[0].trim()), Long.parseLong(t2[1].trim()), Long.parseLong(t2[2].trim()));
			ss.add(v);
			L l = vMap.get(v.b); 
			if (l == null) {
				l = new L();
				vMap.put(v.b, l);
			}
			l.add(id++);
		}

		for (var e: vMap.entrySet()) {
			long v = e.getKey();
			L l = e.getValue();
			if (l.size() == 1) continue;
			System.out.println("[" + v + "]");
			for (int i = 0; i < l.size(); i++) {
				for (int j = i+1; j < l.size(); j++) {
					T p1 = ps.get(l.get(i));
					long v1 = p1.b;
					T p2 = ps.get(l.get(j));
					long v2 = p2.b;
					System.out.println(Primes.retrieveAllFactorsLong(Math.abs(v1 - v2)));
				}
			}
		}
		
		
		int vx = 312;
		int vy = -116;
		int vz = 109;
		
		/*
		int vx = -3;
		int vy = 1;
		int vz = 2;
		*/
		
		T p1 = ps.get(0);
		T s1 = ss.get(0);
		long x1 = p1.a;
		long y1 = p1.b;
		long z1 = p1.c;
		long vx1 = s1.a;
		long vy1 = s1.b;
		long vz1 = s1.c;
		
		T p2 = ps.get(1);
		T s2 = ss.get(1);
		long x2 = p2.a;
		long y2 = p2.b;
		long z2 = p2.c;
		long vx2 = s2.a;
		long vy2 = s2.b;
		long vz2 = s2.c;

		long dx1 = vx1 - vx;
		long dy1 = vy1 - vy;
		long dz1 = vz1 - vz;
		long dx2 = vx2 - vx;
		long dy2 = vy2 - vy;
		long dz2 = vz2 - vz;

		
		System.out.println(x1 + ", " + y1 + ", " + z1 + " @ " + vx1 + ", " + vy1 + ", " + vz1);
		System.out.println(x2 + ", " + y2 + ", " + z2 + " @ " + vx2 + ", " + vy2 + ", " + vz2);

		System.out.println(x1 + "," + dx1 + "," + y1 + "," + dy1 + "," + x2 + "," + dx2 + "," + y2 + "," + dy2);
		

		BigInteger _x1 = BigInteger.valueOf(x1);
        BigInteger _y1 = BigInteger.valueOf(y1);
        BigInteger _z1 = BigInteger.valueOf(z1);
        BigInteger _dx1 = BigInteger.valueOf(dx1);
        BigInteger _dy1 = BigInteger.valueOf(dy1);
        BigInteger _dz1 = BigInteger.valueOf(dz1);
        BigInteger _x2 = BigInteger.valueOf(x2);
        BigInteger _y2 = BigInteger.valueOf(y2);
        BigInteger _z2 = BigInteger.valueOf(z2);
        BigInteger _dx2 = BigInteger.valueOf(dx2);
        BigInteger _dy2 = BigInteger.valueOf(dy2);
        BigInteger _dz2 = BigInteger.valueOf(dz2);

        BigInteger _x = _dx2.multiply(_dy1).multiply(_x1).negate()
                        .add(_dx1.multiply(_dy2).multiply(_x2))
                        .add(_dx1.multiply(_dx2).multiply(_y1))
                        .subtract(_dx1.multiply(_dx2).multiply(_y2))
                        .divide(_dx2.multiply(_dy1).subtract(_dx1.multiply(_dy2))).negate();

        BigInteger _y = _dy1.multiply(_dy2).multiply(_x1)
                        .subtract(_dy1.multiply(_dy2).multiply(_x2))
                        .subtract(_dx1.multiply(_dy2).multiply(_y1))
                        .add(_dx2.multiply(_dy1).multiply(_y2))
                        .divide(_dx2.multiply(_dy1).negate().add(_dx1.multiply(_dy2))).negate();

        BigInteger _z = _dz1.multiply(_dz2).multiply(_x1)
                .subtract(_dz1.multiply(_dz2).multiply(_x2))
                .subtract(_dx1.multiply(_dz2).multiply(_z1))
                .add(_dx2.multiply(_dz1).multiply(_z2))
                .divide(_dx2.multiply(_dz1).negate().add(_dx1.multiply(_dz2))).negate();

        BigInteger _t1 = _dy2.multiply(_x1).subtract(_dy2.multiply(_x2))
                        .subtract(_dx2.multiply(_y1)).add(_dx2.multiply(_y2))
                        .divide(_dx2.multiply(_dy1).negate().add(_dx1.multiply(_dy2))).negate();

        BigInteger _t2 = _dy1.multiply(_x1).negate().add(_dy1.multiply(_x2))
                        .add(_dx1.multiply(_y1)).subtract(_dx1.multiply(_y2))
                        .divide(_dx2.multiply(_dy1).subtract(_dx1.multiply(_dy2))).negate();

        // Affichage des résultats
        System.out.println("x = " + _x);
        System.out.println("y = " + _y);
        System.out.println("z = " + _z);
        System.out.println("t1 = " + _t1);
        System.out.println("t2 = " + _t2);
        
        long x$ = -(-(dx2 * dy1 * x1) + dx1 * dy2 * x2 + dx1 * dx2 * y1 - dx1 * dx2 * y2) / (dx2 * dy1 - dx1 * dy2);
        long y$ = -(dy1 * dy2 * x1 - dy1 * dy2 * x2 - dx1 * dy2 * y1 + dx2 * dy1 * y2) / (-(dx2 * dy1) + dx1 * dy2);
        long z$ = -(dz1 * dz2 * x1 - dz1 * dz2 * x2 - dx1 * dz2 * z1 + dx2 * dz1 * z2) / (-(dx2 * dz1) + dx1 * dz2);
        long t1$ = -(dy2 * x1 - dy2 * x2 - dx2 * y1 + dx2 * y2) / (-(dx2 * dy1) + dx1 * dy2);
        long t2$ = -(-(dy1 * x1) + dy1 * x2 + dx1 * y1 - dx1 * y2) / (dx2 * dy1 - dx1 * dy2);

        // Affichage des résultats
        System.out.println("x = " + x$);
        System.out.println("y = " + y$);
        System.out.println("z = " + z$);
        System.out.println("t1 = " + t1$);
        System.out.println("t2 = " + t2$);
        
        long x = 129723668686742l;
        long y = 353939130278484l;
        long z = 227368817349775l;
        long t1 = 379155208275l;
        long t2 = 495926101181l;
        //System.out.println("dx1=" + dx1
        System.out.println(x1 + (vx1 - vx) * t1 - x);
        System.out.println(y1 + (vy1 - vy) * t1 - y);
        System.out.println(x2 + (vx2 - vx) * t2 - x);
        System.out.println(y2 + (vy2 - vy) * t2 - y);
        System.out.println(z1 + (vz1 - vz) * t1 - z);
        System.out.println(z2 + (vz2 - vz) * t2 - z);
        System.out.println(x1 + dx1 * t1 - x);
        System.out.println(y1 + dy1 * t1 - y);
        System.out.println(z1 + dz1 * t1 - z);
        System.out.println(x2 + dx2 * t2 - x);
        System.out.println(y2 + dy2 * t2 - y);
        System.out.println(z2 + dz2 * t2 - z);
        
        System.out.println(x + y + z);
	}

    
	private static record T(long a, long b, long c) {}
}
