package codechef.training.interval;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import tools.enumeration.any.MixAnyOrdered;
import tools.math.Primes;
import tools.scanner.Scan;

public class Codechef_Coprimes {

	static List<Long> p1;
	static List<Long> p2;
	static List<Long> p;

	public static void main(String[] args) {
		var factors = List.of(2l, 3l, 5l, 7l);
		System.out.println(countCoprimes(210l * 100 - 2, factors));
		System.out.println(countCoprimes2(210l * 100 - 2, factors));
		System.out.println(countCoprimes3(210l * 100 - 2, factors));
	}

	public static void main2(String[] args) {
		int a = Scan.readInt();
		int b = Scan.readInt();
		long l = Scan.readLong();
		long r = Scan.readLong();
		
		p1 = Primes.retrieveUniqueFactorsLong(a);
		p2 = Primes.retrieveUniqueFactorsLong(b);
		var all = new LinkedHashSet<>(p1);
		all.addAll(p2);
		p = new ArrayList<>(all);
		System.out.println(count(r) - count(l-1));
	}

	private static long count(long x) {
		long c1 = countCoprimes(x, p1);
		long c2 = countCoprimes(x, p2);
		long c = countCoprimes(x, p);
		return c1 + c2 - c;
	}

	private static long countCoprimes(long v, List<Long> factors) {
		long r = 0;
		for (var l : new MixAnyOrdered<>(factors)) {
			long m = ((l.size() & 1) == 0) ? 1 : -1;
			long f = l.stream().reduce(1l, (a, b) -> a * b);
			r += m * Math.floorDiv(v, f);
		}
		return r;
	}
	
	private static long countCoprimes2(long n, Collection<Long> primes) {
        double r = 1;
        for (long l : primes) {
            r *= 1d - 1d / l;
        }
        System.out.println("=" + (r * n));
        return (long) (r * n + 0.5);
    }
	
	private static long countCoprimes3(long n, Collection<Long> primes) {
		MathContext m = MathContext.DECIMAL128;
        BigDecimal r = new BigDecimal(1, m);
        for (long l : primes) {
        	BigDecimal c = new BigDecimal(1, m);
        	c = c.divide(new BigDecimal(l), m).add(new BigDecimal(-1), m).negate(m);
            r = r.multiply(c, m);
        }
        BigDecimal res = new BigDecimal(n, m).multiply(r, m);
        System.out.println("=" + res);
        return res.longValue();
    }

}