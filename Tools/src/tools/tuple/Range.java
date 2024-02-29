package tools.tuple;

public class Range implements Comparable<Range> {
	public long a;
	public long b;
	public Range() {}
	public Range(long[] t) { this.a = t[0]; this.b = t[1]; }
	public Range(int[] t) { this.a = t[0]; this.b = t[1]; }
	public Range(long a, long b) { this.a = a; this.b = b; }
	public String toString() { return "[" + a + "," + b + "]"; }
	public int hashCode() {	return (int) (Long.rotateLeft(a, 16) ^ b); }
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Range other = (Range) obj;
		return b == other.b && a == other.a;
	}
	
	@Override
	public int compareTo(Range o) {
		if (a < o.a) return -1;
		if (a > o.a) return 1;
		if (b < o.b) return -1;
		if (b > o.b) return 1;
		return 0;
	}

	public long size() {
		return b - a;
	}
	
	public Range intersected(Range r) {
		if (a > r.b || b < r.a) return null;
		return new Range(Math.max(a, r.a), Math.min(b, r.b));
	}
}
