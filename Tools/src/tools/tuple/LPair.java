package tools.tuple;

public class LPair {
	public long a;
	public long b;
	public LPair() {}
	public LPair(long a, long b) { this.a = a; this.b = b; }
	public String toString() { return "(" + a + ":" + b + ")"; }
	public int hashCode() {	return (int) (Long.rotateLeft(a, 16) ^ b); }
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		LPair other = (LPair) obj;
		return b == other.b && a == other.a;
	}
}
