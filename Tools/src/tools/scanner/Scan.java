package tools.scanner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Scan {
	private static final InputStream INPUT = ScanHelper.retrieveInputStream();

	public static int once = -1;
	private static BufferedReader br = new BufferedReader(new InputStreamReader(INPUT));
	private static StringTokenizer st;
	private static boolean debugMode = false;

	public static String readString() {
		try {
			while (st == null || !st.hasMoreElements()) {
				String s = br.readLine();
				if (debugMode) System.err.println(s);
				st = new StringTokenizer(s);
			}
			return st.nextToken();
		} catch (IOException ex) { throw new Error("Scan reached EOF."); }
	}

	public static String readLine() {
		try {  
			if (st == null || !st.hasMoreElements()) {
				String s = br.readLine();
				if (debugMode) System.err.println(s);
				st = new StringTokenizer(s);
			}
			return st.hasMoreElements() ? st.nextToken("\n") : "";
		} catch (Exception ex) { throw new Error("Scan reached EOF."); }
	}

	public static void setDebugMode(boolean mode) { debugMode = mode; }

	public static char readChar() { return readString().charAt(0); }

	public static char[] readChars() { return readLine().toCharArray(); }

	public static int readInt() { return Integer.parseInt(readString()); }

	public static int readBinary() { return Integer.parseInt(readString(), 2); }

	public static int readHex() { return Integer.parseInt(readString(), 16); }

	public static long readLong() { return Long.parseLong(readString()); }

	public static long readBinaryLong() { return Long.parseLong(readString(), 2); }

	public static long readHexLong() { return Long.parseLong(readString(), 16); }

	public static double readDouble() { return Double.parseDouble(readString()); }

	public static String[] readLines() {
		int size = readInt();
		String[] res = new String[size];
		for (int i = 0; i < size; i++) res[i] = readLine();
		return res;
	}

	public static String[] readLineAsStrings() {
		return readLine().split(" ");
	}

	public static int[] readInts() {
		int size = readInt();
		int[] res = new int[size];
		for (int i = 0; i < size; i++) res[i] = readInt();
		return res;
	}

	public static int[][] readIntArray(int h, int w) {
		int[][] res = new int[h][w];
		for (int i = 0; i < h; i++) for (int j = 0; j < w; j++)	res[i][j] = readInt();
		return res;
	}

	public static int[] readLineAsInts() {
		String[] strs = readLine().split(" ");
		int[] res = new int[strs.length];
		for (int i = 0; i < strs.length; i++) res[i] = Integer.parseInt(strs[i]);
		return res;
	}

	public static long[] readLongs() {
		int size = readInt();
		long[] res = new long[size];
		for (int i = 0; i < size; i++) res[i] = readLong();
		return res;
	}

	public static long[] readLineAsLongs() {
		String[] strs = readLine().split(" ");
		long[] res = new long[strs.length];
		for (int i = 0; i < strs.length; i++) res[i] = Long.parseLong(strs[i]);
		return res;
	}

	public static double[] readDoubles() {
		int size = readInt();
		double[] res = new double[size];
		for (int i = 0; i < size; i++) res[i] = readDouble();
		return res;
	}

	public static double[] readLineAsDoubles() {
		String[] strs = readLine().split(" ");
		double[] res = new double[strs.length];
		for (int i = 0; i < strs.length; i++) res[i] = Double.parseDouble(strs[i]);
		return res;
	}

	public static String[] readLines(int size) {
		String[] res = new String[size];
		for (int i = 0; i < size; i++) res[i] = readLine();
		return res;
	}

	public static String[] readStrings(int size) {
		String[] res = new String[size];
		for (int i = 0; i < size; i++) res[i] = readString();
		return res;
	}

	public static int[] readInts(int size) {
		int[] res = new int[size];
		for (int i = 0; i < size; i++) res[i] = readInt();
		return res;
	}

	public static long[] readLongs(int size) {
		long[] res = new long[size];
		for (int i = 0; i < size; i++) res[i] = readLong();
		return res;
	}

	public static double[] readDoubles(int size) {
		double[] res = new double[size];
		for (int i = 0; i < size; i++) res[i] = readDouble();
		return res;
	}

	public static void open(String fileName) {
		if (INPUT == System.in) return;
		try { 
			if (br != null) br.close();
			br = new BufferedReader(new FileReader(fileName));
		}
		catch (IOException ex) { throw new Error("Could not read " + new File(fileName).getAbsolutePath() + ".", ex); }
	}

	public static String[] readRaw() {
		List<String> l = new ArrayList<>();
		try {
			String s;
			while (!(s = readLine()).isEmpty()) l.add(s);
		} catch (Throwable t) {}
		return l.isEmpty() ? null : l.toArray(new String[0]);
	}

	public static String[] readRawStrings() {
		List<String> l = new ArrayList<>();
		try {
			String line;
			while (!(line = readLine()).isEmpty()) for (String s: line.split(" ")) l.add(s);
		} catch (Throwable t) {}
		return l.isEmpty() ? null : l.toArray(new String[0]);
	}

	public static int[] readRawInts() {
		List<Integer> l = new ArrayList<>();
		try {
			String s;
			while (!(s = readLine()).isEmpty()) l.add(Integer.parseInt(s));
		} catch (Throwable t) {}
		return l.isEmpty() ? null : l.stream().mapToInt(i->i).toArray();
	}

	public static int[] readIntLine() {
		String[] s = readLine().split(" +");
		int[] r = new int[s.length];
		for (int i = 0; i < s.length; i++) r[i] = Integer.parseInt(s[i]);
		return r;
	}

	public static long[] readLongLine() {
		String[] s = readLine().split(" +");
		long[] r = new long[s.length];
		for (int i = 0; i < s.length; i++) r[i] = Long.parseLong(s[i]);
		return r;
	}

	public static double[] readDoubleLine() {
		String[] s = readLine().split(" +");
		double[] r = new double[s.length];
		for (int i = 0; i < s.length; i++) r[i] = Double.parseDouble(s[i]);
		return r;
	}

	public static int[][] readMap0() {
		String[] table = readRaw();
		int[][] t = new int[table.length][table[0].length()];
		for (int i = 0; i < table.length; i++) {
			byte[] bytes = table[i].getBytes();
			for (int j = 0; j < bytes.length; j++) t[i][j] = bytes[j];
		}
		return t;
	}

	public static int[][] readMap(int l) {
		String[] table = readLines(l);
		int[][] t = new int[l][table[0].length()];
		for (int i = 0; i < l; i++) {
			byte[] bytes = table[i].getBytes();
			for (int j = 0; j < bytes.length; j++) t[i][j] = bytes[j];
		}
		return t;
	}

	public static int[][] readMap1() {
		String[] table = readLines();
		int[][] t = new int[table.length][table[0].length()];
		for (int i = 0; i < table.length; i++) {
			byte[] bytes = table[i].getBytes();
			for (int j = 0; j < bytes.length; j++) t[i][j] = bytes[j];
		}
		return t;
	}

	public static int[][] readMapCL() {
		Scan.readInt();
		return readMap1();
	}

	public static int[][] readMap2() {
		int size = readInt();
		readInt();
		String[] table = new String[size];
		for (int i = 0; i < size; i++) table[i] = readLine();
		int[][] t = new int[table.length][table[0].length()];
		for (int i = 0; i < table.length; i++) {
			byte[] bytes = table[i].getBytes();
			for (int j = 0; j < bytes.length; j++) t[i][j] = bytes[j];
		}
		return t;
	}
	
	public static int[][] reaIntdMap0() {
		String[] table = readRaw();
		int[][] t = new int[table.length][table[0].length()];
		for (int i = 0; i < table.length; i++) {
			byte[] bytes = table[i].getBytes();
			for (int j = 0; j < bytes.length; j++) t[i][j] = bytes[j] - '0';
		}
		return t;
	}

	public static int[][] readIntMap(int l) {
		String[] table = readLines(l);
		int[][] t = new int[l][table[0].length()];
		for (int i = 0; i < l; i++) {
			byte[] bytes = table[i].getBytes();
			for (int j = 0; j < bytes.length; j++) t[i][j] = bytes[j] - '0';
		}
		return t;
	}

	public static int[][] readIntMap1() {
		String[] table = readLines();
		int[][] t = new int[table.length][table[0].length()];
		for (int i = 0; i < table.length; i++) {
			byte[] bytes = table[i].getBytes();
			for (int j = 0; j < bytes.length; j++) t[i][j] = bytes[j] - '0';
		}
		return t;
	}

	public static int[][] readIntMapCL() {
		Scan.readInt();
		return readIntMap1();
	}

	public static int[][] reaIntdMap2() {
		int size = readInt();
		readInt();
		String[] table = new String[size];
		for (int i = 0; i < size; i++) table[i] = readLine();
		int[][] t = new int[table.length][table[0].length()];
		for (int i = 0; i < table.length; i++) {
			byte[] bytes = table[i].getBytes();
			for (int j = 0; j < bytes.length; j++) t[i][j] = bytes[j] - '0';
		}
		return t;
	}

	public static void readIntArrays(int n, int[]... ts) {
		for (int i = 0; i < n; i++) for (int[] t: ts) t[i] = readInt();
	}

	public static void readLongArrays(int n, long[]... ts) {
		for (int i = 0; i < n; i++) for (long[] t: ts) t[i] = readLong();
	}

	public static void readDoubleArrays(int n, double[]... ts) {
		for (int i = 0; i < n; i++) for (double[] t: ts) t[i] = readDouble();
	}
	
	public static int readOnce() {
		if (once < 0) once = readInt();
		return once;
	}
	
	public static int readTime() {
		List<Integer> l = new ArrayList<>();
		String[] tk = readString().split("[^-\\d]+");
		for (String e: tk) if (!e.isEmpty()) try {
			l.add(Integer.parseInt(e));
		} catch (NumberFormatException ex) {}
		if (l.size() != 2) throw new NumberFormatException("Invalid time format");
		return l.get(0) * 60 + l.get(1);
	}
	
	public static boolean isEclipse() {
		return INPUT != System.in;
	}
}
