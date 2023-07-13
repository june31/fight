package tools.tuple;

public class IL {
	public int index;
	public long value;
	public IL() {}
	public IL(int a, long b) { this.index = a; this.value = b; }
	public String toString() { return "(" + index + ":" + value + ")"; }
	public int hashCode() {	return Integer.rotateLeft(index, 16) ^ (int) value; }
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		IL other = (IL) obj;
		return value == other.value && index == other.index;
	}
}
