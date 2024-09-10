package summer24;

public class Roller extends MiniGame {

	static {
		rollers[0] = new Roller();
		rollers[1] = new Roller();
		rollers[2] = new Roller();
	}
	
	public static int dir1m1;
	public static int dir20;
	public static int dir21;
	public static int dir32;
	public static int eta;
	public static boolean[] dead = new boolean[10];

	public int pos;
	public int stun;
	
	public static void init(String pattern, int p0, int p1, int p2, int stun0, int stun1, int stun2, int turns) {
		eta = turns;
		for (int i = 0; i < 4; i++) {
			char c = pattern.charAt(0);
			dir1m1 = c == 'U' ? U : c == 'D' ? D : c == 'R' ? R : L;
			c = pattern.charAt(1);
			dir20 = c == 'U' ? U : c == 'D' ? D : c == 'R' ? R : L;
			c = pattern.charAt(2);
			dir21 = c == 'U' ? U : c == 'D' ? D : c == 'R' ? R : L;
			c = pattern.charAt(3);
			dir32 = c == 'U' ? U : c == 'D' ? D : c == 'R' ? R : L;
		}
		for (int i = 0; i < 10; i++) dead[i] = false; 
		initR(rollers[myIndex], pattern, p0, stun0);
		initR(rollers[(myIndex + 2) % 3], pattern, p1, stun1);
		initR(rollers[(myIndex + 1) % 3], pattern, p2, stun2);
	}

	private static void initR(Roller r, String pattern, int p, int stun) {
		r.active = pattern.charAt(0) != 'G';
		if (!r.active) return;
		if (stun < 0) {
			dead[p % 10] = true;
			return;
		}
		r.pos = p;
		r.stun = stun;
	}
}
