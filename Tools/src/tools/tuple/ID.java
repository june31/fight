package tools.tuple;

public class ID {
	public int index;
	public double value;
	public ID() {}
	public ID(int a, double b) { this.index = a; this.value = b; }
	public String toString() { return "(" + index + ":" + value + ")"; }
	public int hashCode() {	return Integer.rotateLeft(index, 16) ^ (int) value; }
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		ID other = (ID) obj;
		return value == other.value && index == other.index;
	}
}
