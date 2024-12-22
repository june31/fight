package tools.tuple;

import java.util.Objects;

public class LS {
	public long l;
	public String s;
	public LS() {}
	public LS(long l, String s) {  this.l = l; this.s = s; }
	public String toString() { return "(" + l + ":" + s + ")"; }
	public int hashCode() {	return Integer.rotateLeft((int) l, 16) ^ s.hashCode(); }
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		LS other = (LS) obj;
		return l == other.l && Objects.equals(s, other.s);
	}
}
