package tools.scanner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.function.Consumer;

public class Scan {
	private static final boolean SYSTEM_IN = false;
	private static final File INPUT_FILE = new File("input.txt");
	
	private static BufferedReader br;
	private static StringTokenizer st;
	
	static {
		if (SYSTEM_IN) br = new BufferedReader(new InputStreamReader(System.in));
		else try { br = new BufferedReader(new FileReader(INPUT_FILE)); }
		catch (IOException ex) { } // Assume open() will be used.
	}

	public static String readString() {
		while (st == null || !st.hasMoreElements())
			try { st = new StringTokenizer(br.readLine()); }
			catch (IOException ex) { throw new Error("Scan reached EOF."); }
		return st.nextToken();
	}
	
	public static String readLine() {
		try { return st.hasMoreTokens() ? st.nextToken("\n") : br.readLine(); }
		catch (IOException e) { throw new Error("Scan reached EOF."); }
	}

	public static int readInt() { return Integer.parseInt(readString()); }
	
	public static long readLong() { return Long.parseLong(readString()); }
	
	public static double readDouble() { return Double.parseDouble(readString()); }
	
	public static String[] readDtringArray() {
		int size = readInt();
		String[] res = new String[size];
		for (int i = 0; i < size; i++) res[i] = readString();
		return res;
	}

	public static int[] readIntArray() {
		int size = readInt();
		int[] res = new int[size];
		for (int i = 0; i < size; i++) res[i] = readInt();
		return res;
	}
	
	public static long[] readLongArray() {
		int size = readInt();
		long[] res = new long[size];
		for (int i = 0; i < size; i++) res[i] = readLong();
		return res;
	}

	public static double[] readDoubleArray() {
		int size = readInt();
		double[] res = new double[size];
		for (int i = 0; i < size; i++) res[i] = readDouble();
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

	public static void loop(Runnable r) {
		int loops = readInt();
		for (int i = 0; i < loops; i++) r.run();
	}
	
	public static void loop0(Consumer<Integer> c) {
		int loops = readInt();
		for (int i = 0; i < loops; i++) c.accept(i);
	}

	public static void loop1(Consumer<Integer> c) {
		int loops = readInt();
		for (int i = 1; i <= loops; i++) c.accept(i);
	}
	
	public static void open(String fileName) {
		try { 
			if (br != null) br.close();
			br = new BufferedReader(new FileReader(fileName));
		}
		catch (IOException ex) { throw new Error("Could not read " + new File(fileName).getAbsolutePath() + ".", ex); }
	}
}
