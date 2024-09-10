package summer24;

public class Dive extends MiniGame {

	static {
		for (int i = 0; i < 3 * MAX_DEPTH; i++) dives[i] = new Dive();
	}

	public int pattern; 
	public int points;
	public int mul;
	public int eta;
	
	public static void init(String pattern, int p0, int p1, int p2, int mul0, int mul1, int mul2) {
		initD(MAX_DEPTH * myIndex, pattern, p0, mul0);
		initD(MAX_DEPTH * ((myIndex + 2) % 3), pattern, p1, mul1);
		initD(MAX_DEPTH * ((myIndex + 1) % 3), pattern, p2, mul2);
	}
	
	public static void initD(int f, String pattern, int p, int mul) {
		boolean status = pattern.charAt(0) != 'G';
		int limit = Math.min(pattern.length(), MAX_DEPTH);
		for (int i = 0; i < limit; i++) {
			Dive d = dives[f + i];
			d.active = status;
			if (status) {
				char c = pattern.charAt(i);
				d.pattern = c == 'U' ? U : c == 'D' ? D : c == 'R' ? R : L;
				d.eta = pattern.length() - i;
			}
			if (status && ++p == pattern.length()) status = false;
		}
	}
	
	@Override
	public String toString() {
		return dir(pattern) + " points:" + points + " mul:" + mul;
	}
}
