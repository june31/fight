package summer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import tools.Scanner;

public class CGS_5 {
	/* Ignore and do not change the code below */
	private static final Gson gson = new Gson();

	/**
	 * Try a solution
	 * @param recipe A string respecting the given format to fix the mutant's path.
	 */
	public static void trySolution(String recipe) {
		System.out.println("" + gson.toJson(recipe));
	}

    public static void main(String args[]) {
        try (Scanner in = new Scanner(System.in)) {
            trySolution(findCorrectPath(
                gson.fromJson(in.nextLine(), new TypeToken<String>(){}.getType()),
                gson.fromJson(in.nextLine(), new TypeToken<List<Integer>>(){}.getType()),
                gson.fromJson(in.nextLine(), new TypeToken<List<List<Integer>>>(){}.getType())
            ));
        }
    }

	final static int FORWARD = 1;
	final static int BACK = 2;
	final static int LEFT = 3;
	final static int RIGHT = 4;
	final static Set<Pos> obs = new HashSet<>();
	
	static int n;
	static int[] instr;

	public static String findCorrectPath(String instructions, List<Integer> target, List<List<Integer>> os) {
		
		for (var o: os) {
			obs.add(new Pos(o.get(1), o.get(0)));
		}
		
		n = instructions.length();
		instr = new int[n];
		int i = 0;
		for (char ins: instructions.toCharArray()) {
			if (ins == 'F') instr[i] = FORWARD;
			else if (ins == 'B') instr[i] = BACK;
			else if (ins == 'L') instr[i] = LEFT;
			else instr[i] = RIGHT;
			i++;
		}
		int X = target.get(0);
		int Y = target.get(1);

		int[] xs = new int[n + 1];
		int[] ys = new int[n + 1];
		int[] dxs = new int[n + 1];
		int[] dys = new int[n + 1];

		int dx = 1; 
		int dy = 0; 

		int id = 0;
		for (var ins : instr) {
			dxs[id] = dx;
			dys[id] = dy;
			if (ins == FORWARD) {
				xs[id+1] = xs[id] + dx;
				ys[id+1] = ys[id] + dy;
			}
			else if (ins == BACK) {
				xs[id+1] = xs[id] - dx;
				ys[id+1] = ys[id] - dy;
			}
			else if (ins == LEFT) {
				int tmp = dx;
				dx = -dy;
				dy = tmp;
				xs[id+1] = xs[id];
				ys[id+1] = ys[id];
			}
			else {
				int tmp = dx;
				dx = dy;
				dy = -tmp;
				xs[id+1] = xs[id];
				ys[id+1] = ys[id];
			}
			id++;
		}

		i = 0;
		String s = "?";
		int ins = 0;
		for (; i < n; i++) {
			int x = xs[i];
			int y = ys[i];
			int TDX = X - x;
			int TDY = Y - y;
			int RDX = xs[n] - x;
			int RDY = ys[n] - y;
			dx = dxs[i];
			dy = dys[i];
			ins = instr[i];
			if (ins == FORWARD) {
				if (TDX == RDX - 2 * dx && TDY == RDY - 2 * dy && check(x, y, dx, dy, i, BACK)) { s = "BACK"; break; }
				if (TDX - dy == -RDY && TDY + dx == RDX && check(x, y, dx, dy, i, LEFT)) { s = "TURN LEFT"; break; }
				if (TDX + dy == RDY && TDY - dx == -RDX && check(x, y, dx, dy, i, RIGHT)) { s = "TURN RIGHT"; break; }
			} else if (ins == BACK) {
				if (TDX == RDX + 2 * dx && TDY == RDY + 2 * dy && check(x, y, dx, dy, i, FORWARD)) { s = "FORWARD"; break; }
				if (TDX + dy == -RDY && TDY - dx == RDX && check(x, y, dx, dy, i, LEFT)) { s = "TURN LEFT"; break; }
				if (TDX - dy == RDY && TDY + dx == -RDX && check(x, y, dx, dy, i, RIGHT)) { s = "TURN RIGHT"; break; }
			} else if (ins == LEFT) {
				if (TDX == -RDX && TDY == -RDY && check(x, y, dx, dy, i, RIGHT)) { s = "TURN RIGHT"; break; }
				if (TDX - dx == RDY && TDY - dy == -RDX && check(x, y, dx, dy, i, FORWARD)) { s = "FORWARD"; break; }
				if (TDX + dx == RDY && TDY + dy == -RDX && check(x, y, dx, dy, i, BACK)) { s = "BACK"; break; }
			} else {
				if (TDX == -RDX && TDY == -RDY && check(x, y, dx, dy, i, LEFT)) { s = "TURN LEFT"; break; }
				if (TDX - dx == -RDY && TDY - dy == RDX && check(x, y, dx, dy, i, FORWARD)) { s = "FORWARD"; break; }
				if (TDX + dx == -RDY && TDY + dy == RDX && check(x, y, dx, dy, i, BACK)) { s = "BACK"; break; }
			}
		}
		
		//System.err.println(ins);
		return "Replace instruction " + (i+1) + " with " + s;
	}
	
	public static int ddx, ddy;
	public static final Pos p = new Pos();
	public static boolean check(int x, int y, int dx, int dy, int i, int ins) {
		ddx = dx;
		ddy = dy;
		p.c = x;
		p.l = y;
		apply(ins);
		if (obs.contains(p)) return false;
		for (int j = i+1; j < n; j++) {
			apply(instr[j]);
			if (obs.contains(p)) return false;
		}
		return true;
	}

	private static void apply(int ins) {
		if (ins == FORWARD) {
			p.c += ddx;
			p.l += ddy;
		}
		else if (ins == BACK) {
			p.c -= ddx;
			p.l -= ddy;
		}
		else if (ins == LEFT) {
			int tmp = ddx;
			ddx = -ddy;
			ddy = tmp;
		}
		else {
			int tmp = ddx;
			ddx = ddy;
			ddy = -tmp;
		}
	}
}

class Pos {
	public int l;
	public int c;
	public Pos() {}
	public Pos(Pos p) { this(p.l, p.c); }
	public Pos(int line, int col) { l = line; c = col; }
	public String toString() { return "(" + l + ":" + c + ")"; }
	public int hashCode() {	return Integer.rotateLeft(l, 16) ^ c; }
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Pos other = (Pos) obj;
		return c == other.c && l == other.l;
	}
}