package tools.tuple;

import java.util.Objects;

public class IO<A> {
	public int i;
	public A o;
	public IO() {}
	public IO(int a, A b) { this.i = a; this.o = b; }
	public String toString() { return "(" + i + ":" + o + ")"; }
	public int hashCode() {	return Integer.rotateLeft(i, 16) ^ o.hashCode(); }
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		IO<A> other = (IO<A>) obj;
		return i == other.i && Objects.equals(o, other.o);
	}
}
