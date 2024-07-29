package tools.tuple;

import java.util.Objects;

public class SS {
	public String a;
	public String b;
	public SS() {}
	public SS(String a, String b) { this.a = a; this.b = b; }
	public String toString() { return "(" + a + ":" + b + ")"; }
	public int hashCode() {	return Integer.rotateLeft(b.hashCode(), 16) ^ a.hashCode(); }
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		SS other = (SS) obj;
		return Objects.equals(a, other.a) && Objects.equals(b, other.b);
	}
}

