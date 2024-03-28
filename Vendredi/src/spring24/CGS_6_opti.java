package spring24;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import tools.Scanner;
import tools.tables.Table;

class CGS_6_opti {

    /**
     * @param nRows The height of the image.
     * @param nCols The width of the image.
     * @param image Pixels of the image, given row by row from top to bottom. All pixel colors are alphanumeric.
     * @return The total length of wire needed to deploy the network (modulo 10^9+7)
     */
    public static int getCableLength(int nl, int nc, List<String> image) {
    	int[][] map = Table.toMap(image.toArray(new String[0]));
    	var ttl = new TreeMap<Integer, TreeMap<Integer, Integer>>();
    	var ttc = new TreeMap<Integer, TreeMap<Integer, Integer>>();
    	for (int l = 0; l < nl; l++) {
			for (int c = 0; c < nc; c++) {
				int x = map[l][c];
				var tl = ttl.get(x);
				var tc = ttc.get(x);
				if (tl == null) {
					tl = new TreeMap<>();
					ttl.put(x, tl);
					tc = new TreeMap<>();
					ttc.put(x, tc);
				}
				Integer vl = tl.get(l);
				tl.put(l, vl == null ? 1 : vl + 1);
				Integer vc = tc.get(c);
				tc.put(c, vc == null ? 1 : vc + 1);
			}
		}
		
    	long length = 0;
    	for (var tl: ttl.values()) {
    		var list = new ArrayList<>(tl.entrySet());
    		int[][][] vals = new int[list.size()+1][nc+1][2];
    		int old = 0;
    		for (int i = list.size() - 1; i >= 0; i--) {
    			var e = list.get(i);
    			int pos = e.getKey();
    			int mul = e.getValue();
    			for (int j = 1; j <= nc; j++) {
    				vals[i][j][0] = vals[i+1][j][0] + (old - pos) * vals[i+1][j][1];
    				vals[i][j][1] = vals[i+1][j][1];
				}
    			vals[i][mul][1]++; 
    			old = pos;
    		}
    		
    		for (int i = 0; i < list.size(); i++) {
    			int mul = list.get(i).getValue();
        		for (int j = 1; j <= nc; j++) {
        			length += mul * j * vals[i][j][0];
        		}
    		}
    	}

    	for (var tc: ttc.values()) {
    		var list = new ArrayList<>(tc.entrySet());
    		int[][][] vals = new int[list.size()+1][nl+1][2];
    		int old = 0;
    		for (int i = list.size() - 1; i >= 0; i--) {
    			var e = list.get(i);
    			int pos = e.getKey();
    			int mul = e.getValue();
    			for (int j = 1; j <= nl; j++) {
    				vals[i][j][0] = vals[i+1][j][0] + (old - pos) * vals[i+1][j][1];
    				vals[i][j][1] = vals[i+1][j][1];
				}
    			vals[i][mul][1]++; 
    			old = pos;
    		}
    		
    		for (int i = 0; i < list.size(); i++) {
    			int mul = list.get(i).getValue();
        		for (int j = 1; j <= nl; j++) {
        			length += mul * j * vals[i][j][0];
        		}
    		}
    	}

        return (int) (length*2 % 1_000_000_007);
    }

    /* Ignore and do not change the code below */
    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    /**
     * Try a solution
     * @param cableLength The total length of wire needed to deploy the network (modulo 10^9+7)
     */
    public static void trySolution(int cableLength) {
        System.out.println("" + gson.toJson(cableLength));
    }

    public static void main(String args[]) {
        try (Scanner in = new Scanner(System.in)) {
            trySolution(getCableLength(
                gson.fromJson(in.nextLine(), new TypeToken<Integer>(){}.getType()),
                gson.fromJson(in.nextLine(), new TypeToken<Integer>(){}.getType()),
                gson.fromJson(in.nextLine(), new TypeToken<List<String>>(){}.getType())
            ));
        }
    }
    /* Ignore and do not change the code above */
}
