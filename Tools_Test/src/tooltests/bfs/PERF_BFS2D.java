package tooltests.bfs;

import tools.bfs.BFS2D;
import tools.bfs.BFS2DExt;
import tools.chrono.Chrono;

public class PERF_BFS2D {
	public static void main(String[] args) {
		int[][] t = new int[200][200];
		for (int j = 0; j < 10; j++) {
		Chrono.start();
		BFS2D b1 = new BFS2D(t).wall('#');
		for (int i = 0; i < 100; i++) {
			b1.diffuse(i, i);
		}
		System.out.print("Fast ");
		Chrono.stop();
		Chrono.start();
		BFS2DExt b2 = new BFS2DExt(t).wall('#');
		for (int i = 0; i < 100; i++) {
			b2.diffuse(i, i);
		}
		System.out.print("Slow ");
		Chrono.stop();
		}
	}
}
