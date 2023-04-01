package tools.tuple;

public class Pos {
	public int l;
	public int c;
	public Pos() {}
	public Pos(Pos p) { this(p.l, p.c); }
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
}
