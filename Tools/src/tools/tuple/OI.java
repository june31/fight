package tools.tuple;

import java.util.Objects;

public class OI<A> {
	public A o;
	public int i;
	public OI() {}
	public OI(A o, int i) { this.o = o; this.i = i; }
	public String toString() { return "(" + o + ":" + i + ")"; }
	public int hashCode() {	return Integer.rotateLeft(i, 16) ^ o.hashCode(); }
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		OI<A> other = (OI<A>) obj;
		return i == other.i && Objects.equals(o, other.o);
	}
}
