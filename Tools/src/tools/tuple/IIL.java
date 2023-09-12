package tools.tuple;

public class IIL {
	public int line;
	public int col;
	public long value;
	public IIL() {}
	public IIL(int i, int j, long v) { this.line = i; this.col = i; value = v; }
	public String toString() { return "(" + line + "," + col + ":" + value + ")"; }
	public int hashCode() {	return Integer.rotateLeft(line, 10) ^ Integer.rotateLeft(col, 20) ^ (int) value; }
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		IIL other = (IIL) obj;
		return line == other.line && col == other.col && value == other.value;
	}
}
