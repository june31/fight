package tools.tuple;

public class III {
	public int i;
	public int j;
	public int value;
	public III() {}
	public III(int i, int j, int v) { this.i = i; this.j = i; value = v; }
	public String toString() { return "(" + i + "," + j + ":" + value + ")"; }
	public int hashCode() {	return Integer.rotateLeft(i, 10) ^ Integer.rotateLeft(j, 20) ^ value; }
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		III other = (III) obj;
		return i == other.i && j == other.j && value == other.value;
	}
}
