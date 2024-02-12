package tools.math.list;

import java.util.Map;

import tools.collections.int32.L;
import tools.math.Num;

public class NumL {
	
	public static int gcd(L l) {
		return Num.gcd(l.array());
	}

	public static long lcm(L l) {
		return Num.lcm(l.array());
	}
	
    public static L findNPrimes(int n) {
        L primes = new L();
        int max = n > 5 ? (int) (n * Math.log(n) + n * Math.log(Math.log(n))) : 12;
        boolean[] notPrime = new boolean[max];
        for (int i = 2; primes.size() < n; i++) {
            if (!notPrime[i]) {
                primes.add(i);
                for (int j = 2 * i; j < max; j += i) notPrime[j] = true;
            }
        }
        return primes;
    }

    public static L findPrimesTo(int n) {
        L primes = new L();
        if (n < 2) return primes;
        boolean[] notPrime = new boolean[n+1];
        for (int i = 2; i <= n; i++) {
            if (!notPrime[i]) {
                primes.add(i);
                for (int j = 2 * i; j <= n; j += i) notPrime[j] = true;
            }
        }
        return primes;
    }
    
    public static L findNFibo(int n) {
        L fibo = new L();
        int a = 1;
        int b = 1;
        for (int i = 0; i < n; i++) {
        	fibo.add(a);
        	int tmp = b;
        	b = a + b;
        	a = tmp;
        }
        return fibo;
    }

    public static L findFiboTo(int n) {
        L fibo = new L();
        int a = 1;
        int b = 1;
        while (a <= n) {
        	fibo.add(a);
        	int tmp = b;
        	b = a + b;
        	a = tmp;
        }
        return fibo;
    }
    
    public static L max(Map<Integer, Integer> m) {
    	int max = Num.max(m.values(), v -> v).o;
    	L l = new L();
    	for (Map.Entry<Integer, Integer> e: m.entrySet()) if (e.getValue() == max) l.add(e.getKey());
    	return l;
    }
}
