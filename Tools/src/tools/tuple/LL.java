package tools.tuple;

public class LL {
	public long index;
	public long value;
	public LL() {}
	public LL(long index, long value) { this.index = index; this.value = value; }
	public String toString() { return "(" + index + ":" + value + ")"; }
	public int hashCode() {	return (int) (Long.rotateLeft(index, 16) ^ value); }
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		LL other = (LL) obj;
		return value == other.value && index == other.index;
	}
}
