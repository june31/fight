package tools;

public class T {
	public static boolean isNum(int i) { return i >= '0' && i <= '9'; }
	public static boolean isUppercase(int i) { return i < 'a' || i > 'z'; }
	public static boolean isLowercase(int i) { return i < 'A' || i > 'Z'; }
	public static boolean isAlpha(int i) { return i >= 'A' && i <= 'Z' || i >= 'a' && i <= 'z'; }
	public static boolean isAlphaNum(int i) { return isAlpha(i) || isNum(i); }
	public static boolean isSpace(int i) { return i == ' ' || i == '\t'; }
	
	public static boolean isNotNum(int i) { return !isNum(i); }
	public static boolean isNotUppercase(int i) { return i >= 'a' && i <= 'z'; }
	public static boolean isNotLowercase(int i) { return i >= 'A' && i <= 'Z'; }
	public static boolean isNotAlpha(int i) { return !isAlpha(i); }
	public static boolean isNotAlphaNum(int i) { return !isAlphaNum(i); }
	public static boolean isNotSpace(int i) { return !isSpace(i); }
	
	public static int toUppercase(int i) { return i >= 'a' && i <= 'z' ? (i & 223) : i; }
	public static int toLowercase(int i) { return i >= 'A' && i <= 'Z' ? (i | 32) : i; }
	public static int switchCase(int i) { return isAlpha(i) ? (i ^ 32) : i; }
}
