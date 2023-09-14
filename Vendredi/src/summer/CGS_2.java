package summer;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import tools.Scanner;

public class CGS_2 {
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
                gson.fromJson(in.nextLine(), new TypeToken<List<String>>(){}.getType()),
                gson.fromJson(in.nextLine(), new TypeToken<List<Integer>>(){}.getType())
            ));
        }
    }
	
	final static int FORWARD = 1;
	final static int BACK = 2;
	final static int LEFT = 3;
	final static int RIGHT = 4;
	
    public static String findCorrectPath(List<String> instructions, List<Integer> target) {
        int n = instructions.size();
        int[] instr = new int[n];
        for (int i = 0; i < n; i++) {
        	var ins = instructions.get(i);
        	if (ins.equals("FORWARD")) instr[i] = FORWARD;
        	else if (ins.equals("BACK")) instr[i] = BACK;
        	else if (ins.equals("TURN LEFT")) instr[i] = LEFT;
        	else instr[i] = RIGHT;
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
        
        int i = 0;
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
        		if (TDX == RDX - 2 * dx && TDY == RDY - 2 * dy) { s = "BACK"; break; }
        		if (TDX - dy == -RDY && TDY + dx == RDX) { s = "TURN LEFT"; break; }
        		if (TDX + dy == RDY && TDY - dx == -RDX) { s = "TURN RIGHT"; break; }
        	} else if (ins == BACK) {
        		if (TDX == RDX + 2 * dx && TDY == RDY + 2 * dy) { s = "FORWARD"; break; }
        		if (TDX + dy == -RDY && TDY - dx == RDX) { s = "TURN LEFT"; break; }
        		if (TDX - dy == RDY && TDY + dx == -RDX) { s = "TURN RIGHT"; break; }
        	} else if (ins == LEFT) {
        		if (TDX == -RDX && TDY == -RDY) { s = "TURN RIGHT"; break; }
        		if (TDX - dx == RDY && TDY - dy == -RDX) { s = "FORWARD"; break; }
        		if (TDX + dx == RDY && TDY + dy == -RDX) { s = "BACK"; break; }
        	} else {
        		if (TDX == -RDX && TDY == -RDY) { s = "TURN LEFT"; break; }
        		if (TDX - dx == -RDY && TDY - dy == RDX) { s = "FORWARD"; break; }
        		if (TDX + dx == -RDY && TDY + dy == RDX) { s = "BACK"; break; }
        	}
        }
        
        //System.err.println(ins);
        return "Replace instruction " + (i+1) + " with " + s;
    }
}
