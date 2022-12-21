package codechef.training.sparsetable;

import tools.output.Out;
import tools.scanner.Scan;
import tools.structures.segmenttree.SegmentTreeLong;

public class Main_SPREAD extends Scan {
	public static void main(String[] args) {
		int n = readInt();
		int m = readInt();
		int c = readInt();
		long[] tab = new long[n];
		var tree = new SegmentTreeLong(tab, Long::sum);
		
		for (int i = 0; i < m; i++) {
			if (readString().equals("S")) {
				int u = readInt() - 1;
				int v = readInt();
				int k = readInt();
				tree.set(u, tab[u] + k);
				if (v < n) tree.set(v, tab[v] - k);
			} else Out.bufln(tree.get(0, readInt() - 1) + c);
		}
		Out.flush();
	}
}
