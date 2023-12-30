package tools.tables;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import tools.function.BiIntConsumer;
import tools.function.BiIntToIntFunction;
import tools.function.IntIntObjConsumer;
import tools.function.IntToIntFunction;
import tools.function.LongToLongFunction;
import tools.function.TriIntConsumer;
import tools.function.TriIntPredicate;
import tools.function.TriIntToIntFunction;
import tools.tuple.II;
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

	public static List<Integer> toList(int[] table) {
		List<Integer> l = new ArrayList<>();
		for (int i: table) l.add(i);
		return l;
	}

	public static List<Long> toList(long[] table) {
		List<Long> l = new ArrayList<>();
		for (long i: table) l.add(i);
		return l;
	}

	public static List<Double> toList(double[] table) {
		List<Double> l = new ArrayList<>();
		for (double d: table) l.add(d);
		return l;
	}

	public static List<Boolean> toList(boolean[] table) {
		List<Boolean> l = new ArrayList<>();
		for (boolean b : table) l.add(b);
		return l;
	}
	
	public static int[] toIntArray(byte[] table) {
		int[] t = new int[table.length];
		for (int i = 0; i < table.length; i++) t[i] = table[i];
		return t;
	}

	public static int[] toIntArray(String s) {
		return toIntArray(s.getBytes());
	}
	

	public static int[] toIntArray(List<Integer> l) {
		int[] t = new int[l.size()];
		for (int i = 0; i < t.length; i++) t[i] = l.get(i);
		return t;
	}

	public static boolean[] toBooleanArray(int[] table, IntPredicate ip) {
		boolean[] t = new boolean[table.length];
		for (int i = 0; i < table.length; i++) t[i] = ip.test(table[i]);
		return t;
	}

	public static String toASCIIString(int[] table) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < table.length; i++) sb.append((char) table[i]);
		return sb.toString();
	}

	public static int[][] toMap(String[] table) {
		int[][] t = new int[table.length][table[0].length()];
		for (int i = 0; i < table.length; i++) {
			byte[] bytes = table[i].getBytes();
			for (int j = 0; j < bytes.length; j++) t[i][j] = bytes[j];
		}
		return t;
	}

	public static void printMap(int[][] map) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				int c = map[i][j] == 0 ? ' ' : (map[i][j] < 10 ? '0' + Math.abs(map[i][j]) : map[i][j]);
				sb.append((char) c);
			}
			sb.append('\n');
		}
		System.out.print(sb);
	}
	
	public static void printArray(int[][] table) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[0].length; j++) {
				if (j != 0) sb.append(", ");
				sb.append(table[i][j]);
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

	public static String toString(int[][] map) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				sb.append((char) map[i][j]);
			}
			sb.append('\n');
		}
		return sb.toString();
	}
	public static void debugMapCL(int[][] map) {
		System.err.println(map[0].length + " " + map.length);
		debugMap(map);
	}

	public static void debugMapLC(int[][] map) {
		System.err.println(map.length + " " + map[0].length);
		debugMap(map);
	}

	public static void fill(int[] table, int value) {
		for (int i = 0; i < table.length; i++) table[i] = value;
	}

	public static void fill(long[] table, long value) {
		for (int i = 0; i < table.length; i++) table[i] = value;
	}

	public static void fill(int[][] map, int value) {
		for (int i = 0; i < map.length; i++) for (int j = 0; j < map[0].length; j++) map[i][j] = value;
	}

	public static void fill(long[][] map, long value) {
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
		for (int i = 0; i < table.length; i++) t[i] = f.applyAsInt(table[i]);
		return t;
	}

	public static int[][] map(int[][] map, IntToIntFunction f) {
		int[][] m = new int[map.length][map[0].length];
		for (int l = 0; l < map.length; l++)
			for (int c = 0; c < map[0].length; c++)
				m[l][c] = f.applyAsInt(map[l][c]);
		return m;
	}

	/** BiIntToIntFunction is (l, c) -> v' */
	public static int[][] map(int[][] map, BiIntToIntFunction f) {
		int[][] m = new int[map.length][map[0].length];
		for (int l = 0; l < map.length; l++)
			for (int c = 0; c < map[0].length; c++)
				m[l][c] = f.applyAsInt(l, c);
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

	public static Pos[] findAndSort(int[][] map, IntPredicate p) {
		Map<Integer, Pos> m = new TreeMap<>();
		for (int l = 0; l < map.length; l++)
			for (int c = 0; c < map[0].length; c++)
				if (p.test(map[l][c])) m.put(map[l][c], new Pos(l, c));
		return m.values().toArray(new Pos[0]);
	}

	public static Pos[] findAndSort(int[][] map, TriIntPredicate p) {
		Map<Integer, Pos> m = new TreeMap<>();
		for (int l = 0; l < map.length; l++)
			for (int c = 0; c < map[0].length; c++)
				if (p.test(l, c, map[l][c])) m.put(map[l][c], new Pos(l, c));
		return m.values().toArray(new Pos[0]);
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

	public static void forEach(int[] table, IntConsumer f) {
		for (int i = 0; i < table.length; i++) f.accept(table[i]);
	}

	public static <A> void forEach(A[] table, Consumer<A> f) {
		for (int i = 0; i < table.length; i++) f.accept(table[i]);
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

	public static <A> void forEach(A[][] map, Consumer<A> f) {
		for (int l = 0; l < map.length; l++)
			for (int c = 0; c < map[0].length; c++)
				f.accept(map[l][c]);
	}

	public static <A> void forEach(A[][] map, BiIntConsumer f) {
		for (int l = 0; l < map.length; l++)
			for (int c = 0; c < map[0].length; c++)
				f.accept(l, c);
	}

	public static <A> void forEach(A[][] map, IntIntObjConsumer<A> f) {
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
		List<Integer> l = new ArrayList<>();
		for (int i: t) l.add(i); 
		return l;
	}

	public static List<Long> longs(long[] t) {
		List<Long> l = new ArrayList<>();
		for (long i: t) l.add(i); 
		return l;
	}

	public static List<Double> doubles(double[] t) {
		List<Double> l = new ArrayList<>();
		for (double i: t) l.add(i); 
		return l;
	}

	public static List<String> strings(String[] t) {
		List<String> l = new ArrayList<>();
		for (String i: t) l.add(i); 
		return l;
	}

	public static <A> List<A> objects(A[] t) {
		List<A> l = new ArrayList<>();
		for (A i: t) l.add(i); 
		return l;
	}

	public static String toString(int[] t) {
		return toString(t, " ");
	}

	public static <A> String toString(List<A> l) {
		return toString(l, " ");
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

	public static String toString(long[] t, String sep) {
		boolean first = true;
		StringBuilder sb = new StringBuilder();
		for (long a: t) {
			if (!first) sb.append(sep);
			first = false;
			sb.append(a);
		}
		return sb.toString();
	}

	public static String toString(double[] t, String sep) {
		boolean first = true;
		StringBuilder sb = new StringBuilder();
		for (double a: t) {
			if (!first) sb.append(sep);
			first = false;
			sb.append(a);
		}
		return sb.toString();
	}

	public static String toString(boolean[] t) {
		StringBuilder sb = new StringBuilder();
		for (boolean a: t) sb.append(a?'1':'0');
		return sb.toString();
	}

	public static String toString(boolean[][] t) {
		StringBuilder sb = new StringBuilder();
		for (boolean[] as: t) {
			for (boolean a: as) sb.append(a?'1':'0');
			sb.append('\n');
		}
		return sb.toString();
	}

	public static <A> String toString(A[] t, String sep) {
		boolean first = true;
		StringBuilder sb = new StringBuilder();
		for (A a: t) {
			if (!first) sb.append(sep);
			first = false;
			sb.append(a);
		}
		return sb.toString();
	}

	public static <A> String toString(List<A> l, String sep) {
		boolean first = true;
		StringBuilder sb = new StringBuilder();
		for (A a: l) {
			if (!first) sb.append(sep);
			first = false;
			sb.append(a);
		}
		return sb.toString();
	}

	public static int[] from(Collection<Integer> l) {
		return l.stream().mapToInt(i->i).toArray();
	}
	
	public static int[] retainFromValues(int[] table, IntPredicate f) {
		List<Integer> l = new ArrayList<>();
		for (int x : table) if (f.test(x)) l.add(x);
		return l.stream().mapToInt(i->i).toArray();
	}

	public static int[] retainFromIndexes(int[] table, IntPredicate f) {
		List<Integer> l = new ArrayList<>();
		for (int i = 0; i < table.length; i++) if (f.test(i)) l.add(table[i]);
		return l.stream().mapToInt(i->i).toArray();
	}

	@SuppressWarnings("unchecked")
	public static <A> A[] retainFromValues(A[] table, Predicate<A> f) {
		if (table.length == 0) return table;
		List<A> l = new ArrayList<>();
		for (A x : table) if (f.test(x)) l.add(x);
		A[] t = (A[]) (Array.newInstance(table[0].getClass(), l.size()));
		for (int i = 0; i < t.length; i++) t[i] = l.get(i);
		return t;
	}

	@SuppressWarnings("unchecked")
	public static <A> A[] retainFromIndexes(A[] table, IntPredicate f) {
		if (table.length == 0) return table;
		List<A> l = new ArrayList<>();
		for (int i = 0; i < table.length; i++) if (f.test(i)) l.add(table[i]);
		A[] t = (A[]) (Array.newInstance(table[0].getClass(), l.size()));
		for (int i = 0; i < t.length; i++) t[i] = l.get(i);
		return t;
	}

	public static <A> List<A> retainFromValues(List<A> list, Predicate<A> f) {
		List<A> l = new ArrayList<>();
		for (A x : list) if (f.test(x)) l.add(x);
		return l;
	}

	public static <A> List<A> retainFromIndexes(List<A> list, IntPredicate f) {
		List<A> l = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) if (f.test(i)) l.add(list.get(i));
		return l;
	}

	public static <A> List<A> retainToList(A[] table, Predicate<A> f) {
		List<A> l = new ArrayList<>();
		for (A x : table) if (f.test(x)) l.add(x);
		return l;
	}

	public static <A> List<A> retainToListFromIndexes(A[] table, IntPredicate f) {
		List<A> l = new ArrayList<>();
		for (int i = 0; i < table.length; i++) if (f.test(i)) l.add(table[i]);
		return l;
	}

	public static int[] inc(int[] table) { return map(table, x -> x+1); }
	public static int[] dec(int[] table) { return map(table, x -> x-1); }
	
	public static void println(int[] t) { System.out.println(toString(t, " ")); }
	public static void println(long[] t) { System.out.println(toString(t, " ")); }
	public static void println(double[] t) { System.out.println(toString(t, " ")); }
	public static void println(boolean[] t) { System.out.println(toString(t)); }
	public static void println(boolean[][] t) { System.out.println(toString(t)); }
	public static void println(int[] t, String sep) { System.out.println(toString(t, sep)); }
	public static <A> void println(A[] t) { System.out.println(toString(t, " ")); }
	public static <A> void println(A[] t, String sep) { System.out.println(toString(t, sep)); }
	public static <A> void println(List<A> l) { System.out.println(toString(l, " ")); }
	public static <A> void println(List<A> l, String sep) { System.out.println(toString(l, sep)); }

	public static int[] copy(int[] table) {
		return table.clone();
	}

	public static char[] copy(char[] table) {
		return table.clone();
	}

	public static long[] copy(long[] table) {
		return table.clone();
	}

	public static double[] copy(double[] table) {
		return table.clone();
	}

	public static boolean[] copy(boolean[] table) {
		return table.clone();
	}
	
	public static int[][] copy(int[][] map) {
		int[][] m = new int[map.length][];
		for (int i = 0; i < m.length; i++) m[i] = map[i].clone();
		return m;
	}

	public static long[][] copy(long[][] map) {
		long[][] m = new long[map.length][];
		for (int i = 0; i < m.length; i++) m[i] = map[i].clone();
		return m;
	}

	public static boolean[][] copy(boolean[][] map) {
		boolean[][] m = new boolean[map.length][];
		for (int i = 0; i < m.length; i++) m[i] = map[i].clone();
		return m;
	}

    public static int[] reverse(int[] t) {
    	int l = t.length;
    	int[] r = new int[l];
    	for (int i = 0; i < l; i++) r[i] = t[l - 1 - i];
        return r;
    }

    public static <K, V> Map<V, K> reverse(Map<K, V> map) {
        Map<V, K> rev = new HashMap<>();
        for (Map.Entry<K, V> entry : map.entrySet()) rev.put(entry.getValue(), entry.getKey());
        return rev;
    }
    
    public static int[] indexes(int[] t) {
    	II[] ii = new II[t.length];
    	for (int i = 0; i < t.length; i++) {
			ii[i] = new II(i, t[i]);
		}
    	Arrays.sort(ii, (i1, i2) -> i1.value - i2.value);
    	int[] ind = new int[t.length];
    	for (int i = 0; i < t.length; i++) {
    		ind[i] = ii[i].index;
    	}
    	return ind;
    }

	public static int[] ascii(String s) {
		int[] t = new int[s.length()];
		char[] c = s.toCharArray();
		for (int i = 0; i < s.length(); i++) t[i] = c[i];
		return t;
	}
	
	public static String fromAscii(int[] t) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < t.length; i++) s.append((char) t[i]);
		return s.toString();
	}

	public static void apply(int[] t, IntToIntFunction f) {
		for (int i = 0; i < t.length; i++) t[i] = f.applyAsInt(t[i]);
	}
	
	public static void applyLong(long[] t, LongToLongFunction f) {
		for (int i = 0; i < t.length; i++) t[i] = f.applyAsLong(t[i]);
	}

	public static int[][] expand(int[] t, int nl) {
		int nc = t.length / nl;
		int[][] res = new int[nl][nc];
		for (int i = 0; i < nl; i++) for (int j = 0; j < nc; j++) res[i][j] = t[i * nl + j];
		return res;
	}

	public static int[] flatten(int[][] t) {
		int nl = t.length;
		int nc = t[0].length;
		int[] res = new int[nl * nc];
		for (int i = 0; i < nl; i++) for (int j = 0; j < nc; j++) res[i * nl + j] = t[i][j];
		return res;
	}
	
	public static long[] flatten(long[][] t) {
		int nl = t.length;
		int nc = t[0].length;
		long[] res = new long[nl * nc];
		for (int i = 0; i < nl; i++) for (int j = 0; j < nc; j++) res[i * nl + j] = t[i][j];
		return res;
	}

	public static double[] flatten(double[][] t) {
		int nl = t.length;
		int nc = t[0].length;
		double[] res = new double[nl * nc];
		for (int i = 0; i < nl; i++) for (int j = 0; j < nc; j++) res[i * nl + j] = t[i][j];
		return res;
	}

	public static boolean[] flatten(boolean[][] t) {
		int nl = t.length;
		int nc = t[0].length;
		boolean[] res = new boolean[nl * nc];
		for (int i = 0; i < nl; i++) for (int j = 0; j < nc; j++) res[i * nl + j] = t[i][j];
		return res;
	}

	public static int[] toIntArray(String[] s) {
		int[] t = new int[s.length];
		for (int i = 0; i < t.length; i++) t[i] = Integer.parseInt(s[i]);
		return t;
	}
	
	public static int get(int[][] map, int l, int c) {
		if (l < 0 || l >= map.length || c < 0 || c >= map[0].length) return -1;
		return map[l][c];
	}

	public static int get(int[][] map, Pos p) {
		if (p.l < 0 || p.l >= map.length || p.c < 0 || p.c >= map[0].length) return -1;
		return map[p.l][p.c];
	}
}
