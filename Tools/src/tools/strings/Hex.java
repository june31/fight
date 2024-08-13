package tools.strings;

public class Hex {
	public static String toHexDownCase(long l) {
		return Long.toHexString(l).toLowerCase();
	}
	
	public static String toHexDownCase(int i) {
		return Integer.toHexString(i).toLowerCase();
	}
	
	public static String toHexUpCase(long l) {
		return Long.toHexString(l).toUpperCase();
	}
	
	public static String toHexUpCase(int i) {
		return Integer.toHexString(i).toUpperCase();
	}
	
	public static long toLong(String s) {
		return Long.parseLong(s, 16);
	}
	
	public static int toInt(String s) {
		return Integer.parseInt(s, 16);
	}
}
