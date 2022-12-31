package tools.tuple;

public class Pos3 {
	public int x;
	public int y;
	public int z;
	public Pos3() {}
	public Pos3(int x, int y, int z) { this.x = x; this.y = y; this.z = z; }
	public String toString() { return "(" + x + ":" + y + ":" + z + ")"; }
	public int hashCode() {	return Integer.rotateLeft(x, 20) ^ Integer.rotateLeft(y, 10) ^ z; }
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Pos3 other = (Pos3) obj;
		return x == other.x && y == other.y && z == other.z;
	}
}
