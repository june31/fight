package tools.tuple;

public class II {
	public int index;
	public int value;
	public II() {}
	public II(int a, int b) { this.index = a; this.value = b; }
	public String toString() { return "(" + index + ":" + value + ")"; }
	public int hashCode() {	return Integer.rotateLeft(index, 16) ^ value; }
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		II other = (II) obj;
		return value == other.value && index == other.index;
	}
}
