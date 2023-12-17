package tooltests.bfs;

import tools.bfs.BFS2D;
import tools.bfs.BFS2DExt;
import tools.bfs.BFS2DWExt;
import tools.tables.Table;
import tools.tuple.Pos;

public class Misc_BFS2DWExt {
	public static void main(String[] args) {
		
		int[][] zeros = new int[6][6];
		int[][] ones = new int[6][6];
		Table.fill(ones, 1);
		int[][] twos = new int[6][6];
		Table.fill(twos, 2);
		int[][] tens = new int[6][6];
		Table.fill(tens, 10);
		int turn;
		BFS2D b1;
		BFS2DExt b2;
		BFS2DWExt bfs;
		
		b1 = new BFS2D(zeros);
		turn = b1.diffuse(new Pos(0, 0), '#');
		System.out.println("2D Turn: " + turn);

		b2 = new BFS2DExt(zeros);
		turn = b2.diffuse(new Pos(0, 0), '#');
		System.out.println("2D Turn: " + turn);

		bfs = new BFS2DWExt(zeros, ones);
		turn = bfs.diffuse(new Pos(0, 0), '#');
		System.out.println("Ones Turn: " + turn + " maxW: " + bfs.maxWeight);

		bfs = new BFS2DWExt(zeros, twos);
		turn = bfs.diffuse(new Pos(0, 0), '#');
		System.out.println("Twos Turn: " + turn + " maxW: " + bfs.maxWeight);

		bfs = new BFS2DWExt(zeros, tens);
		turn = bfs.diffuse(new Pos(0, 0), '#');
		System.out.println("Tens Turn: " + turn + " maxW: " + bfs.maxWeight);
		
		bfs = new BFS2DWExt(zeros, zeros);
		turn = bfs.diffuse(new Pos(0, 0), '#');
		System.out.println("Zeros Turn: " + turn + " maxW: " + bfs.maxWeight);
		
		b1 = new BFS2D(zeros);
		turn = b1.diffuse(new Pos(0, 0), '#', new Pos(3, 4));
		System.out.println("2D Turn: " + turn);

		b2 = new BFS2DExt(zeros);
		turn = b2.diffuse(new Pos(0, 0), '#', new Pos(3, 4));
		System.out.println("2D Turn: " + turn);

		bfs = new BFS2DWExt(zeros, ones);
		turn = bfs.diffuse(new Pos(0, 0), '#', new Pos(3, 4));
		System.out.println("Ones Turn: " + turn + " maxW: " + bfs.maxWeight);

		bfs = new BFS2DWExt(zeros, twos);
		turn = bfs.diffuse(new Pos(0, 0), '#', new Pos(3, 4));
		System.out.println("Twos Turn: " + turn + " maxW: " + bfs.maxWeight);

		bfs = new BFS2DWExt(zeros, tens);
		turn = bfs.diffuse(new Pos(0, 0), '#', new Pos(3, 4));
		System.out.println("Tens Turn: " + turn + " maxW: " + bfs.maxWeight);
		
		bfs = new BFS2DWExt(zeros, zeros);
		turn = bfs.diffuse(new Pos(0, 0), '#', new Pos(3, 4));
		System.out.println("Zeros Turn: " + turn + " maxW: " + bfs.maxWeight);
		
		int[][] w1 = {
				{ 1, 2, 4, 1, 3, 4 },
				{ 0, 2, 4, 1, 4, 1 },
				{ 0, 0, 1, 3, 1, 2 },
				{ 2, 4, 2, 1, 2, 4 },
				{ 1, 2, 4, 1, 2, 1 },
				{ 1, 1, 0, 1, 0, 4 }
		};
		bfs = new BFS2DWExt(zeros, w1);
		turn = bfs.diffuse(new Pos(0, 0), '#', new Pos(3, 4));
		System.out.println("Zeros Turn: " + turn + " maxW: " + bfs.maxWeight);
	}
}
