package tools.output;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class Out {
	public static boolean SYSTEM_OUT = true;
	public static File OUTPUT_FILE = new File("input.txt");
	
	private static PrintStream stream;
	private static StringBuilder sb = new StringBuilder();
	
	static { reset(); }
	
	public static void reset() {
		if (SYSTEM_OUT) stream = System.out;
		else try { stream = new PrintStream(OUTPUT_FILE); }
		catch (IOException ex) { throw new Error("Could not write " + OUTPUT_FILE.getAbsolutePath() + ".", ex); }
	}
	
	public static void print(Object o) { flush(); stream.print(o); }
	public static void println(Object o) { flush(); stream.println(o); }
	public static void println() { flush(); stream.println('\n'); }
	public static void buf(Object o) { sb.append(o); }
	public static void bufln(Object o) { sb.append(o + "\n"); }
	public static void bufln() { sb.append('\n'); }
	public static void flush() { stream.print(sb); sb = new StringBuilder(); }
}
