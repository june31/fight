package tools.collections;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class J {
	public static <A> List<A> intersect(Collection<A> c1, Collection<A> c2) {
		return c1.stream()
		  .distinct()
		  .filter(c2::contains)
		  .collect(Collectors.toList());
	}

	public static <A> List<A> minus(Collection<A> c1, Collection<A> c2) {
		return c1.stream()
		  .distinct()
		  .filter(x -> !c2.contains(x))
		  .collect(Collectors.toList());
	}

	public static <A> List<A> union(Collection<A> c1, Collection<A> c2) {
		var s = new TreeSet<>(c1);
		s.addAll(c2);
		return new ArrayList<>(s);
	}

	public static int[] ints(Collection<Integer> c) {
		var t = new int[c.size()];
		int p = 0;
		for (int i: c) t[p++] = i; 
		return t;
	}

	public static List<Integer> ints(int[] t) {
		var l = new ArrayList<Integer>();
		for (int i: t) l.add(i); 
		return l;
	}

	public static long[] longs(Collection<Long> c) {
		var t = new long[c.size()];
		int p = 0;
		for (long i: c) t[p++] = i; 
		return t;
	}

	public static List<Long> longs(long[] t) {
		var l = new ArrayList<Long>();
		for (long i: t) l.add(i); 
		return l;
	}

	public static double[] doubles(Collection<Double> c) {
		var t = new double[c.size()];
		int p = 0;
		for (double i: c) t[p++] = i; 
		return t;
	}

	public static List<Double> doubles(double[] t) {
		var l = new ArrayList<Double>();
		for (double i: t) l.add(i); 
		return l;
	}
	
	public static String[] strings(Collection<String> c) {
		var t = new String[c.size()];
		int p = 0;
		for (String i: c) t[p++] = i; 
		return t;
	}

	public static List<String> strings(String[] t) {
		var l = new ArrayList<String>();
		for (String i: t) l.add(i); 
		return l;
	}
	
	@SuppressWarnings("unchecked")
	public static <A> A[] objects(Class<A> clazz, Collection<A> c) {
		var t = (A[]) (Array.newInstance(clazz, c.size()));
		int p = 0;
		for (A i: c) t[p++] = i; 
		return t;
	}

	public static <A> List<A> objects(A[] t) {
		var l = new ArrayList<A>();
		for (A i: t) l.add(i); 
		return l;
	}
	
	public static <A> String toString(Collection<A> c, String sep) {
		boolean first = true;
		StringBuilder sb = new StringBuilder();
		for (A a: c) {
			if (!first) sb.append(sep);
			first = false;
			sb.append(a);
		}
		return sb.toString();
	}
}
