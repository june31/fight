package tools.tuple;

public class II {
	public int i;
	public int v;
	public II() {}
	public II(int i, int v) { this.i = i; this.v = v; }
	public String toString() { return "(" + i + ":" + v + ")"; }
	public int hashCode() {	return Integer.rotateLeft(i, 16) ^ v; }
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		II other = (II) obj;
		return v == other.v && i == other.i;
	}
	public int a() { return i; }
	public int b() { return v; }
}
