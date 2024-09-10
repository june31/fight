package summer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import tools.Scanner;

public class CGS_6 {

    private static final Gson gson = new Gson();

    public static void trySolution(String mixedAb) {
        System.out.println("" + gson.toJson(mixedAb));
    }

    public static void main(String args[]) {
        try (Scanner in = new Scanner(System.in)) {
            trySolution(mixWishes(
                gson.fromJson(in.nextLine(), new TypeToken<String>(){}.getType()),
                gson.fromJson(in.nextLine(), new TypeToken<String>(){}.getType())
            ));
        }
    }

    public static String mixWishes(String wa, String wb) {
    	System.err.println(wa);
        System.err.println(wb);
        
    	char[] a = wa.toCharArray();
    	char[] b = wb.toCharArray();
    	int na = a.length;
    	int nb = b.length;
    	System.err.println(na);
        System.err.println(nb);
    	int[][] ab = new int[nb][na];
    	int[][] ba = new int[na][nb];
    	
    	for (int i = 0; i < na; i++) {
        	for (int j = 0; j < nb; j++) {
        		ab[j][i] = wb.indexOf(a[i], j);
        		ba[i][j] = wa.indexOf(b[j], i);
        	}
    	}
    	
    	int min = Integer.MAX_VALUE;
    	int minMask = 0;
    	
    	for (int mask = 0; mask < 0x8000000; mask++) {
        	int ia = 0;
        	int ib = 0;
        	int l = 0;
        	int shift = 0;
    		while (true) {
    			while (ia < na && ab[ib][ia] == -1) { ia++; l++; }
    			if (ia == na) {
    				l += nb - ib;
    				break;
    			}
    			while (ib < nb && ba[ia][ib] == -1) { ib++; l++; }
    			if (ib == nb) {
    				l += na - ia;
    				break;
    			}
    			while (ia < na && ib < nb && a[ia] == b[ib]) { ia++; ib++; l++; }
    			if (ia == na) {
    				l += nb - ib;
    				break;
    			}
    			if (ib == nb) {
    				l += na - ia;
    				break;
    			}
    			if ((mask & 1<<shift++) == 0) { ia++; l++; } 
    			else { ib++; l++; }
    			if (ia == na) {
    				l += nb - ib;
    				break;
    			}
    			if (ib == nb) {
    				l += na - ia;
    				break;
    			}
    		}
    		if (l < min) {
    			min = l;
    			minMask = mask;
    		}
    	}
    	
    	StringBuilder sb = new StringBuilder();
    	int ia = 0;
    	int ib = 0;
    	int shift = 0;
		while (true) {
			while (ia < na && ab[ib][ia] == -1) sb.append(a[ia++]);
			if (ia == na) {
				while (ib < nb) sb.append(b[ib++]);
				break;
			}
			while (ib < nb && ba[ia][ib] == -1) sb.append(b[ib++]);
			if (ib == nb) {
				while (ia < na) sb.append(a[ia++]);
				break;
			}
			while (ia < na && ib < nb && a[ia] == b[ib]) { sb.append(a[ia++]); ib++; }
			if (ia == na) {
				while (ib < nb) sb.append(b[ib++]);
				break;
			}
			if (ib == nb) {
				while (ia < na) sb.append(a[ia++]);
				break;
			}
			if ((minMask & 1<<shift++) == 0) sb.append(a[ia++]);
			else sb.append(b[ib++]);
			if (ia == na) {
				while (ib < nb) sb.append(b[ib++]);
				break;
			}
			if (ib == nb) {
				while (ia < na) sb.append(a[ia++]);
				break;
			}
		}
		System.err.println(min + " " + sb.length());
        return sb.toString();
    }
}
