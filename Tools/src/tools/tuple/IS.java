package tools.tuple;

import java.util.Objects;

public class IS {
	public int i;
	public String s;
	public IS() {}
	public IS(int i, String s) {  this.i = i; this.s = s; }
	public String toString() { return "(" + i + ":" + s + ")"; }
	public int hashCode() {	return Integer.rotateLeft(i, 16) ^ s.hashCode(); }
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		IS other = (IS) obj;
		return i == other.i && Objects.equals(s, other.s);
	}
}
