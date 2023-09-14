package summer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import tools.Scanner;

public class CGS_3 {
	 /* Ignore and do not change the code below */
    private static final Gson gson = new Gson();

    /**
     * Try a solution
     * @param output An array of two integers representing the number of gears rotating clockwise and counterclockwise respectively, or [-1, -1] if the configuration is invalid.
     */
    public static void trySolution(List<Integer> output) {
        System.out.println("" + gson.toJson(output));
    }

    public static void main(String args[]) {
        try (Scanner in = new Scanner(System.in)) {
            trySolution(gearBalance(
                gson.fromJson(in.nextLine(), new TypeToken<Integer>(){}.getType()),
                gson.fromJson(in.nextLine(), new TypeToken<List<List<Integer>>>(){}.getType())
            ));
        }
    }
    /* Ignore and do not change the code above */
    
    public static List<Integer> gearBalance(int n, List<List<Integer>> cs) {
    	Gear[] gs = new Gear[n];
    	for (int i = 0; i < n; i++) {
    		gs[i] = new Gear();
    	}
    	for (var l : cs) {
    		gs[l.get(0)].conns.add(gs[l.get(1)]);
    		gs[l.get(1)].conns.add(gs[l.get(0)]);
    	}
    	
    	gs[0].clockwise = true;
    	boolean hasWork = true;
    	boolean blocked = false;
    	all: while (hasWork) {
    		hasWork = false;
    		for (int i = 0; i < n; i++) {
    			Gear g = gs[i];
    			if (g.clockwise != null && !g.processed) {
    				for (Gear l: g.conns) {
    					if (l.clockwise != null && l.clockwise == g.clockwise) {
    						blocked = true;
    						break all;
    					}
    					l.clockwise = !g.clockwise;
    				}
    				g.processed = true;
    			} 
    			if (!g.processed) hasWork = true;
    		}
    	}

    	if (blocked) return Arrays.asList(-1, -1);
    	
    	int csum = 0;
		for (int i = 0; i < n; i++) if (gs[i].clockwise) csum++;
    	return Arrays.asList(csum, n-csum);
    }
    
    static class Gear {
    	static int idx = 0;
    	int id = idx++;
    	Boolean clockwise;
    	boolean processed = false;
    	List<Gear> conns = new ArrayList<>();
    	@Override
    	public String toString() {
    		return "G"+id;
    	}
    }
}
