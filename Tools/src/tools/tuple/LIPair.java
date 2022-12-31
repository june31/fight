package tools.tuple;

public class LIPair {
	public long a;
	public long b;
	public LIPair() {}
	public LIPair(long a, long b) { this.a = a; this.b = b; }
	public String toString() { return "(" + a + ":" + b + ")"; }
	public int hashCode() {	return Integer.rotateLeft((int) a, 16) ^ (int) b; }
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		LIPair other = (LIPair) obj;
		return b == other.b && a == other.a;
	}
}
