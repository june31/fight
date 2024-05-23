package tools.collections.map;

import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("serial")
public class Mil extends TreeMap<Integer, Long> {
	public Mil() {}
	public Mil(Map<Integer, Long> m) { super(m); }
}