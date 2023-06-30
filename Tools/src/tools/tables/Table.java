package tools.tables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import tools.function.BiIntConsumer;
import tools.function.BiIntToIntFunction;
import tools.function.IntToIntFunction;
import tools.function.TriIntConsumer;
import tools.function.TriIntPredicate;
import tools.function.TriIntToIntFunction;
import tools.tuple.Pos;

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
		System.out.print(sb);
	}
	
	public static void debugMap(int[][] map) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				sb.append((char) map[i][j]);
			}
			sb.append('\n');
		}
		System.err.println(sb);
	}
	
	public static void fill(int[] table, int value) {
		for (int i = 0; i < table.length; i++) table[i] = value;
	}

	public static void fill(int[][] map, int value) {
		for (int i = 0; i < map.length; i++) for (int j = 0; j < map[0].length; j++) map[i][j] = value;
	}

	public static void clear(int[] table) {
		for (int i = 0; i < table.length; i++) table[i] = 0;
	}

	public static void clear(int[][] map) {
		for (int i = 0; i < map.length; i++) for (int j = 0; j < map[0].length; j++) map[i][j] = 0;
	}

	public static IntStream stream(int[][] map) {
		return Arrays.stream(map).flatMapToInt(Arrays::stream);
	}

	public static int[] map(int[] table, IntToIntFunction f) {
		int[] t = new int[table.length];
		for (int i = 0; i < table.length; i++) t[i] = f.apply(table[i]);
		return t;
	}

	public static int[][] map(int[][] map, IntToIntFunction f) {
		int[][] m = new int[map.length][map[0].length];
		for (int l = 0; l < map.length; l++)
			for (int c = 0; c < map[0].length; c++)
				m[l][c] = f.apply(map[l][c]);
		return m;
	}

	/** BiIntToIntFunction is (l, c) -> v' */
	public static int[][] map(int[][] map, BiIntToIntFunction f) {
		int[][] m = new int[map.length][map[0].length];
		for (int l = 0; l < map.length; l++)
			for (int c = 0; c < map[0].length; c++)
				m[l][c] = f.apply(l, c);
		return m;
	}

	/** TriIntToIntFunction is (l, c, v) -> v' */
	public static int[][] map(int[][] map, TriIntToIntFunction f) {
		int[][] m = new int[map.length][map[0].length];
		for (int l = 0; l < map.length; l++)
			for (int c = 0; c < map[0].length; c++)
				m[l][c] = f.apply(l, c, map[l][c]);
		return m;
	}

	public static int[] findAll(int[] table, int x) {
		List<Integer> posList = new ArrayList<>();
		for (int i = 0; i < table.length; i++) if (x == table[i]) posList.add(i);
		return posList.stream().mapToInt(i->i).toArray();
	}

	public static int[] findAll(int[] table, IntPredicate p) {
		List<Integer> posList = new ArrayList<>();
		for (int i = 0; i < table.length; i++) if (p.test(table[i])) posList.add(i);
		return posList.stream().mapToInt(i->i).toArray();
	}

	public static Pos[] findAll(int[][] map, int x) {
		List<Pos> posList = new ArrayList<>();
		for (int l = 0; l < map.length; l++)
			for (int c = 0; c < map[0].length; c++)
				if (x == map[l][c]) posList.add(new Pos(l, c));
		return posList.toArray(new Pos[0]);
	}
	
	public static Pos[] findAll(int[][] map, IntPredicate p) {
		List<Pos> posList = new ArrayList<>();
		for (int l = 0; l < map.length; l++)
			for (int c = 0; c < map[0].length; c++)
				if (p.test(map[l][c])) posList.add(new Pos(l, c));
		return posList.toArray(new Pos[0]);
	}

	public static Pos[] findAll(int[][] map, TriIntPredicate p) {
		List<Pos> posList = new ArrayList<>();
		for (int l = 0; l < map.length; l++)
			for (int c = 0; c < map[0].length; c++)
				if (p.test(l, c, map[l][c])) posList.add(new Pos(l, c));
		return posList.toArray(new Pos[0]);
	}

	public static int find(int[] table, int x) {
		for (int i = 0; i < table.length; i++) if (table[i] == x) return i;
		return -1;
	}

	public static Pos find(int[][] map, int x) {
		for (int l = 0; l < map.length; l++)
			for (int c = 0; c < map[0].length; c++)
				if (map[l][c] == x) return new Pos(l, c);
		return null;
	}
	
	public static void forEach(int[][] map, IntConsumer f) {
		for (int l = 0; l < map.length; l++)
			for (int c = 0; c < map[0].length; c++)
				f.accept(map[l][c]);
	}
	
	public static void forEach(int[][] map, BiIntConsumer f) {
		for (int l = 0; l < map.length; l++)
			for (int c = 0; c < map[0].length; c++)
				f.accept(l, c);
	}

	public static void forEach(int[][] map, TriIntConsumer f) {
		for (int l = 0; l < map.length; l++)
			for (int c = 0; c < map[0].length; c++)
				f.accept(l, c, map[l][c]);
	}
	
	public static int[] extractColumn(int[][] t, int col) {
		int n = t.length;
		int[] r = new int[n];
		for (int i = 0; i < n; i++) r[i] = t[i][col];
		return r;
	}
	
	public static int[] concat(int x, int[] t) {
		int[] r = new int[t.length + 1];
		r[0] = x;
		System.arraycopy(t, 0, r, 1, t.length);
		return r;
	}

	public static int[] concat(int[] t, int x) {
		int[] r = new int[t.length + 1];
		System.arraycopy(t, 0, r, 0, t.length);
		r[t.length] = x;
		return r;
	}

	public static int[] concat(int[] t1, int[] t2) {
		int[] r = new int[t1.length + t2.length];
		System.arraycopy(t1, 0, r, 0, t1.length);
		System.arraycopy(t2, 0, r, t1.length, t2.length);
		return r;
	}

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

	public static String toString(int[] t) {
		return toString(t, " ");
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
	
	public static int[] inc(int[] table) { return map(table, x -> x+1); }
	public static int[] dec(int[] table) { return map(table, x -> x-1); }
	
	public static void println(int[] t) { System.out.println(toString(t, " ")); }
	public static void println(int[] t, String sep) { System.out.println(toString(t, sep)); }
}
