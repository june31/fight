package tools.collections.map;

import java.util.Map;
import java.util.TreeMap;

import tools.collections.multi.Lis;
import tools.tuple.IS;

@SuppressWarnings("serial")
public class Mis extends TreeMap<Integer, String> {
	public Mis() {}
	public Mis(Map<Integer, String> m) { super(m); }
	
	public Lis maxes() {
		Lis l = new Lis();
		int max = Integer.MIN_VALUE;
		for (int i: keySet()) if (i > max) max = i;
		for (Map.Entry<Integer, String> e: entrySet()) if (e.getKey() == max) l.addIS(e.getKey(), e.getValue());
		return l;
	}
	
	public Lis mins() {
		Lis l = new Lis();
		int min = Integer.MAX_VALUE;
		for (int i: keySet()) if (i < min) min = i;
		for (Map.Entry<Integer, String> e: entrySet()) if (e.getKey() == min) l.addIS(e.getKey(), e.getValue());
		return l;
	}
	
	public IS max() {
		IS max = new IS(Integer.MIN_VALUE, null);
		for (Map.Entry<Integer, String> e: entrySet()) if (e.getKey() > max.i) max = new IS(e.getKey(), e.getValue());
		return max;
	}
	
	public IS min() {
		IS min = new IS(Integer.MAX_VALUE, null);
		for (Map.Entry<Integer, String> e: entrySet()) if (e.getKey() < min.i) min = new IS(e.getKey(), e.getValue());
		return min;
	}
}