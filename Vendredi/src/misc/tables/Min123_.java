package misc.tables;

import tools.chrono.Chrono;
import tools.output.Out;
import tools.scanner.Scan;
import tools.structures.segmenttree.SegmentTreeIntIdx;

public class Min123_ {
	public static void main(String[] args) {
		Chrono.start();

		int[] tab = Scan.readInts();
		SegmentTreeIntIdx tree = new SegmentTreeIntIdx(tab, (x, y) -> x < y);
		int m = Scan.readInt();
		for (int i = 0; i < m; i++) {
			int a = Scan.readInt();
			int b = Scan.readInt();
			int id1 = tree.getIndex(a, b);
			int val1 = tab[id1];
			tree.set(id1, Integer.MAX_VALUE);
			int id2 = tree.getIndex(a, b);
			int val2 = tab[id2];
			tree.set(id2, Integer.MAX_VALUE);
			Out.bufln(val1 + " " + val2 + " " + tab[tree.getIndex(a, b)]);
			tree.set(id1, val1);
			tree.set(id2, val2);
		}
		Out.flush();
		
		Chrono.stop();
	}
}
