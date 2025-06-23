package tools.tuple;

import java.util.Objects;

public class PS {
	public Pos p;
	public String s;

	public PS(Pos p, String s) {
		this.p = p;
		this.s = s;
	}

	public PS(PS other) {
		this.p = new Pos(other.p);
		this.s = other.s;
	}

	@Override
	public String toString() {
		return "(" + p + ", " + s + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		PS other = (PS) obj;
		return Objects.equals(p, other.p) && Objects.equals(s, other.s);
	}

	@Override
	public int hashCode() {
		return Objects.hash(p, s);
	}
}
