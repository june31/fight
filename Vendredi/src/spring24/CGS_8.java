package spring24;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import tools.Scanner;
import tools.switches.Switch;
import tools.tables.Table;

class CGS_8 {

    /**
     * @param nRows The number of rows in the target pattern.
     * @param nCols The number of columns in the target pattern.
     * @param targetPattern The target pattern, row by row from left to right.
     * @return The shortest possible list of pixel coordinates to activate in order to reproduce the target pattern.
     */
    public static List<List<Integer>> createPattern(int nl, int nc, List<String> targetPattern) {
    	int[][] map = Table.toMap(targetPattern.toArray(new String[0]));
		boolean[] end = Table.toBooleanArray(Table.flatten(map), x -> x == '#');
		boolean[][] switches = new boolean[nl*nc][nl*nc];
		for (int l = 0; l < nl; l++) {
			for (int c = 0; c < nc; c++) {
				boolean[][] t = new boolean[nl][nc];
				t[l][c] = true;
				if (l > 0) t[l-1][c] = true;
				if (l < nl - 1) t[l+1][c] = true;
				if (c > 0) t[l][c-1] = true;
				if (c < nc - 1) t[l][c+1] = true;
				switches[l * nc + c] = Table.flatten(t);
			}
		}
		boolean[] result = Switch.reach(end, switches);
		var l = new ArrayList<List<Integer>>();
		for (int i = 0; i < nl*nc; i++) if (result[i]) l.add(List.of(i / nc, i % nc));
        return l;
    }

    /* Ignore and do not change the code below */
    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    /**
     * Try a solution
     * @param output The shortest possible list of pixel coordinates to activate in order to reproduce the target pattern.
     */
    public static void trySolution(List<List<Integer>> output) {
        System.out.println("" + gson.toJson(output));
    }

    public static void main(String args[]) {
        try (Scanner in = new Scanner(System.in)) {
            trySolution(createPattern(
                gson.fromJson(in.nextLine(), new TypeToken<Integer>(){}.getType()),
                gson.fromJson(in.nextLine(), new TypeToken<Integer>(){}.getType()),
                gson.fromJson(in.nextLine(), new TypeToken<List<String>>(){}.getType())
            ));
        }
    }
    /* Ignore and do not change the code above */
}


