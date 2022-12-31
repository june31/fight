package tools.tuple;

public class Pair {
	public int a;
	public int b;
	public Pair() {}
	public Pair(int a, int b) { this.a = a; this.b = b; }
	public String toString() { return "(" + a + ":" + b + ")"; }
	public int hashCode() {	return Integer.rotateLeft(a, 16) ^ b; }
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Pair other = (Pair) obj;
		return b == other.b && a == other.a;
	}
}
