package tools.tuple;

public class DD implements Comparable<DD> {
	public double a;
	public double b;
	public DD() {}
	public DD(double[] t) { this.a = t[0]; this.b = t[1]; }
	public DD(long[] t) { this.a = t[0]; this.b = t[1]; }
	public DD(int[] t) { this.a = t[0]; this.b = t[1]; }
	public DD(double a, double b) { this.a = a; this.b = b; }
	public String toString() { return "[" + a + ", " + b + "]"; }
	public int hashCode() {	return Double.hashCode(a + 173 * b); }
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		DD other = (DD) obj;
		return b == other.b && a == other.a;
	}
	
	@Override
	public int compareTo(DD o) {
		if (a < o.a) return -1;
		if (a > o.a) return 1;
		if (b < o.b) return -1;
		if (b > o.b) return 1;
		return 0;
	}

	public double size() {
		return b - a;
	}
	
	public DD intersected(DD r) {
		if (a > r.b || b < r.a) return null;
		return new DD(Math.max(a, r.a), Math.min(b, r.b));
	}
}
