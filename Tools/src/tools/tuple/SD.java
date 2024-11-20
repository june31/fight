package tools.tuple;

import java.util.Objects;

public class SD {
	public String s;
	public double d;
	public SD() {}
	public SD(String s, double d) { this.s = s; this.d = d; }
	public String toString() { return "(" + s + ":" + d + ")"; }
	public int hashCode() {	return Objects.hash(s, d); }
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		SD other = (SD) obj;
		return d == other.d && Objects.equals(s, other.s);
	}
}
