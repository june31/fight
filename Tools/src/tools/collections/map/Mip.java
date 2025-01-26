package tools.collections.map;

import java.util.Map;
import java.util.TreeMap;

import tools.collections.multi.Lip;
import tools.tuple.IP;
import tools.tuple.Pos;

@SuppressWarnings("serial")
public class Mip extends TreeMap<Integer, Pos> {
	public Mip() {}
	public Mip(Map<Integer, Pos> m) { super(m); }
	
	public static Mip of(Object... vals) {
		Mip m = new Mip();
		for (int i = 0; i < vals.length; i += 2) {
			m.put((Integer) vals[i], (Pos) vals[i + 1]);
		}
		return m;
	}
	
	public Mip(int[][] map) {
		for (int l = 0; l < map.length; l++) {
			for (int c = 0; c < map[0].length; c++) {
				put(map[l][c], new Pos(l, c));
			}
		}
	}
	public Lip maxes() {
		Lip l = new Lip();
		int max = Integer.MIN_VALUE;
		for (int i: keySet()) if (i > max) max = i;
		for (Map.Entry<Integer, Pos> e: entrySet()) if (e.getKey() == max) l.addIP(e.getKey(), e.getValue());
		return l;
	}
	
	public Lip mins() {
		Lip l = new Lip();
		int min = Integer.MAX_VALUE;
		for (int i: keySet()) if (i < min) min = i;
		for (Map.Entry<Integer, Pos> e: entrySet()) if (e.getKey() == min) l.addIP(e.getKey(), e.getValue());
		return l;
	}
	
	public IP max() {
		IP max = new IP(Integer.MIN_VALUE, null);
		for (Map.Entry<Integer, Pos> e: entrySet()) if (e.getKey() > max.i) max = new IP(e.getKey(), e.getValue());
		return max;
	}
	
	public IP min() {
		IP min = new IP(Integer.MAX_VALUE, null);
		for (Map.Entry<Integer, Pos> e: entrySet()) if (e.getKey() < min.i) min = new IP(e.getKey(), e.getValue());
		return min;
	}
}