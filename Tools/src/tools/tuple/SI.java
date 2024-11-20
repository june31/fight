package tools.tuple;

import java.util.Objects;

public class SI {
	public String s;
	public int i;
	public SI() {}
	public SI(String s, int i) { this.s = s; this.i = i; }
	public String toString() { return "(" + s + ":" + i + ")"; }
	public int hashCode() {	return Integer.rotateLeft(i, 16) ^ s.hashCode(); }
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		SI other = (SI) obj;
		return i == other.i && Objects.equals(s, other.s);
	}
}
