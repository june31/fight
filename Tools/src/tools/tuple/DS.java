package tools.tuple;

import java.util.Objects;

public class DS {
	public double d;
	public String s;
	public DS() {}
	public DS(double d, String s) {  this.d = d; this.s = s; }
	public String toString() { return "(" + d + ":" + s + ")"; }
	public int hashCode() {	return Objects.hash(d, s); }
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		DS other = (DS) obj;
		return d == other.d && Objects.equals(s, other.s);
	}
}
