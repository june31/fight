package summer24;

import tools.collections.int32.L;

public class Bow extends MiniGame {
	static {
		for (int i = 0; i < 3 * MAX_DEPTH; i++) bows[i] = new Bow();
	}
	
	public int wind;
	public int totalNext;
	public int eta;
	public int x;
	public int y;
	public int length;
	
	public static void init(String winds, int x0, int y0, int x1, int y1, int x2, int y2) {
		initW(MAX_DEPTH * myIndex, winds, x0, y0);
		initW(MAX_DEPTH * ((myIndex + 2) % 3), winds, x1, y1);
		initW(MAX_DEPTH * ((myIndex + 1) % 3), winds, x2, y2);
	}
	
	public static void initW(int f, String winds, int x, int y) {
		boolean active = winds.charAt(0) != 'G';
		int i = 0;
		L l = new L(winds.toCharArray()).mapped(c -> c - '0');
		if (active) {
			bows[f].x = x;
			bows[f].y = y;
			int limit = Math.min(l.size(), MAX_DEPTH);
			for (; i < limit; i++) {
				Bow b = bows[f + i];
				b.wind = l.get(i);
				b.eta = l.size() - i;
				int next = 0;
				for (int j = i + 1; j < l.size(); j++) next += l.get(j);
				b.totalNext = next;
				b.active = true;
			}
		}
		for (; i < MAX_DEPTH; i++) {
			Bow b = bows[f + i];
			b.active = false;
		}
	}
	
	@Override
	public String toString() {
		return x + " " + y;
	}
}
