package tools.collections.map;

import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("serial")
public class Mss extends TreeMap<String, String> {
	
	public Mss() {}
	public Mss(Map<String, String> m) { super(m); }
	
	public String getOrEmpty(String s) {
		String n = get(s);
		return n == null ? "" : n;
	}
}