package challenge.fall2023.model;

import tools.scanner.Scan;

public class Fish {
	
	public static final Fish[] all = new Fish[Scan.readInt()];
	public static final int count = all.length;
	public int id;
    public int color;
	public int type;
	public int l;
	public int c;
	public int vl;
	public int vc;
	public boolean me;
	public boolean foe;
	
	public static void init(int count) {
		
	}
	
	public Fish(int i, int c, int t) {
		id = i;
		color = c;
		type = t;
		all[id] = this;
	}
}
