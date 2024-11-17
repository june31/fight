package tools.collections.map;

import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("serial")
public class Mos<T> extends TreeMap<T, String> {
	
	public Mos() {}
	public Mos(Map<T, String> m) { super(m); }
}
