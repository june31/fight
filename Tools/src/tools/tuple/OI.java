package tools.tuple;

import java.util.Objects;

public class OI<A> {
	public A a;
	public int b;
	public OI() {}
	public OI(A a, int b) { this.a = a; this.b = b; }
	public String toString() { return "(" + a + ":" + b + ")"; }
	public int hashCode() {	return Integer.rotateLeft(b, 16) ^ a.hashCode(); }
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		OI<A> other = (OI<A>) obj;
		return b == other.b && Objects.equals(a, other.a);
	}
	
}
