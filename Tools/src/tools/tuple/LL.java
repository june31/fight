package tools.tuple;

public class LL implements Comparable<LL> {
	public long a;
	public long b;
	public LL() {}
	public LL(long[] t) { this.a = t[0]; this.b = t[1]; }
	public LL(int[] t) { this.a = t[0]; this.b = t[1]; }
	public LL(long a, long b) { this.a = a; this.b = b; }
	public String toString() { return "[" + a + "," + b + "]"; }
	public int hashCode() {	return (int) (Long.rotateLeft(a, 16) ^ b); }
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		LL other = (LL) obj;
		return b == other.b && a == other.a;
	}
	
	@Override
	public int compareTo(LL o) {
		if (a < o.a) return -1;
		if (a > o.a) return 1;
		if (b < o.b) return -1;
		if (b > o.b) return 1;
		return 0;
	}

	public long size() {
		return b - a;
	}
	
	public LL intersected(LL r) {
		if (a > r.b || b < r.a) return null;
		return new LL(Math.max(a, r.a), Math.min(b, r.b));
	}
}
