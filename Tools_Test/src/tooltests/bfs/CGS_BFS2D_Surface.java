package tooltests.bfs;

import tools.bfs.BFS2D;
import tools.scanner.Scan;

// https://www.codingame.com/ide/puzzle/surface
// BFS2D is fast enough. No need for memoization.
public class CGS_BFS2D_Surface {
	public static void main(String[] args) {
		BFS2D bfs = new BFS2D(Scan.readMapCL()).wall('#');
		int n = Scan.readInt();
		for (int i = 0; i < n; i++) {
			int c = Scan.readInt();
			bfs.diffuse(Scan.readInt(), c);
			System.out.println(bfs.scanned);
		}
	}
}
