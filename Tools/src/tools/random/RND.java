package tools.random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SplittableRandom;

import tools.tuple.Pos;

public class RND {
	private static SplittableRandom r = new SplittableRandom();
	
	public static void setSeed(long seed) { r = new SplittableRandom(seed); }
	
	public static int nextInt(int i) { return r.nextInt(i); }
	public static long nextLong(long l) { return r.nextLong(l); }
	public static boolean nextBoolean() { return r.nextBoolean(); }
	public static double nextDouble(double d) { return r.nextDouble(d); }
	public static double nextDouble(double d1, double d2) { return r.nextDouble(d1, d2); }
	public static int[] pick(int k, List<Integer> l) {
		int n = l.size();
        if (k > n / 2) {
    		int[] result = new int[k];
            List<Integer> numbers = new ArrayList<>(n);
            for (int i = 0; i < n; i++) numbers.add(i);
            Collections.shuffle(numbers);
            for (int i = 0; i < k; i++) result[i] = numbers.get(i);
            return result;
        } else {
            Set<Integer> selectedNumbers = new HashSet<>();
            while (selectedNumbers.size() < k) selectedNumbers.add(nextInt(n));
            return selectedNumbers.stream().mapToInt(Integer::intValue).toArray();
        }
	}
	public static Pos[] pickPos(int k, List<Pos> l) {
		int n = l.size();
    	Pos[] result = new Pos[k];
        if (k > n / 2) {
            List<Integer> numbers = new ArrayList<>(n);
            for (int i = 0; i < n; i++) numbers.add(i);
            Collections.shuffle(numbers);
            for (int i = 0; i < k; i++) result[i] = l.get(numbers.get(i));
        } else {
            Set<Integer> selectedNumbers = new HashSet<>();
            while (selectedNumbers.size() < k) selectedNumbers.add(nextInt(n));
            int id = 0;
            for (int i: selectedNumbers) result[id++] = l.get(i);
        }
        return result;
	}
}
