package tools.random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SplittableRandom;

import tools.collections.int32.L;
import tools.collections.pos.Lp;
import tools.tuple.Pos;

public class RND {
	private static SplittableRandom r = new SplittableRandom();
	
	public static void setSeed(long seed) { r = new SplittableRandom(seed); }
	
	public static int nextInt(int i) { return r.nextInt(i); }
	public static long nextLong(long l) { return r.nextLong(l); }
	public static boolean nextBoolean() { return r.nextBoolean(); }
	public static double nextDouble(double d) { return r.nextDouble(d); }
	public static double nextDouble(double d1, double d2) { return r.nextDouble(d1, d2); }
	public static L pick(int k, L l) {
		int n = l.size();
        if (k > n / 2) {
    		L result = new L();
            L numbers = new L();
            for (int i = 0; i < n; i++) numbers.add(i);
            Collections.shuffle(numbers);
            for (int i = 0; i < k; i++) result.add(numbers.get(i));
            return result;
        } else {
            Set<Integer> selectedNumbers = new HashSet<>();
            while (selectedNumbers.size() < k) selectedNumbers.add(nextInt(n));
            return new L(selectedNumbers);
        }
	}
	public static Lp pickPos(int k, Lp l) {
		int n = l.size();
    	Lp result = new Lp();
        if (k > n / 2) {
            L numbers = new L();
            for (int i = 0; i < n; i++) numbers.add(i);
            Collections.shuffle(numbers);
            for (int i = 0; i < k; i++) result.add(l.get(numbers.get(i)));
            return result;
        } else {
            Set<Pos> selectedPos = new HashSet<>();
            while (selectedPos.size() < k) selectedPos.add(l.get(nextInt(n)));
            return new Lp(selectedPos);
        }
	}
}
