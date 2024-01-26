package tools.strings;

import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;

import tools.function.IntToCharFunction;

public class S {
	public static String mul(String s, int n) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) sb.append(s);
		return sb.toString();
	}

	public static String mul(String s, int n, String sep) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) sb.append(i == 0 ? s : sep + s);
		return sb.toString();
	}

	public static String mul(char c, int n) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) sb.append(c);
		return sb.toString();
	}

	public static String mul(char c, int n, String sep) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) sb.append(i == 0 ? "" + c : sep + c);
		return sb.toString();
	}

	public static String genInts(int n, IntUnaryOperator o) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) sb.append(o.applyAsInt(i));
		return sb.toString();
	}

	public static String genInts(int n, String sep, IntUnaryOperator o) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) sb.append((i == 0 ? "" : sep) + o.applyAsInt(i));
		return sb.toString();
	}

	public static String genChars(int n, IntToCharFunction f) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) sb.append(f.applyAsChar(i));
		return sb.toString();
	}

	public static String genChars(int n, String sep, IntToCharFunction f) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) sb.append((i == 0 ? "" : sep) + f.applyAsChar(i));
		return sb.toString();
	}

	public static String genStrings(int n, IntFunction<String> f) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) sb.append(f.apply(i));
		return sb.toString();
	}

	public static String genStrings(int n, String sep, IntFunction<String> f) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) sb.append((i == 0 ? "" : sep) + f.apply(i));
		return sb.toString();
	}

	public static int count(String s, String sub) {
		int n = 0;
		int p = 0;
		int i;
		do {
			i = s.indexOf(sub, p);
			if (i != -1) {
				p = i + sub.length();
				n++;
			}
		} while (i != -1);
		return n;
	}

	public static int count(String s, char c) {
		int n = 0;
		int p = 0;
		int i;
		do {
			i = s.indexOf(c, p);
			if (i != -1) {
				p = i + 1;
				n++;
			}
		} while (i != -1);
		return n;
	}
	
	public static void p(Object o) {
		System.out.print(o);
	}

	public static void o(Object o) {
		System.out.println(o);
	}

	public static void o() {
		System.out.println();
	}

	public static void e(Object o) {
		System.err.println(o);
	}
	
	public static void e() {
		System.err.println();
	}
}
