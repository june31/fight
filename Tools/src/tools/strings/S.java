package tools.strings;

import java.util.Comparator;

import tools.function.IntToCharFunction;

public class S {

	private static long score = 0;

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

	public static String genInts(int n, java.util.function.IntUnaryOperator o) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) sb.append(o.applyAsInt(i));
		return sb.toString();
	}

	public static String genInts(int n, String sep, java.util.function.IntUnaryOperator o) {
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

	public static String genStrings(int n, java.util.function.IntFunction<String> f) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) sb.append(f.apply(i));
		return sb.toString();
	}

	public static String genStrings(int n, String sep, java.util.function.IntFunction<String> f) {
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

	public static String reverse(String s) {
		return new StringBuilder(s).reverse().toString();
	}

	public static void p(Object o) {
		System.out.print(o);
	}

	public static void p(Object... os) {
		StringBuilder sb = new StringBuilder();
		boolean spc = false; 
		for (Object o: os) {
			sb.append(spc ? " " + o : o);
			spc = true;
		}
		System.out.print(sb);
	}

	public static void o(Object o) {
		System.out.println(o);
	}

	public static void o(Object... os) {
		StringBuilder sb = new StringBuilder();
		boolean spc = false; 
		for (Object o: os) {
			sb.append(spc ? " " + o : o);
			spc = true;
		}
		System.out.println(sb);
	}

	public static void ln(Object... os) {
		StringBuilder sb = new StringBuilder();
		for (Object o: os) sb.append(o + "\n");
		System.out.print(sb);
	}

	public static void o() {
		System.out.println();
	}

	public static void e(Object o) {
		System.err.println(o);
	}

	public static void e(Object... os) {
		StringBuilder sb = new StringBuilder();
		boolean spc = false; 
		for (Object o: os) {
			sb.append(spc ? " " + o : o);
			spc = true;
		}
		System.err.println(sb);
	}

	public static void e() {
		System.err.println();
	}

	public static int i(String s) {
		return Integer.parseInt(s);
	}

	public static long l(String s) {
		return Long.parseLong(s);
	}

	public static double d(String s) {
		return Double.parseDouble(s);
	}

	public static void inc() {
		score++;
	}

	public static void dec() {
		score--;
	}

	public static void add(int i) {
		score += i;
	}

	public static void add(long l) {
		score += l;
	}

	public static void set(int i) {
		score = i;
	}

	public static void set(long l) {
		score = l;
	}

	public static void score() {
		System.out.println(score);
	}

	public static void score(long l) {
		score += l;
		System.out.println(score);
	}

	public static long getScore() {
		return score;
	}

	public static Comparator<String> stringLengthComparator() {
		return (s1, s2) -> {
			int l = Integer.compare(s1.length(), s2.length());
			if (l == 0) return s1.compareTo(s2);
			return l;
		};
	}
	
    public static String[] splitLast(String input, String delimiter) {
        int lastIndex = input.lastIndexOf(delimiter);
        if (lastIndex == -1) {
            return new String[] { input, "" };
        }
        String firstPart = input.substring(0, lastIndex);
        String secondPart = input.substring(lastIndex + delimiter.length());
        return new String[] { firstPart, secondPart };
    }
}
