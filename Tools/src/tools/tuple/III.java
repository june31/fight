package tools.tuple;

public class III {
	public int line;
	public int col;
	public int value;
	public III() {}
	public III(int i, int j, int v) { this.line = i; this.col = i; value = v; }
	public String toString() { return "(" + line + "," + col + ":" + value + ")"; }
	public int hashCode() {	return Integer.rotateLeft(line, 10) ^ Integer.rotateLeft(col, 20) ^ value; }
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		III other = (III) obj;
		return line == other.line && col == other.col && value == other.value;
	}
}
