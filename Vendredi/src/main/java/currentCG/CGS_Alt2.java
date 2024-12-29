package currentCG;

import tools.bfs.BFS;
import tools.scanner.Scan;
import tools.scanner.list.ScanL;
import tools.strings.S;

public class CGS_Alt2 {
	public static void main(String[] args) {
		int n = Scan.readInt();
		var map = new int[n][n];
		for (int i = 0; i < n; i++) map[i] = ScanL.readLine().array();
		var bfs = new BFS(map);
		bfs.setMoveCondition(b -> Math.abs(b.v2 - b.v1) <= 1);
		bfs.setEnd(0);
		S.o(bfs.diffuse(n/2, n/2).found ? "yes" : "no");
	}
}
