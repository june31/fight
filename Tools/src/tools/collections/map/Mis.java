package tools.collections.map;

import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("serial")
public class Mis extends TreeMap<Integer, String> {
	public Mis() {}
	public Mis(Map<Integer, String> m) { super(m); }
}