package spring24;

import java.util.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import tools.collections.string.Ls;
import tools.tables.Table;

class CGP_5 {

    /**
     * @param n The size of the image
     * @param targetImage The rows of the desired image, from top to bottom
     */
    public static List<String> solve(int n, List<String> targetImage) {
    	int[][] map = Table.toMap(targetImage.toArray(new String[0]));
    	Ls commands = new Ls();
    	boolean action = false;
    	do {
    		action = false;
    		LoopL: for (int l = 0; l < n; l++) {
    			boolean work = false;
				for (int c = 0; c < n; c++) {
					if (map[l][c] == '#') continue LoopL;
					if (map[l][c] == '.') work = true;
				}
				if (work) {
					for (int c = 0; c < n; c++) map[l][c] = 'x';
					commands.add("R " + l);
					action = true;
				}
			}
    		LoopC: for (int c = 0; c < n; c++) {
    			boolean work = false;
				for (int l = 0; l < n; l++) {
					if (map[l][c] == '.') continue LoopC;
					if (map[l][c] == '#') work = true;
				}
				if (work) {
					for (int l = 0; l < n; l++) map[l][c] = 'x';
					commands.add("C " + c);
					action = true;
				}
			}
    	} while (action);
        return commands.reversed();
    }

    /* Ignore and do not change the code below */
    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    /**
     * Try a solution
     */
    public static void trySolution(List<String> commands) {
        System.out.println("" + gson.toJson(commands));
    }

    public static void main(String args[]) {
        try (Scanner in = new Scanner(System.in)) {
            trySolution(solve(
                gson.fromJson(in.nextLine(), new TypeToken<Integer>(){}.getType()),
                gson.fromJson(in.nextLine(), new TypeToken<List<String>>(){}.getType())
            ));
        }
    }
    /* Ignore and do not change the code above */
}



