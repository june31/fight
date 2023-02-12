package tools.tables;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class T {

	public static List<Integer> ints(int[] t) {
		var l = new ArrayList<Integer>();
		for (int i: t) l.add(i); 
		return l;
	}

	public static List<Long> longs(long[] t) {
		var l = new ArrayList<Long>();
		for (long i: t) l.add(i); 
		return l;
	}

	public static List<Double> doubles(double[] t) {
		var l = new ArrayList<Double>();
		for (double i: t) l.add(i); 
		return l;
	}

	public static List<String> strings(String[] t) {
		var l = new ArrayList<String>();
		for (String i: t) l.add(i); 
		return l;
	}

	public static <A> List<A> objects(A[] t) {
		var l = new ArrayList<A>();
		for (A i: t) l.add(i); 
		return l;
	}

	public static String toString(int[] t, String sep) {
		boolean first = true;
		StringBuilder sb = new StringBuilder();
		for (int a: t) {
			if (!first) sb.append(sep);
			first = false;
			sb.append(a);
		}
		return sb.toString();
	}

	public static int[] sortAndCreateIndexes(int[] t) {
		int[] r = new int[t.length];
		for (int i = 0; i < t.length; i++) r[i] = i;
		Stack<Integer> stack = new Stack<>();
		stack.push(0);
		stack.push(t.length - 1);
		while (!stack.isEmpty()) {
			int end = stack.pop();
			int start = stack.pop();
			if (start < end) {
				int pivotIndex = partition(t, r, start, end);
				stack.push(pivotIndex + 1);
				stack.push(end);
				stack.push(start);
				stack.push(pivotIndex - 1);
			}
		}
		return r;
	}

	private static int partition(int[] t, int[] r, int start, int end) {
		int pivot = t[end];
		int i = start - 1;
		for (int j = start; j < end; j++) {
			if (t[j] <= pivot) {
				i++;
				int temp = t[i];
				t[i] = t[j];
				t[j] = temp;
				temp = r[i];
				r[i] = r[j];
				r[j] = temp;
			}
		}
		int temp = t[i + 1];
		t[i + 1] = t[end];
		t[end] = temp;
		temp = r[i + 1];
		r[i + 1] = r[end];
		r[end] = temp;
		return i + 1;
	}
	
	public static int[] posIndex(int[] indexes) {
		int[] t = new int[indexes.length];
		for (int i = 0; i < t.length; i++) t[indexes[i]] = i;
		return t;
	}
}

