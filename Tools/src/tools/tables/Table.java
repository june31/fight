package tools.tables;

import java.util.List;

public class Table {
	public static int[][] wall(int[][] t, int x) {
		return wall(t, 1, x);
	}

	public static int[][] wall(int[][] t, int thickness, int x) {
		int[][] walled = new int[t.length + 2*thickness][t[0].length + 2*thickness];
		for (int i = 0; i < walled[0].length; i++) {
			for (int j = 0; j < thickness; j++) {
				walled[j][i] = x;
				walled[t.length + thickness + j][i] = x;
			}
		}
		for (int i = 0; i < walled.length; i++) {
			for (int j = 0; j < thickness; j++) {
				walled[i][j] = x;
				walled[i][t[0].length + thickness + j] = x;
			}
		}
		for (int i = 0; i < t.length; i++)
			for (int j = 0; j < t[0].length; j++) walled[i+thickness][j+thickness] = t[i][j];
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
	
	public static int[] toIntArray(byte[] table) {
		int[] t = new int[table.length];
		for (int i = 0; i < table.length; i++) t[i] = table[i];
		return t;
	}

	public static int[][] toMap(String[] table) {
		int[][] t = new int[table.length][table[0].length()];
		for (int i = 0; i < table.length; i++) {
			byte[] bytes = table[i].getBytes();
			for (int j = 0; j < bytes.length; j++) t[i][j] = bytes[j];
		}
		return t;
	}

	public static void showMap(int[][] map) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				sb.append((char) map[i][j]);
			}
			sb.append('\n');
		}
		System.out.println(sb);
	}
}
