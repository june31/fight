package tools.tuple;

public class LI {
	public long l;
	public int i;
	public LI() {}
	public LI(long l, int i) { this.l = l; this.i = i; }
	public String toString() { return "(" + l + ":" + i + ")"; }
	public int hashCode() {	return Integer.rotateLeft((int) l, 16) ^ (int) i; }
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		LI other = (LI) obj;
		return l == other.l && i == other.i;
	}
}
