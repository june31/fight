package tools.tuple;

import java.util.Objects;

public class IP {
	public int i;
	public Pos p;
	public IP() {}
	public IP(int i, Pos p) {  this.i = i; this.p = p; }
	public String toString() { return "(" + i + ":" + p + ")"; }
	public int hashCode() {	return Integer.rotateLeft(i, 16) ^ p.hashCode(); }
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		IP other = (IP) obj;
		return i == other.i && Objects.equals(p, other.p);
	}
}
