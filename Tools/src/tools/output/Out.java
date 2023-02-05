package tools.output;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class Out {
	public static boolean SYSTEM_OUT = true;
	public static File OUTPUT_FILE = new File("input.txt");
	
	private static PrintStream stream;
	private static StringBuilder sb = new StringBuilder();
	private static String space = " ";
	private static boolean first = true;
	
	static { reset(); }
	
	public static void reset() {
		if (SYSTEM_OUT) stream = System.out;
		else try { stream = new PrintStream(OUTPUT_FILE); }
		catch (IOException ex) { throw new Error("Could not write " + OUTPUT_FILE.getAbsolutePath() + ".", ex); }
	}
	
	public static void print(Object o) { flush(); stream.print(o); }
	public static void println(Object o) { flush(); stream.println(o); }
	public static void println() { flush(); stream.println(); }
	public static void space(Object o) { if (!first) sb.append(space); sb.append(o); first = false; }
	public static void setSeparator(String s) { space = s; }
	public static void buf(Object o) { sb.append(o); }
	public static void bufln(Object o) { sb.append(o + "\n"); }
	public static void bufln() { sb.append('\n'); first = true; }
	public static void flush() { stream.print(sb); clear(); }
	public static void flushln() { bufln(); stream.print(sb); clear(); }
	public static void clear() { sb = new StringBuilder(); first = true; }
}
