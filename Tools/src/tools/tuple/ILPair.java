package tools.tuple;

public class ILPair {
	public int a;
	public long b;
	public ILPair() {}
	public ILPair(int a, long b) { this.a = a; this.b = b; }
	public String toString() { return "(" + a + ":" + b + ")"; }
	public int hashCode() {	return Integer.rotateLeft(a, 16) ^ (int) b; }
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		ILPair other = (ILPair) obj;
		return b == other.b && a == other.a;
	}
}
