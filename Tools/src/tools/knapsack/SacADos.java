package tools.knapsack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SacADos {
	private int[] w;
	private int[] v;
	private int n;
	public int[][] table;
	public int max;
	
	public SacADos(int[] weights) {
		this(weights, weights);
	}

	public SacADos(int[] weights, int[] values) {
		w = weights;
		v = values;
		n = w.length;
	}

	public List<Integer> solve(int capacity) {
		List<Integer> l = new ArrayList<>();
		table = new int[n + 1][capacity + 1];
		for (int i = 0; i < n; i++) {
			for (int c = 0; c <= capacity; c++) {
				if (c >= w[i]) table[i+1][c] = Math.max(table[i][c], table[i][c-w[i]] + v[i]);
				else table[i+1][c] = table[i][c];
			}	
		}
		max = table[n][capacity];
		int m = capacity;
		for (int i = n-1; i >= 0; i--) {
			if (table[i+1][m] != table[i][m]) {
				l.add(i);
				m -= w[i];
			}
		}
		Collections.reverse(l);
		return l;
	}
}
