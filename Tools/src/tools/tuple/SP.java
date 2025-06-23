package tools.tuple;

public class SP {
	public String s;
	public Pos p;

	public SP(String s, Pos p) {
		this.s = s;
		this.p = p;
	}

	public SP(SP other) {
		this.s = other.s;
		this.p = new Pos(other.p);
	}

	@Override
	public String toString() {
		return "(" + s + ", " + p + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		SP other = (SP) obj;
		return s.equals(other.s) && p.equals(other.p);
	}

	@Override
	public int hashCode() {
		return s.hashCode() * 31 + p.hashCode();
	}
}
