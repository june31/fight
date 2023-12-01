package tools.predicate;

public class P {
	public static boolean in(int c, String s) {
		for (char ch: s.toCharArray()) if (c == ch) return true;
		return false;
	}

	public static boolean isDigit(int c) { return c >= '0' && c <= '9'; }
	public static boolean isUpper(int c) { return c >= 'A' && c <= 'Z'; }
	public static boolean isLower(int c) { return c >= 'a' && c <= 'z'; }
	public static boolean isLetter(int c) { return isUpper(c) || isLower(c); }
	public static boolean isAlphaNum(int c) { return isLetter(c) || isDigit(c); }
	
}
