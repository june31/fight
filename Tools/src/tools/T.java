package tools;

public class T {
	public static boolean num(int i) { return i >= '0' && i <= '9'; }
	public static boolean uppercase(int i) { return i >= 'A' && i <= 'Z'; }
	public static boolean lowercase(int i) { return i >= 'a' && i <= 'z'; }
	public static boolean alpha(int i) { return uppercase(i) || lowercase(i); }
	public static boolean alphaNum(int i) { return alpha(i) || num(i); }
	public static boolean space(int i) { return i == ' '; }
	
	public static boolean notNum(int i) { return !num(i); }
	public static boolean notUppercase(int i) { return !uppercase(i); }
	public static boolean notLowercase(int i) { return !lowercase(i); }
	public static boolean notAlpha(int i) { return !alpha(i); }
	public static boolean notAlphaNum(int i) { return !alphaNum(i); }
	public static boolean notSpace(int i) { return !space(i); }
}
