package tools.tuple;

public class Segment implements Comparable<Segment> {
	public int a;
	public int b;
	public Segment() {}
	public Segment(int a, int b) { this.a = a; this.b = b; }
	public String toString() { return "[" + a + "," + b + "]"; }
	public int hashCode() {	return Integer.rotateLeft(a, 16) ^ b; }
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Segment other = (Segment) obj;
		return b == other.b && a == other.a;
	}
	
	@Override
	public int compareTo(Segment o) {
		if (a != o.a) return a - o.a;
		return b - o.b;
	}
}
