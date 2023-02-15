package tools.tuple;

public class II {
	public int a;
	public int b;
	public II() {}
	public II(int a, int b) { this.a = a; this.b = b; }
	public String toString() { return "(" + a + ":" + b + ")"; }
	public int hashCode() {	return Integer.rotateLeft(a, 16) ^ b; }
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		II other = (II) obj;
		return b == other.b && a == other.a;
	}
}
