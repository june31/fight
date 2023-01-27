package tools.math;

import java.util.ArrayList;
import java.util.List;

import tools.enumeration.any.MixAnyOrdered;

public class Primes {

	public static List<Integer> retrieveUniqueFactors(int n) {
		if (n <= 0) return null;
		List<Integer> l = new ArrayList<>();
        if ((n & 1) == 0) {
        	l.add(2);
        	n >>= Integer.numberOfTrailingZeros(n);
        }
        int c = 3;
        int max = (int) Math.sqrt(n);
        while (n > 1 && c <= max) {
            if (Math.floorMod(n, c) == 0) {
                l.add(c);
                n /= c;
                while (Math.floorMod(n, c) == 0) n /= c;
            }
            c += 2;
        }
        if (n > 1) l.add(n);
		return l;
	}

	public static List<Long> retrieveUniqueFactorsLong(long n) {
		if (n <= 0) return null;
		List<Long> l = new ArrayList<>();
        if ((n & 1) == 0) {
        	l.add(2l);
        	n >>= Long.numberOfTrailingZeros(n);
        }
        long c = 3;
        int max = (int) Math.sqrt(n);
        while (n > 1 && c <= max) {
            if (Math.floorMod(n, c) == 0) {
                l.add(c);
                n /= c;
                while (Math.floorMod(n, c) == 0) n /= c;
            }
            c += 2;
        }
        if (n > 1) l.add(n);
		return l;
	}
	
	public static long countCoprimes(long v, List<Long> factors) {
		long r = 0;
		for (var l : new MixAnyOrdered<>(factors)) {
			long m = ((l.size() & 1) == 0) ? 1 : -1;
			long f = l.stream().reduce(1l, (a, b) -> a * b);
			r += m * Math.floorDiv(v, f);
		}
		return r;
	}
}