package tools.collections.map;

import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("serial")
public class Mso<T> extends TreeMap<String, T> {
	
	public Mso() {}
	public Mso(Map<String, T> m) { super(m); }
}
