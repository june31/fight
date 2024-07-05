package summer24;

public class Hurdle extends MiniGame {
	
	static {
		for (int i = 0; i < 3 * MAX_DEPTH; i++) hurdles[i] = new Hurdle();
	}
	
	public static int field; 
	public int pos;
	public int stun;
	
	public static void init(String shape, int p0, int p1, int p2, int stun0, int stun1, int stun2) {
		int image = 0;
		if (shape.charAt(0) != 'G')
			for (int i = 0; i < 30; i++)
				if (shape.charAt(i) == '#')
					image |= 1l << i;
		field = image;
		initH(MAX_DEPTH * myIndex, shape, p0, stun0);
		initH(MAX_DEPTH * ((myIndex + 2) % 3), shape, p1, stun1);
		initH(MAX_DEPTH * ((myIndex + 1) % 3), shape, p2, stun2);
	}
	
	public static void initH(int start, String shape, int p, int stun) {
		Hurdle h = hurdles[start];
		h.active = shape.charAt(0) != 'G';
		h.pos = p;
		h.stun = stun;
	}
	
	@Override
	public String toString() {
		return "pos:" + pos + " stun:" + stun + " state:" + active;
	}
}
