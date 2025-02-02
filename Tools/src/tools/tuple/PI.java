package tools.tuple;

import java.util.Objects;

public class PI {
	public Pos p;
	public int i;
	public PI() {}
	public PI(Pos p, int i) { this.p = p; this.i = i; }
	public String toString() { return "(" + p + ":" + i + ")"; }
	public int hashCode() {	return Integer.rotateLeft(i, 16) ^ p.hashCode(); }
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		PI other = (PI) obj;
		return i == other.i && Objects.equals(p, other.p);
	}
}
