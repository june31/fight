package tools.collections.map;

import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("serial")
public class Mss extends TreeMap<String, String> {
	
	public Mss() {}
	public Mss(Map<String, String> m) { super(m); }
	
	public static Mss of(String... ss) {
		Mss m = new Mss();
		for (int i = 0; i < ss.length; i += 2) {
			m.put(ss[i], ss[i + 1]);
		}
		return m;
	}
	
	public String getOrEmpty(String s) {
		String n = get(s);
		return n == null ? "" : n;
	}
}
