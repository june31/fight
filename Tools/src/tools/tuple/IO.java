package tools.tuple;

import java.util.Objects;

public class IO<A> {
	public int a;
	public A b;
	public IO() {}
	public IO(int a, A b) { this.a = a; this.b = b; }
	public String toString() { return "(" + a + ":" + b + ")"; }
	public int hashCode() {	return Integer.rotateLeft(a, 16) ^ b.hashCode(); }
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		IO<A> other = (IO<A>) obj;
		return a == other.a && Objects.equals(b, other.b);
	}
	
}
