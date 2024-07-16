package tools.strings;

public class Regex {
	public static String escape(String s) {
		String[] cs = { "\\", ".", "^", "$", "*", "+", "?", "(", ")", "[", "]", "{", "}", "|" };
		for (String c : cs) s = s.replace(c, "\\" + c);
		return s;
	}
}
