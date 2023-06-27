package tools.tables;

import java.util.Stack;

public class TableInProgress {

	// Ex: 5, 8, 6, 2, 3
	// --> 3, 4, 0, 2, 1
	public static int[] retrieveIndexes(int[] t) {
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

