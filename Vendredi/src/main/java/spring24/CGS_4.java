package spring24;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import tools.math.Dist;
import tools.tables.Table;

class CGS_4 {

    /**
     * @param nRows The height of the image.
     * @param nCols The width of the image.
     * @param image Pixels of the image, given row by row from top to bottom.
     * @return The parameters of the largest circle [centerRow, centerCol, radius].
     */
    public static List<Integer> findLargestCircle(int nRows, int nCols, List<String> image) {
        int[][] map = Table.toMap(image.toArray(new String[0]));
        int nl = map.length;
        int nc = map[0].length;

        int maxL = 0;
        int maxC = 0;
        int maxR = 0;
        for (int l = 1; l < nl-maxR-1; l++) {
        	CLoop: for (int c = maxR+1; c < nc-maxR-1; c++) {
        		int r = maxR + 1;
        		while (true) {
        			if (l < r || l >= nl - r || c < r || c >= nc - r) continue CLoop; 
        			int x = map[l-r][c];
        			if (x == map[l+r][c] && x == map[l][c-r] && x == map[l+r][c] && x == map[l][c+r]) {
        				boolean perfect = true;
        				int sr1 = r*r;
        				int sr2 = (r+1)*(r+1);
        				int fmin = 0;
        				for (int i = 1; i <= r; i++) {
        					for (int f = fmin; f <= r; f++) {
        						int d = Dist.squared(l, c, l-r+f, c+i);
            					if (Dist.squared(l, c, l-r+f, c+i) >= sr2) {
            						fmin = f+1;
            						continue;
            					}
            					if (d < sr1) break;
            					if (map[l-r+f][c+i] != x || map[l-r+f][c-i] != x || map[l+r-f][c+i] != x || map[l+r-f][c-i] != x) {
            						perfect = false;
            						break;
            					}
        					}
        				}
        				if (perfect) {
        					maxL = l;
        					maxC = c;
        					maxR = r;
        				}
        			}
        			r++;
        		}
			}
		}
        
        return Arrays.asList(maxL, maxC, maxR);
    }

    
    /* Ignore and do not change the code below */
    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    /**
     * Try a solution
     * @param largestCircle The parameters of the largest circle [centerRow, centerCol, radius].
     */
    public static void trySolution(List<Integer> largestCircle) {
        System.out.println("" + gson.toJson(largestCircle));
    }

    public static void main(String args[]) {
        try (Scanner in = new Scanner(System.in)) {
            trySolution(findLargestCircle(
                gson.fromJson(in.nextLine(), new TypeToken<Integer>(){}.getType()),
                gson.fromJson(in.nextLine(), new TypeToken<Integer>(){}.getType()),
                gson.fromJson(in.nextLine(), new TypeToken<List<String>>(){}.getType())
            ));
        }
    }
    /* Ignore and do not change the code above */
}


