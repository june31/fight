package tables;

import java.util.List;

public class Table {
	public static int[][] wall(int[][] t, int x) {
		int[][] walled = new int[t.length + 2][t[0].length + 2];
		for (int i = 0; i < walled[0].length; i++) {
			walled[0][i] = x;
			walled[t.length + 1][i] = x;
		}
		for (int i = 0; i < walled.length; i++) {
			walled[i][0] = x;
			walled[i][t.length + 1] = x;
		}
		for (int i = 0; i < t.length; i++)
			for (int j = 0; j < t[0].length; j++) walled[i+1][j+1] = t[i][j];
		return walled;
	}
	
	public static int[][][] wall(int[][][] t, int x) {
		int[][][] walled = new int[t.length + 2][t[0].length + 2][t[0][0].length + 2];
		for (int j = 0; j < walled[0].length; j++) {
			for (int k = 0; k < walled[0][0].length; k++) {
				walled[0][j][k] = x;
				walled[t.length + 1][j][k] = x;
			}
		}
		for (int i = 0; i < walled.length; i++) {
			for (int k = 0; k < walled[0][0].length; k++) {
				walled[i][0][k] = x;
				walled[i][t[0].length + 1][k] = x;
			}
		}
		for (int i = 0; i < walled.length; i++) {
			for (int j = 0; j < walled[0].length; j++) {
				walled[i][j][0] = x;
				walled[i][j][t.length + 1] = x;
			}
		}
		for (int i = 0; i < t.length; i++)
			for (int j = 0; j < t[0].length; j++)
				for (int k = 0; k < t[0][0].length; k++) walled[i+1][j+1][k+1] = t[i][j][k];
		return walled;
	}
	
	public static int[][] convert(List<int[]> l) {
		int[][] res = new int[l.size()][];
		for (int i = 0; i < res.length; i++) {
			res[i] = l.get(i);
		}
		return res;
	}
}
