package codingame.player;

import java.util.ArrayList;
import java.util.List;

public class CGP_WC23_1 {

	public static void main(String[] args) {
		System.out.println(solve(4, 5, 8, 3));
	}
    public static List<String> solve(int ps, int ns, int pe, int ne) {
        int m = Math.max(ps - pe, ns - ne);
        int d =  m > 0 ? (m + 1) / 2 : 0;
        var l = new ArrayList<String>();
        if (ps - d*2 < pe) for (int i = 0; i < pe - ps + d*2; i++) l.add("PROTON");
        if (ns - d*2 < ne) for (int i = 0; i < ne - ns + d*2; i++) l.add("NEUTRO");
        for (int i = 0; i < d; i++) l.add("ALPHA");
        return l;
    }
}
