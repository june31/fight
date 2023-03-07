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
	
	private static BufferedReader br = new BufferedReader(new InputStreamReader(INPUT));
	private static StringTokenizer st;

	public static String readString() {
		while (st == null || !st.hasMoreElements())
			try { st = new StringTokenizer(br.readLine()); }
			catch (IOException ex) { throw new Error("Scan reached EOF."); }
		return st.nextToken();
	}
	
	public static String readLine() {
		try { return st != null && st.hasMoreTokens() ? st.nextToken("\n") : br.readLine(); }
		catch (IOException e) { throw new Error("Scan reached EOF."); }
	}

	public static int readInt() { return Integer.parseInt(readString()); }
	
	public static long readLong() { return Long.parseLong(readString()); }
	
	public static double readDouble() { return Double.parseDouble(readString()); }
	
	public static String[] readStringArray() {
		int size = readInt();
		String[] res = new String[size];
		for (int i = 0; i < size; i++) res[i] = readLine();
		return res;
	}

	public static String[] readLineAsStrings() {
		return readLine().split(" ");
	}

	public static int[] readIntArray() {
		int size = readInt();
		int[] res = new int[size];
		for (int i = 0; i < size; i++) res[i] = readInt();
		return res;
	}

	public static int[] readLineAsInts() {
		String[] strs = readLine().split(" ");
		int[] res = new int[strs.length];
		for (int i = 0; i < strs.length; i++) res[i] = Integer.parseInt(strs[i]);
		return res;
	}

	public static long[] readLongArray() {
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

	public static double[] readDoubleArray() {
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

	public static String[] readStringArray(int size) {
		String[] res = new String[size];
		for (int i = 0; i < size; i++) res[i] = readString();
		return res;
	}
	
	public static int[] readIntArray(int size) {
		int[] res = new int[size];
		for (int i = 0; i < size; i++) res[i] = readInt();
		return res;
	}
	
	public static long[] readLongArray(int size) {
		long[] res = new long[size];
		for (int i = 0; i < size; i++) res[i] = readLong();
		return res;
	}

	public static double[] readDoubleArray(int size) {
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

	public static String[] readRawStrings() {
		List<String> l = new ArrayList<>();
		try {
			String s;
			while (!(s = readLine()).isBlank()) l.add(s);
		} catch (Throwable t) {}
		return l.isEmpty() ? null : l.toArray(new String[0]);
	}

	public static int[] readIntLine() {
		String[] s = readLine().split(" ");
		int[] r = new int[s.length];
		for (int i = 0; i < s.length; i++) r[i] = Integer.parseInt(s[i]);
		return r;
	}

	public static long[] readLongLine() {
		String[] s = readLine().split(" ");
		long[] r = new long[s.length];
		for (int i = 0; i < s.length; i++) r[i] = Long.parseLong(s[i]);
		return r;
	}

	public static int[][] readMap() {
		String[] table = readStringArray();
		int[][] t = new int[table.length][table[0].length()];
		for (int i = 0; i < table.length; i++) {
			byte[] bytes = table[i].getBytes();
			for (int j = 0; j < bytes.length; j++) t[i][j] = bytes[j];
		}
		return t;
	}
	
	public static int[][] readRawMap() {
		String[] table = readRawStrings();
		int[][] t = new int[table.length][table[0].length()];
		for (int i = 0; i < table.length; i++) {
			byte[] bytes = table[i].getBytes();
			for (int j = 0; j < bytes.length; j++) t[i][j] = bytes[j];
		}
		return t;
	}
}
