package tools.tuple;

import java.util.ArrayList;
import java.util.List;

public class Pos {
	public int l;
	public int c;
	public Pos() {}
	public Pos(Pos p) { this(p.l, p.c); }
	public Pos(String s) {
		List<Integer> coords = new ArrayList<>();
		for (String e: s.split("[^-\\d]+"))
			if (!e.isEmpty())
				try {
					coords.add(Integer.parseInt(e));
				} catch (NumberFormatException ex) {}
		l = coords.get(0);
		c = coords.get(1);
	}
	public Pos(int line, int col) { l = line; c = col; }
	public String toString() { return "(" + l + ":" + c + ")"; }
	public int hashCode() {	return Integer.rotateLeft(l, 16) ^ c; }
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Pos other = (Pos) obj;
		return c == other.c && l == other.l;
	}
	public Pos reverse() {
		return new Pos(c, l);
	}
}
