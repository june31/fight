package aoc.done;

import java.util.ArrayList;
import java.util.List;

import tools.bfs.BFS2D;
import tools.scanner.Scan;
import tools.tables.Table;
import tools.tuple.Pos;

public class Day_23_1 {
	
	private static int[][] map = Scan.readMap0();
	private static int L = map.length;
	
	public static void main(String[] args) {
		Board startBoard = new Board();
		startBoard.m = map;
		startBoard.start = new Pos(0, 1);
		recurse(startBoard);
		Table.printMap(Board.bestMap);
		System.out.println(Board.maxSteps);
	}
	
	private static void recurse(Board board) {
		for (Pos p: board.process()) {
			Board newBoard = new Board();
			board.copyTo(newBoard);
			newBoard.start = p;
			recurse(newBoard);
		}
	}

	private static class Board {

		private static int maxSteps = 0;
		private static int[][] bestMap;
		
		private int[][] m;
		private int steps = -1;
		private Pos start;
		private Pos end;

		private BFS2D bfs;
		private int d;

		void copyTo(Board b) {
			b.m = Table.copy(m);
			b.steps = steps;
			b.start = end;
		}

		List<Pos> process() {
			List<Pos> children = new ArrayList<>();
			bfs = new BFS2D(m);
			bfs.diffuse(start, () -> cond(), () -> fin());
			if (bfs.l2 == L - 1) {
				System.out.println(" --- " + steps);
				if (steps > maxSteps) {
					maxSteps = steps;
					bestMap = m;
				}
				return new ArrayList<>();
			}
			//Table.printMap(m);
			//System.out.println(steps);
			int v;
			v = m[bfs.l2 - 1][bfs.c2];
			if (v == '.' || v == '^') children.add(new Pos(bfs.l2 - 1, bfs.c2));
			v = m[bfs.l2 + 1][bfs.c2];
			if (v == '.' || v == 'v') children.add(new Pos(bfs.l2 + 1, bfs.c2));
			v = m[bfs.l2][bfs.c2 - 1];
			if (v == '.' || v == '<') children.add(new Pos(bfs.l2, bfs.c2 - 1));
			v = m[bfs.l2][bfs.c2 + 1];
			if (v == '.' || v == '>') children.add(new Pos(bfs.l2, bfs.c2 + 1));

			return children;
		}

		private boolean cond() {
			if (bfs.v2 < '.') return false;
			if (bfs.v1 == '>' && bfs.c2 != bfs.c1 + 1) return false; 
			if (bfs.v1 == '<' && bfs.c2 != bfs.c1 - 1) return false; 
			if (bfs.v1 == '^' && bfs.l2 != bfs.l1 - 1) return false; 
			if (bfs.v1 == 'v' && bfs.l2 != bfs.l1 + 1) return false; 
			return true;
		}

		private boolean fin() {
			steps++;
			d = 0;
			m[bfs.l2][bfs.c2] = '$';
			if (bfs.l2 == 0) return false;
			if (bfs.l2 == L-1) return true;
			
			if (m[bfs.l2 - 1][bfs.c2] > '$') d++;
			if (m[bfs.l2 + 1][bfs.c2] > '$') d++;
			if (m[bfs.l2][bfs.c2 - 1] > '$') d++;
			if (m[bfs.l2][bfs.c2 + 1] > '$') d++;
			if (d > 1) end = new Pos(bfs.l2, bfs.c2);
			return d > 1;
		}
	}
}
