package tools.tuple;

public class OPair<A, B> {
	public A a;
	public B b;
	public OPair() {}
	public OPair(A a, B b) { this.a = a; this.b = b; }
	public String toString() { return "(" + a + ":" + b + ")"; }
	public int hashCode() {	return Integer.rotateLeft(a.hashCode(), 16) ^ b.hashCode(); }
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		OPair<A, B> other = (OPair<A, B>) obj;
		return b == other.b && a == other.a;
	}
}
